package com.jackingaming.notesquirrel.sandbox.spritesheetverifier1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class SpriteSheetVerifier1Activity extends AppCompatActivity {

    private ImageView imageView;
    private Button buttonFrame;
    private Button buttonSet;

    private Bitmap imageSource;
    private Bitmap[][] spriteSheet;

    private int indexButtonX;
    private int indexButtonY;

    private int sheetTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprite_sheet_verifier1);

        ////////////////////////////
        Assets.init(getResources());
        ////////////////////////////

        indexButtonX = 0;
        indexButtonY = 0;

        sheetTracker = 0;

        //spriteSheet = Assets.items;
        //spriteSheet = Assets.tiles;
        spriteSheet = Assets.wintermute;


        //imageSource = BitmapFactory.decodeResource(getResources(), R.drawable.gbc_hm2_spritesheet_items);
        //imageSource = BitmapFactory.decodeResource(getResources(), R.drawable.pc_yoko_tileset1);
        Bitmap tooBigClippit = BitmapFactory.decodeResource(getResources(), R.drawable.pc_ms_office_clippit);
        Bitmap justRightClippit = Bitmap.createScaledBitmap(tooBigClippit, 500, 500, false);
        imageSource = justRightClippit;

        imageView = (ImageView) findViewById(R.id.imageview_sprite_sheet);
        imageView.setImageBitmap(imageSource);
        Log.d(MainActivity.DEBUG_TAG, "imageSource.getWidth(): " + imageSource.getWidth());
        Log.d(MainActivity.DEBUG_TAG, "imageSource.getHeight(): " + imageSource.getHeight());


        initButtonFrameOnClickListener();
        initButtonSetOnClickListener();


        initButtonsCroppedMapTester();
    }

    private int column = 3;
    private int row = 8;
    private void initButtonsCroppedMapTester() {
        Log.d(MainActivity.DEBUG_TAG, "SpriteSheetVerifier1Activity.initButtonsCroppedMapTester()");

        final TextView textViewDisplayWidthHeight = (TextView) findViewById(R.id.textview_display_column_row);

        Button buttonCenter = (Button) findViewById(R.id.button_center);
        buttonCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "Button clicked: center");
                if (sheetTracker == 0) {
                    //ms clippit
                    indexButtonX = 0;
                    indexButtonY = 0;

                    Bitmap tooBigClippit = BitmapFactory.decodeResource(getResources(), R.drawable.pc_ms_office_clippit);
                    Bitmap justRightClippit = Bitmap.createScaledBitmap(tooBigClippit, 500, 500, false);
                    imageSource = justRightClippit;
                    imageView.setImageBitmap(imageSource);

                    String text = String.format("(x: %d), (y: %d)", indexButtonX, indexButtonY);
                    textViewDisplayWidthHeight.setText(text);
                } else {
                    //pokemon world map
                    column = 3;
                    row = 8;

                    Bitmap croppedWorldMap = Assets.grabPokemonWorldMapSection(getResources(), column, row);
                    imageView.setImageBitmap(croppedWorldMap);

                    textViewDisplayWidthHeight.setText("column: " + column + ", row: " + row);
                }
            }
        });

        Button up = (Button) findViewById(R.id.button_up);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "Button clicked: up");
                if (sheetTracker == 0) {
                    //ms clippit
                    indexButtonY--;
                    if (indexButtonY < 0) {
                        indexButtonY = 0;
                    }

                    Bitmap newImage = Bitmap.createScaledBitmap(spriteSheet[indexButtonY][indexButtonX], 240, 240, false);
                    imageView.setImageBitmap(newImage);

                    String text = String.format("(x: %d), (y: %d)", indexButtonX, indexButtonY);
                    textViewDisplayWidthHeight.setText(text);
                } else {
                    //pokemon world map
                    row--;
                    if (row < 1) {
                        row = 1;
                    }

                    Bitmap croppedWorldMap = Assets.grabPokemonWorldMapSection(getResources(), column, row);
                    imageView.setImageBitmap(croppedWorldMap);

                    textViewDisplayWidthHeight.setText("column: " + column + ", row: " + row);
                }
            }
        });
        Button down = (Button) findViewById(R.id.button_down);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "Button clicked: down");
                if (sheetTracker == 0) {
                    //ms clippit
                    indexButtonY++;
                    if (indexButtonY > (spriteSheet.length - 1)) {
                        indexButtonY = (spriteSheet.length - 1);
                    }

                    Bitmap newImage = Bitmap.createScaledBitmap(spriteSheet[indexButtonY][indexButtonX], 240, 240, false);
                    imageView.setImageBitmap(newImage);

                    String text = String.format("(x: %d), (y: %d)", indexButtonX, indexButtonY);
                    textViewDisplayWidthHeight.setText(text);
                } else {
                    //pokemon world map
                    row++;
                    if (row > 10) {
                        row = 10;
                    }

                    Bitmap croppedWorldMap = Assets.grabPokemonWorldMapSection(getResources(), column, row);
                    imageView.setImageBitmap(croppedWorldMap);

                    textViewDisplayWidthHeight.setText("column: " + column + ", row: " + row);
                }
            }
        });
        Button left = (Button) findViewById(R.id.button_left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "Button clicked: left");
                if (sheetTracker == 0) {
                    //ms clippit
                    indexButtonX--;
                    if (indexButtonX < 0) {
                        indexButtonX = 0;
                    }

                    Bitmap newImage = Bitmap.createScaledBitmap(spriteSheet[indexButtonY][indexButtonX], 240, 240, false);
                    imageView.setImageBitmap(newImage);

                    String text = String.format("(x: %d), (y: %d)", indexButtonX, indexButtonY);
                    textViewDisplayWidthHeight.setText(text);
                } else {
                    //pokemon world map
                    column--;
                    if (column < 1) {
                        column = 1;
                    }

                    Bitmap croppedWorldMap = Assets.grabPokemonWorldMapSection(getResources(), column, row);
                    imageView.setImageBitmap(croppedWorldMap);

                    textViewDisplayWidthHeight.setText("column: " + column + ", row: " + row);
                }
            }
        });
        Button right = (Button) findViewById(R.id.button_right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "Button clicked: right");
                if (sheetTracker == 0) {
                    //ms clippit
                    indexButtonX++;
                    if (indexButtonX > (spriteSheet[indexButtonY].length - 1)) {
                        indexButtonX = (spriteSheet[indexButtonY].length - 1);
                    }

                    Bitmap newImage = Bitmap.createScaledBitmap(spriteSheet[indexButtonY][indexButtonX], 240, 240, false);
                    imageView.setImageBitmap(newImage);

                    String text = String.format("(x: %d), (y: %d)", indexButtonX, indexButtonY);
                    textViewDisplayWidthHeight.setText(text);
                } else {
                    //pokemon world map
                    column++;
                    if (column > 10) {
                        column = 10;
                    }

                    Bitmap croppedWorldMap = Assets.grabPokemonWorldMapSection(getResources(), column, row);
                    imageView.setImageBitmap(croppedWorldMap);

                    textViewDisplayWidthHeight.setText("column: " + column + ", row: " + row);
                }
            }
        });
    }

    private void initButtonFrameOnClickListener() {
        buttonFrame = (Button) findViewById(R.id.button_frame_sprite_sheet);

        buttonFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "SpriteSheetVerifier1Activity.buttonFrame's OnClickListener.onClick(View)");

                //How to set by pixel:
                //imageView.getLayoutParams().width = (spriteSheet[indexButtonY][indexButtonX].getWidth() * 6);
                //imageView.getLayoutParams().height = (spriteSheet[indexButtonY][indexButtonX].getHeight() * 6);
                //////////////////////////
                //imageView.requestLayout();
                //////////////////////////

                //How to set by dp:
                //int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixel, getResources().getDisplayMetrics());
                //imageView.getLayoutParams().height = dimensionInDp;
                //imageView.getLayoutParams().width = dimensionInDp;
                //////////////////////////
                //imageView.requestLayout();
                //////////////////////////

                indexButtonX++;
                if (indexButtonX == spriteSheet[indexButtonY].length) {
                    indexButtonX = 0;
                    indexButtonY++;

                    if (indexButtonY == spriteSheet.length) {
                        indexButtonY = 0;
                    }
                }

                Bitmap newImage = Bitmap.createScaledBitmap(spriteSheet[indexButtonY][indexButtonX], 240, 240, false);
                imageView.setImageBitmap(newImage);

                /////////////////////////////////////////////////////////////////////
                TextView textView = (TextView) findViewById(R.id.textview_sprite_sheet);
                TextView textViewDisplayColumnRow = (TextView) findViewById(R.id.textview_display_column_row);
                if (sheetTracker == 0) {
                    textView.setText("Wintermute (homage to William Gibson)");
                    String text = String.format("(x: %d), (y: %d)", indexButtonX, indexButtonY);
                    textViewDisplayColumnRow.setText(text);
                } else {
                    textView.setText("");
                }
                /////////////////////////////////////////////////////////////////////

                /*
                int[][] test = {
                        {1, 2},
                        {3, 4},
                        {5, 6},
                        {7, 8}
                };
                Log.d(MainActivity.DEBUG_TAG, "test.length, test[0].length: " + test.length + ", " + test[0].length);
                */

                Log.d(MainActivity.DEBUG_TAG,
                        "imageView.getWidth(), imageView.getHeight(): " +
                                imageView.getWidth() + ", " + imageView.getHeight());

                Log.d(MainActivity.DEBUG_TAG,
                        "imageView.getDrawable().getIntrinsicWidth(), imageView.getDrawable().getIntrinsicHeight(): " +
                                imageView.getDrawable().getIntrinsicWidth() + ", " + imageView.getDrawable().getIntrinsicHeight());
            }
        });
    }

    private void initButtonSetOnClickListener() {
        buttonSet = (Button) findViewById(R.id.button_set_sprite_sheet);

        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetTracker++;
                if (sheetTracker > 4) {
                    sheetTracker = 0;
                }

                indexButtonX = 0;
                indexButtonY = 0;

                switch (sheetTracker) {
                    case 0:
                        Bitmap tooBigClippit = BitmapFactory.decodeResource(getResources(), R.drawable.pc_ms_office_clippit);
                        Bitmap justRightClippit = Bitmap.createScaledBitmap(tooBigClippit, 500, 500, false);
                        imageSource = justRightClippit;
                        imageView.setImageBitmap(imageSource);

                        spriteSheet = Assets.wintermute;
                        break;
                    case 1:
                        imageSource = BitmapFactory.decodeResource(getResources(), R.drawable.corgi_crusade_editted);
                        imageView.setImageBitmap(imageSource);

                        spriteSheet = Assets.corgiCrusade;
                        break;
                    case 2:
                        imageSource = BitmapFactory.decodeResource(getResources(), R.drawable.snes_breath_of_fire_gobi);
                        imageView.setImageBitmap(imageSource);

                        spriteSheet = Assets.gobi;
                        break;
                    case 3:
                        imageSource = BitmapFactory.decodeResource(getResources(), R.drawable.gbc_hm2_spritesheet_items);
                        imageView.setImageBitmap(imageSource);

                        spriteSheet = Assets.items;
                        break;
                    case 4:
                        imageSource = BitmapFactory.decodeResource(getResources(), R.drawable.pc_yoko_tileset);
                        imageView.setImageBitmap(imageSource);

                        spriteSheet = Assets.tiles;
                        break;
                }
            }
        });
    }

}