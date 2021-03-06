package com.jackingaming.notesquirrel.passpoints;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

import java.util.List;

public class ImageActivity extends AppCompatActivity
        implements IPointCollectorListener {

    //reference (key) to put/get boolean from SharedPreferences (persistent data).
    public static final String PASSWORD_SET = "PASSWORD_SET";
    //value representing the acceptable "off-ness" from the passcode's targeted point.
    private static final int POINT_CLOSENESS = 100;
    private PointCollector pointCollector = new PointCollector();
    private Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        //MUST come after setContentView(int).
        addTouchListener();

        //REGISTER self to the SUBJECT as an interested SUBSCRIBER.
        pointCollector.setListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //reference to persistent data (first time using OR pw was set during a prior run).
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //true if pw was set during a prior run, otherwise defaults to false.
        boolean passpointsSet = prefs.getBoolean(PASSWORD_SET, false);
        //if false (pw undefined by user), show prompt.
        if (!passpointsSet) {
            //displays a Dialog message to the device's screen.
            showSetPasspointsPrompt();
        }
    }

    private void showSetPasspointsPrompt() {
        //Builder is probably an inner-class of the AlertDialog class.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting up buttons on the builder (to customize the dialog that it will build).
        //setNegativeButton(CharSequence, OnClickListener) would be like the "cancel" button.
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //intentionally left blank.
            }
        });

        builder.setTitle("Create your passpoint sequence");
        builder.setMessage("Touch four points to set pass sequence");

        //use the Builder to create the dialog (set various options on the builder and then call create()).
        //this will return an AlertDialog object (it can have an "Ok" and/or "Cancel" button[s]).
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addTouchListener() {
        ImageView image = (ImageView) findViewById(R.id.touch_image);

        image.setOnTouchListener(pointCollector);
    }

    private void savePasspoints(final List<Point> points) {
        ////////////////////////////////////////////////////////////////////
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.storing_data));
        final AlertDialog dlg = builder.create();
        dlg.show();
        ////////////////////////////////////////////////////////////////////

        //Ferocious looking parameterized-class (AsyncTask<Params, Progress, Result>),
        //this class lets you pass in parameters into your class, post values to
        //indicate progress (which you can get to update your GUI), and
        //you can get results as well.
        //Cannot use void with lower-case 'V' because void is a primitive-type,
        //must use the class Void.
        //DEFINING the abstract class... using anonymous-class-type-of syntax.
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            //Needed to provide implementation code to this abstract method.
            @Override
            protected Void doInBackground(Void... params) {
                //SOME TASKS WILL TAKE A LONG TIME (THAT'S WHY WE'RE USING A
                //SEPARATE THREAD [i.e. AsyncTask] IN THE FIRST PLACE),
                //BUT OUR PARTICULAR CASE IS ACTUALLY REALLY FAST, SO WE'RE SLOWING IT
                //DOWN SO WE'LL ACTUALLY SEE THE DIALOG BEFORE dismiss() IS CALLED.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //USING THE NEW Database.storePoints(List<Point>) METHOD TO ADD ENTRIES TO OUR DATABASE'S TABLE.
                database.storePoints(points);
                Log.d(MainActivity.DEBUG_TAG, "Points saved: " + points.size());

                return null;
            }

            //Actual overriding of the original method (this method
            //runs after your task finish executing).
            @Override
            protected void onPostExecute(Void aVoid) {

                //distinguish between setting pw for first time VS attempts to log in (we started
                //implementing this feature in MainActivity, but commented it out so we could use
                //the button's onClickListener to learn how to use Toast).
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(PASSWORD_SET, true);
                editor.commit();

                //clears the ArrayList<Point> of present log-in attempt, so will be ready for next attempt.
                pointCollector.clear();
                //automatically have the AlertDialog message ("Storing passpoints...") go-away
                //without user having to click an "OK button".
                dlg.dismiss();
            }
        };
        //ACTUALLY RUNNING what we defined.
        task.execute();
    }

    private void verifyPasspoints(final List<Point> touchedPoints) {
        ////////////////////////////////////////////////////////////////////
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Checking passpoints...");
        final AlertDialog dlg = builder.create();
        dlg.show();
        ////////////////////////////////////////////////////////////////////

        //Ferocious looking parameterized-class (AsyncTask<Params, Progress, Result>),
        //this class lets you pass in parameters into your class, post values to
        //indicate progress (which you can get to update your GUI), and
        //you can get results as well.
        //Cannot use void with lower-case 'V' because void is a primitive-type,
        //must use the class Void.
        //DEFINING the abstract class... using anonymous-class-type-of syntax.
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            //Needed to provide implementation code to this abstract method.
            @Override
            protected Boolean doInBackground(Void... voids) {
                List<Point> savedPoints = database.getPoints();

                Log.d(MainActivity.DEBUG_TAG, "Loaded saved points: " + savedPoints.size());

                //return false if the stored passcode or the collection-of-points-just-touched
                //are not the expected size (PointCollector.NUM_POINTS).
                if ((savedPoints.size() != PointCollector.NUM_POINTS) ||
                        (touchedPoints.size() != PointCollector.NUM_POINTS)) {
                    return false;
                }

                for (int i = 0; i < PointCollector.NUM_POINTS; i++) {
                    Point savedPoint = savedPoints.get(i);
                    Point touchedPoint = touchedPoints.get(i);

                    //Horizontal distance away from the current saved point in the passcode sequence.
                    int xDiff = savedPoint.x - touchedPoint.x;
                    //Vertical distance away from the current saved point in the passcode sequence.
                    int yDiff = savedPoint.y - touchedPoint.y;

                    //Next is PYTHAGOREAN THEOREM applied to this problem to get one value for
                    //the difference between the touched point and the current saved point in
                    //the passcode sequence.
                    int distSquared = (xDiff * xDiff) + (yDiff * yDiff);

                    Log.d(MainActivity.DEBUG_TAG, "Distance squared: " + distSquared);

                    //return false if the point-just-touched is too far away from the
                    //allowable/acceptable "off-ness" from the actual passcode point.
                    //doing it this way to AVOID WORKING WITH SQUARE-ROOT.
                    if (distSquared > (POINT_CLOSENESS * POINT_CLOSENESS)) {
                        return false;
                    }
                }

                return true;
            }

            //Actual overriding of the original method (this method
            //runs after your task finish executing).
            //The "Boolean pass" parameter is actually what gets returned by the
            //doInBackground(Void...) method call (the thread's main workload).
            @Override
            protected void onPostExecute(Boolean pass) {
                //clears the ArrayList<Point> of present log-in attempt, so will be ready for next attempt.
                pointCollector.clear();
                //automatically have the AlertDialog message ("Checking passpoints...") go-away
                //without user having to click an "OK button".
                dlg.dismiss();

                //"pass" is what gets returned by doInBackground(Void...).
                //If pass is true, change the context to MainActivity.
                if (pass) {
                    //If we used just "this" instead of "ImageActivity.this"... it would've referred
                    //to this anonymous class instead of ImageActivity. And MainActivity.class (not
                    //MainActivity.java) is the class we want to launch.
                    Intent i = new Intent(ImageActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(ImageActivity.this, "Access Denied", Toast.LENGTH_LONG).show();
                }
            }
        };
        //ACTUALLY RUNNING what we defined.
        task.execute();
    }

    //this SUBSCRIBER class can now PULL relevant data from what the SUBJECT PUSHED.
    @Override
    public void pointsCollected(final List<Point> points) {
        Log.d(MainActivity.DEBUG_TAG, "Collected points: " + points.size());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean passpointsSet = prefs.getBoolean(PASSWORD_SET, false);
        //if false (pw undefined by user), store the 4 points that were collected.
        if (!passpointsSet) {
            Log.d(MainActivity.DEBUG_TAG, "Saving passpoints...");
            savePasspoints(points);
        }
        //otherwise, pw had been set during a prior run and we NEED TO COMPARE
        //THE PASSED-IN 4 points collected TO THE STORED pw.
        else {
            Log.d(MainActivity.DEBUG_TAG, "Verifying passpoints...");
            //check if 4-points passed in matches stored pw.
            verifyPasspoints(points);
            //if the pw is correct, let the user into the application.
        }

        /*
        //TODO: remove this test/verification code later (verification using Logcat's monitor).
        //TESTING THE NEW Database.getPoints() METHOD TO READ VALUES FROM OUR DATABASE'S TABLE.
        List<Point> testReadFromDatabase = database.getPoints();
        for (Point point : testReadFromDatabase) {
            Log.d(MainActivity.DEBUG_TAG, String.format("Got point: (%d, %d)", point.x, point.y));
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        //TODO: remove this developer_helper.
        String message = String.format("Collection of coordinates: (%d, %d), (%d, %d), (%d, %d), (%d, %d)",
                points.get(0).x, points.get(0).y,
                points.get(1).x, points.get(1).y,
                points.get(2).x, points.get(2).y,
                points.get(3).x, points.get(3).y);
        //display message to device's screen via a Toast pop-up.
        //(discovered that Toast is slow, and it continues to show back-logged Toasts even after app shut down.)
        //TODO: remove this Toast-developer_helper.
        Toast toastCantSave = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toastCantSave.show();
        */
    }

    /*
    @Override
    public void singlePointCollected(Point point) {
        String message = String.format("Single coordinate: (%d, %d)", point.x, point.y);
        //display message to device's screen via a Toast pop-up.
        //TODO: remove this Toast-developer_helper.
        Toast toastCantSave = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toastCantSave.show();
    }
    */

}