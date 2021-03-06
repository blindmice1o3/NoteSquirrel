package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps;

import android.content.res.Resources;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableTableTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.BedTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.ComputerTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.GameConsoleTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.GenericSolidTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.SignPostTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.TelevisionTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.solids2x2.ShippingBinTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.walkables.GenericWalkableTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.walkables.TallGrassTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.EggIncubatorTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.FeedingStallTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.FodderStashTile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Modified copy of Util class from previous IntelliJ project (desktop pc version)
 * of PocketCritters-SerialCritterNabbing.
 *
 * Modifying for this Android project (e.g. instead of loading from a
 * String path, need to load from an int resId).
 */
public class TileMapLoader {

    public static String loadFileAsString(Resources resources, int resId) {
        Log.d(MainActivity.DEBUG_TAG, "TileMapLoader.loadFileAsString(Context, int)");

        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        ///////////////////////////////////////////////////////////
        InputStream inputStream = resources.openRawResource(resId);
        ///////////////////////////////////////////////////////////

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = null;
            // As long as the next line is NOT null, append the
            // line (plus a newline character "\n") to StringBuilder.
            while( (line = bufferedReader.readLine()) != null ) {
                ///////////////////////
                sb.append(line + "\n");
                ///////////////////////
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.d(MainActivity.DEBUG_TAG, "Unable to read file (resId): " + resId);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(MainActivity.DEBUG_TAG, "Unable to close file (resId): " + resId);
            } catch (NullPointerException e) {
                e.printStackTrace();
                Log.d(MainActivity.DEBUG_TAG, "BufferedReader is null. File was probably never opened (resId): " + resId);
            }
        }

