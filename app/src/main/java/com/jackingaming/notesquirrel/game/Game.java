package com.jackingaming.notesquirrel.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.game.sprites.Bat;
import com.jackingaming.notesquirrel.game.sprites.Ball;

public class Game {

    private SurfaceHolder holder;
    private Resources resources;

    private Ball ball;
    private Bat player;
    private Bat opponent;

    private Paint textPaint;

    public Game(int widthSurfaceView, int heightSurfaceView, SurfaceHolder holder, Resources resources) {
        this.holder = holder;
        this.resources = resources;

        ball = new Ball(widthSurfaceView, heightSurfaceView);
        player = new Bat(widthSurfaceView, heightSurfaceView, Bat.Position.LEFT);
        opponent = new Bat(widthSurfaceView, heightSurfaceView, Bat.Position.RIGHT);

        textPaint = new Paint();
        //set text's pivot-point to CENTER-OF-TEXT (instead of TOP-LEFT corner).
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(64);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void init() {
        Log.d(MainActivity.DEBUG_TAG, "Game.init()");
        Bitmap spriteSheetCorgiCrusade = BitmapFactory.decodeResource(resources, R.drawable.corgi_crusade);
        Bitmap spriteSheetYokoTileset = BitmapFactory.decodeResource(resources, R.drawable.pc_computer_yoko_tileset);

        ball.init(spriteSheetCorgiCrusade);
        player.init(spriteSheetYokoTileset);
        opponent.init(spriteSheetYokoTileset);
    }

    /**
     * Update the user's bat position.
     *
     * Handle touch events triggered by GameView (custom SurfaceView).
     *
     * @param event The touch event's meta-data (e.g. x and y position
     *              of the user triggered touch event)
     */
    public void onTouchEvent(MotionEvent event) {
        player.setBatPosition(event.getY());
    }

    public void update(long elapsed) {
        ///////////////////////////////////////////
        //COLLISION DETECTION (ball bounce off bat)
        ///////////////////////////////////////////
        //player (rectangle) and ball (point) (x is left side of ball, y is center)
        if (player.getScreenRect().contains(ball.getScreenRect().left, ball.getScreenRect().centerY())) {
            ball.moveRight();
        }
        //opponent (rectangle) and ball (point) (x is right side of ball, y is center)
        else if (opponent.getScreenRect().contains(ball.getScreenRect().right, ball.getScreenRect().centerY())) {
            ball.moveLeft();
        }
        ////////////////////////////////////////////////////////////////////////////////////
        //CHECK FOR WINNING CONDITION (can only reach here IF BALL HAVE NOT BOUNCED OFF BAT)
        ////////////////////////////////////////////////////////////////////////////////////
        //ball moved left passed player
        else if (ball.getScreenRect().left < player.getScreenRect().right) {
            Log.d(MainActivity.DEBUG_TAG, "LOST");
        }
        //ball moved right passed opponent
        else if (ball.getScreenRect().right > opponent.getScreenRect().left) {
            Log.d(MainActivity.DEBUG_TAG, "WON");
        }

        //////////////////////////////////////////////////
        //UPDATE movement for AI (ball and opponent's bat)
        //////////////////////////////////////////////////
        ball.update(elapsed);
        opponent.update(elapsed, ball);
    }

    public void render() {
        //synchronize?
        ////////////////////////////////////
        Canvas canvas = holder.lockCanvas();
        ////////////////////////////////////

        if (canvas != null) {
            //BACKGROUND (clear the canvas by painting the background white).
            canvas.drawColor(Color.WHITE);

            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            //@@@@@@@@@@@@@@@ DRAWING-RELATED-CODE @@@@@@@@@@@@@@@
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            drawText(canvas, "Tap screen to start...");



            //TODO:
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            holder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

    public void drawGame(Canvas canvas) {
        //SPRITES
        //////////////////////
        ball.draw(canvas);
        player.draw(canvas);
        opponent.draw(canvas);
        //////////////////////
    }

    public void drawText(Canvas canvas, String text) {
        //textPaint's Align is set to Align.CENTER, which means
        //its pivot-point is CENTER-OF-TEXT (not TOP-LEFT corner).
        canvas.drawText(text, canvas.getWidth()/2, canvas.getHeight()/2, textPaint);
    }

}