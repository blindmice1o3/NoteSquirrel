package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.gamepad.directionpad;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.input.DirectionalPadFragment;

public class DirectionPadFragment extends Fragment {
    public enum Direction { UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT; }
    public interface TouchListener {
        void onDirectionPadTouch(Direction direction, boolean pressing);
    }
    private TouchListener listener;
    public void setTouchListener(TouchListener listener) {
        this.listener = listener;
    }

    private ConstraintLayout constraintLayout;
    private ImageView imageViewTopLeft;
    private ImageView imageViewTopCenter;
    private ImageView imageViewTopRight;
    private ImageView imageViewMiddleLeft;
    private ImageView imageViewMiddleCenter;
    private ImageView imageViewMiddleRight;
    private ImageView imageViewBottomLeft;
    private ImageView imageViewBottomCenter;
    private ImageView imageViewBottomRight;

    private Rect boundsOfUp;
    private Rect boundsOfDown;
    private Rect boundsOfLeft;
    private Rect boundsOfRight;
    private Rect boundsOfUpLeft;
    private Rect boundsOfUpRight;
    private Rect boundsOfDownLeft;
    private Rect boundsOfDownRight;

    public DirectionPadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_direction_pad, container, false);

        constraintLayout = view.findViewById(R.id.constraintlayout_direction_pad_fragment);
        imageViewTopLeft = view.findViewById(R.id.imageview_topleft_direction_pad_fragment);
        imageViewTopCenter = view.findViewById(R.id.imageview_topcenter_direction_pad_fragment);
        imageViewTopRight = view.findViewById(R.id.imageview_topright_direction_pad_fragment);
        imageViewMiddleLeft = view.findViewById(R.id.imageview_middleleft_direction_pad_fragment);
        imageViewMiddleCenter = view.findViewById(R.id.imageview_middlecenter_direction_pad_fragment);
        imageViewMiddleRight = view.findViewById(R.id.imageview_middleright_direction_pad_fragment);
        imageViewBottomLeft = view.findViewById(R.id.imageview_bottomleft_direction_pad_fragment);
        imageViewBottomCenter = view.findViewById(R.id.imageview_bottomcenter_direction_pad_fragment);
        imageViewBottomRight = view.findViewById(R.id.imageview_bottomright_direction_pad_fragment);

        Bitmap source = BitmapFactory.decodeResource(getResources(), R.drawable.d_pad);
        Bitmap imageTopleft = Bitmap.createBitmap(source, 22, 365, 40, 40);
        Bitmap imageTopCenter = Bitmap.createBitmap(source, 62, 365, 52, 40);
        Bitmap imageTopRight = Bitmap.createBitmap(source, 114, 365, 40, 40);
        Bitmap imageMiddleLeft = Bitmap.createBitmap(source, 22, 405, 40, 52);
        Bitmap imageMiddleCenter = Bitmap.createBitmap(source, 62, 405, 52, 52);
        Bitmap imageMiddleRight = Bitmap.createBitmap(source, 114, 405, 40, 52);
        Bitmap imageBottomLeft = Bitmap.createBitmap(source, 22, 457, 40, 40);
        Bitmap imageBottomCenter = Bitmap.createBitmap(source, 62, 457, 52, 40);
        Bitmap imageBottomRight = Bitmap.createBitmap(source, 114, 457, 40, 40);

        imageViewTopLeft.setImageBitmap(imageTopleft);
        imageViewTopCenter.setImageBitmap(imageTopCenter);
        imageViewTopRight.setImageBitmap(imageTopRight);
        imageViewMiddleLeft.setImageBitmap(imageMiddleLeft);
        imageViewMiddleCenter.setImageBitmap(imageMiddleCenter);
        imageViewMiddleRight.setImageBitmap(imageMiddleRight);
        imageViewBottomLeft.setImageBitmap(imageBottomLeft);
        imageViewBottomCenter.setImageBitmap(imageBottomCenter);
        imageViewBottomRight.setImageBitmap(imageBottomRight);

        return view;
    }

    public void setupOnTouchListener() {
        boundsOfUp = new Rect(imageViewTopCenter.getLeft(), imageViewTopCenter.getTop(), imageViewTopCenter.getRight(), imageViewTopCenter.getBottom());
        Log.d(MainActivity.DEBUG_TAG, "<" + imageViewTopCenter.getLeft() + ", " + imageViewTopCenter.getTop() + ", " + imageViewTopCenter.getRight() + ", " + imageViewTopCenter.getBottom() + ">");
        boundsOfLeft = new Rect(imageViewMiddleLeft.getLeft(), imageViewMiddleLeft.getTop(), imageViewMiddleLeft.getRight(), imageViewMiddleLeft.getBottom());
        boundsOfRight = new Rect(imageViewMiddleRight.getLeft(), imageViewMiddleRight.getTop(), imageViewMiddleRight.getRight(), imageViewMiddleRight.getBottom());
        boundsOfDown = new Rect(imageViewBottomCenter.getLeft(), imageViewBottomCenter.getTop(), imageViewBottomCenter.getRight(), imageViewBottomCenter.getBottom());
        // TODO: boundsOfUpLeft, boundsOfUpRight, boundsOfDownLeft, boundsOfDownRight.
        boundsOfUpLeft = new Rect(imageViewTopLeft.getLeft(), imageViewTopLeft.getTop(), imageViewTopLeft.getRight(), imageViewTopLeft.getBottom());
        boundsOfUpRight = new Rect(imageViewTopRight.getLeft(), imageViewTopRight.getTop(), imageViewTopRight.getRight(), imageViewTopRight.getBottom());
        boundsOfDownLeft = new Rect(imageViewBottomLeft.getLeft(), imageViewBottomLeft.getTop(), imageViewBottomLeft.getRight(), imageViewBottomLeft.getBottom());
        boundsOfDownRight = new Rect(imageViewBottomRight.getLeft(), imageViewBottomRight.getTop(), imageViewBottomRight.getRight(), imageViewBottomRight.getBottom());

        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // If this callback is being called, a MotionEvent was triggered...
                // MotionEvent event may be: ACTION_DOWN, ACTION_MOVE, or ACTION_UP.
                boolean pressing = true;
                Direction direction = null;

                // Determine if the touch event occurred within the bounds of a "button".
                // If so, set direction to the corresponding "button", otherwise the
                // touch event should NOT count as a "button" press.
                if (boundsOfUp.contains( (int) event.getX(), (int) event.getY() )) {
                    direction = Direction.UP;
                } else if (boundsOfDown.contains( (int) event.getX(), (int) event.getY() )) {
                    direction = Direction.DOWN;
                } else if (boundsOfLeft.contains( (int) event.getX(), (int) event.getY() )) {
                    direction = Direction.LEFT;
                } else if (boundsOfRight.contains( (int) event.getX(), (int) event.getY() )) {
                    direction = Direction.RIGHT;
                } else if (boundsOfUpLeft.contains( (int) event.getX(), (int) event.getY() )) {
                    direction = Direction.UP_LEFT;
                } else if (boundsOfUpRight.contains( (int) event.getX(), (int) event.getY() )) {
                    direction = Direction.UP_RIGHT;
                } else if (boundsOfDownLeft.contains( (int) event.getX(), (int) event.getY() )) {
                    direction = Direction.DOWN_LEFT;
                } else if (boundsOfDownRight.contains( (int) event.getX(), (int) event.getY() )) {
                    direction = Direction.DOWN_RIGHT;
                } else {
                    pressing = false;
                }

                // ACTION_UP means a "button" was released, and is NOT a "button" press.
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    pressing = false;
                }

                if (direction != null) {
                    //////////////////////////////////////////////////
                    listener.onDirectionPadTouch(direction, pressing);
                    //////////////////////////////////////////////////
                }

                return true;
            }
        });
    }
}