        return sb.toString();
    }


    /**
     * Responsible for converting the result of "loadFileAsString(Context, int)" into
     * a multi-dimensional array of Tile that represents the scene.
     *
     * In the "tiles_world_map.txt" file, we separated each tile sprite by
     * a "space" or a "newline character".
     *
     * We'll call "split()" on the String loaded from the "tiles_world_map.txt" file and
     * store each tile-sprite-representation in a String[] name "tokens".
     *
     * To split on any amount of white space, use "\\s+" as the argument to "split()".
     */
    public static Tile[][] convertStringToTiles(GameCartridge gameCartridge, String stringOfTiles) {
        Log.d(MainActivity.DEBUG_TAG, "TileMapLoader.convertStringToTiles(String)");

        String[] tokens = stringOfTiles.split("\\s+");

        // Set the width and height of our world, in terms of number of tiles (NOT PIXELS).
        int columns = Integer.parseInt( tokens[0] );
        int rows = Integer.parseInt( tokens[1] );

        // Now every single number after this is actual world-data. We have to read all
        // of this data into a Tile[][] and return it to the TileMap class.
        Tile[][] tiles = new Tile[rows][columns];

        // Here's where it gets a bit tricky. The "tokens" array is 1-D while "tiles" is 2-D.
        //
        // We have to convert the x and y for-loop position into the proper position of
        // our "tokens" array. Almost like "carrying-over-to-a-new-place-value" when adding
        // two numbers, "((y * columns) + x)" will appropriately convert the x and y of the
        // for-loop into the 1-dimensional array index.
        //
        // BUT ALSO HAVE TO "+ 2" because we are setting the first 2 elements in the
        // "tiles_world_map.txt" file (array indexes [0] and [1]) as width and height values.
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                //TelevisionTile
                if (tokens[((y * columns) + x) + 2].equals("3")) {
                    tiles[y][x] = new TelevisionTile(gameCartridge, x, y);
                }
                //ComputerTile
                else if (tokens[((y * columns) + x) + 2].equals("4")) {
                    tiles[y][x] = new ComputerTile(gameCartridge, x, y);
                }
                //GameConsoleTile
                else if (tokens[((y * columns) + x) + 2].equals("5")) {
                    tiles[y][x] = new GameConsoleTile(gameCartridge, x, y);
                }
                //TallGrassTile
                else if (tokens[((y * columns) + x) + 2].equals("2")) {
                    tiles[y][x] = new TallGrassTile(gameCartridge, x, y);
                }
                //PotTile
                else if (tokens[((y * columns) + x) + 2].equals("p")) {
                    tiles[y][x] = new GrowableTableTile(gameCartridge, x, y);
                }
                //BedTile
                else if (tokens[((y * columns) + x) + 2].equals("b")) {
                    tiles[y][x] = new BedTile(gameCartridge, x, y);
                }
                //SignPostTile
                else if (tokens[((y * columns) + x) + 2].equals("a")) {
                    tiles[y][x] = new SignPostTile(gameCartridge, x, y);
                }
                //EggIncubatorTile
                else if (tokens[((y * columns) + x) + 2].equals("g")) {
                    tiles[y][x] = new EggIncubatorTile(gameCartridge, x, y);
                }
                //FodderStashTile
                else if (tokens[((y * columns) + x) + 2].equals("h")) {
                    tiles[y][x] = new FodderStashTile(gameCartridge, x, y);
                }
                //FeedingStallTile
                else if (tokens[((y * columns) + x) + 2].equals("i")) {
                    tiles[y][x] = new FeedingStallTile(gameCartridge, x, y);
                }
                //ShippingBinTile
                else if (tokens[((y * columns) + x) + 2].equals("c")) {
                    tiles[y][x] = new ShippingBinTile(gameCartridge, x, y, ShippingBinTile.Quadrant.TOP_LEFT);
                } else if (tokens[((y * columns) + x) + 2].equals("d")) {
                    tiles[y][x] = new ShippingBinTile(gameCartridge, x, y, ShippingBinTile.Quadrant.TOP_RIGHT);
                } else if (tokens[((y * columns) + x) + 2].equals("e")) {
                    tiles[y][x] = new ShippingBinTile(gameCartridge, x, y, ShippingBinTile.Quadrant.BOTTOM_LFET);
                } else if (tokens[((y * columns) + x) + 2].equals("f")) {
                    tiles[y][x] = new ShippingBinTile(gameCartridge, x, y, ShippingBinTile.Quadrant.BOTTOM_RIGHT);
                }
                //GenericSolidTile
                else if (tokens[((y * columns) + x) + 2].equals("1")) {
                    tiles[y][x] = new GenericSolidTile(gameCartridge, x, y);
                }
                //GenericWalkableTile
                else if (tokens[((y * columns) + x) + 2].equals("0")) {
                    tiles[y][x] = new GenericWalkableTile(gameCartridge, x, y);
                }
                //NullTile (blank tile)
                else if (tokens[((y * columns) + x) + 2].equals("9")) {
                    tiles[y][x] = new GenericSolidTile(gameCartridge, x, y);
                }
            }
        }

        return tiles;
    }


/*
    // This second helper method is going to take in a String (like a String with the number 5 in it, "5") and it's
    // going to convert that into an integer value of 5 ("5" -> 5).
    public static int parseInt(String number) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < number.length(); i++) {
                if ( number.substring(i, (i+1)).equals(",") ) {
                    continue;
                } else if ( number.substring(i, (i+1)).equals("%") ) {
                    continue;
                } else if ( number.substring(i, (i+1)).equals("*") ) {
                    continue;
                } else if ( number.substring(i, (i+1)).equals("�") ) {
                    continue;
                } else {
                    sb.append(number.substring(i, (i+1)));
                }
            }

            int returner = 0;
            if (sb.toString().length() >= 1) {
                returner = Integer.parseInt(sb.toString());;
            }

            return returner;
        } catch (NumberFormatException ex) {
            // If we try to pass in a String that is not a number (like "ABC"), it'll throw an error.
            ex.printStackTrace();   // Print the error to the screen (that way we know something happened).
            // @@@@ But we also want our program to NOT crash, so we'll return 0 as default. @@@@
            return 0;
        }
    }
*/

}