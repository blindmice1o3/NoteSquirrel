package com.jackingaming.notesquirrel.sandbox.downloadhtml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.input.DirectionalPadFragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class DownloadHtmlFragmentParentActivity extends AppCompatActivity {

    Fragment viewFragment;
    TextView textView;

    Fragment controllerFragment;
    Button buttonFragmentSwapper;
    Button buttonWebsiteDisplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_html_fragment_parent);
        Log.d(MainActivity.DEBUG_TAG, "DownloadHtmlFragmentParentActivity.onCreate(Bundle)");

        viewFragment = (ViewFragment) getSupportFragmentManager().findFragmentById(R.id.viewFragment);
        textView = (TextView) findViewById(R.id.textView_displayer);

        controllerFragment = (ControllerFragment) getSupportFragmentManager().findFragmentById(R.id.controllerFragment);
        buttonFragmentSwapper = (Button) findViewById(R.id.button_fragment_swapper);
        buttonWebsiteDisplayer = (Button) findViewById(R.id.button_website_displayer);

        buttonFragmentSwapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "DownloadHtmlFragmentParentActivity.buttonFragmentSwapper.onClick(View)");

                DirectionalPadFragment directionalPadFragment = new DirectionalPadFragment();

                ////////////////////////////////////////////////////////////////////////////////////
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.viewFragment, directionalPadFragment);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
                ////////////////////////////////////////////////////////////////////////////////////
            }
        });

        buttonWebsiteDisplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "DownloadHtmlFragmentParentActivity.buttonWebsiteDisplayer.onClick(View)");

                //Don't want to access the internet on the main thread (could lock up the main thread/UI)
                //Android actually prevent you from doing that... so have to do this on a separate thread.
                new AsyncTask<Void, Void, String>() {
                    //Ferocious looking parameterized-class (AsyncTask<Params, Progress, Result>),
                    //this class lets you pass in parameters into your class, post values to
                    //indicate progress (which you can get to update your GUI), and
                    //you can get results as well.
                    //Cannot use void with lower-case 'V' because void is a primitive-type,
                    //must use the class Void.
                    @Override
                    protected String doInBackground(Void... params) {
                        try {
                            //////////////////////
                            return downloadHTML();
                            //////////////////////
                        } catch (Exception e) {
                            Log.d(MainActivity.DEBUG_TAG, e.toString());
                            e.printStackTrace();
                        }

                        return "Can't reach server. Is Internet access enabled?";
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        /////////////////////////
                        textView.setText(result);
                        /////////////////////////
                    }
                }.execute();
            }
        });
    }

    private String downloadHTML() throws Exception {
        URL url = new URL("https://objectionable.net/");

        InputStream is = url.openStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            //Log.d(MainActivity.DEBUG_TAG, line);

            ////////////////
            sb.append(line);
            ////////////////
        }

        return sb.toString();
    }

}