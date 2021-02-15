package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.TileManager;

public abstract class Creature extends Entity {
    public enum Direction { UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT; }

    protected Direction direction;
    protected float moveSpeed;
    protected float xMove;
    protected float yMove;

    public Creature(int xSpawn, int ySpawn) {
        super(xSpawn, ySpawn);
        direction = Direction.RIGHT;
        moveSpeed = 4f;
        xMove = 0f;
        yMove = 0f;
    }

    public void move(Direction direction) {
        this.direction = direction;

        TileManager tileManager = game.getSceneManager().getCurrentScene().getTileManager();
        int xFutureLeft = 0;
        int xFutureRight = 0;
        int yFutureTop = 0;
        int yFutureBottom = 0;
        switch (direction) {
            case LEFT:
                xMove = -moveSpeed; // NEGATIVE
                // to_move: LEFT
                xFutureLeft = (int) (x + xMove);
                xFutureRight = (int) ((x + width) + xMove - 1);
                yFutureTop = (int) (y);
                yFutureBottom = (int) (y + height - 1);

                // CHECKING tile collision: LEFT-TOP and LEFT-BOTTOM
                if (!tileManager.isSolid(xFutureLeft, yFutureTop) &&
                        !tileManager.isSolid(xFutureLeft, yFutureBottom)) {
                    // CHECKING entity collision AND item collision
                    if (!checkEntityCollision(xMove, 0f) &&
                            !checkItemCollision(xMove, 0f, false)) {

                        if (checkTransferPointCollision(xMove, 0f)) {
                            return; // Entity.respondToTransferPointCollision(String key) was triggered.
                        }

                        ///////////
                        x += xMove;
                        ///////////
                    }
                }
                break;
            case RIGHT:
                xMove = moveSpeed; // POSITIVE
                // to_move: RIGHT
                xFutureLeft = (int) (x + xMove);
                xFutureRight = (int) ((x + width) + xMove - 1);
                yFutureTop = (int) (y);
                yFutureBottom = (int) (y + height - 1);

                // CHECKING tile collision: RIGHT-TOP and RIGHT-BOTTOM
                if (!tileManager.isSolid(xFutureRight, yFutureTop) &&
                        !tileManager.isSolid(xFutureRight, yFutureBottom)) {
                    // CHECKING entity collision AND item collision
                    if (!checkEntityCollision(xMove, 0f) &&
                            !checkItemCollision(xMove, 0f, false)) {

                        if (checkTransferPointCollision(xMove, 0f)) {
                            return; // Entity.respondToTransferPointCollision(String key) was triggered.
                        }

                        ///////////
                        x += xMove;
                        ///////////
                    }
                }
                break;
            case UP:
                yMove = -moveSpeed; // NEGATIVE
                // to_move: UP
                yFutureTop = (int) (y + yMove);
                yFutureBottom = (int) ((y + height) + yMove - 1);
                xFutureLeft = (int) (x);
                xFutureRight = (int) (x + width - 1);

                // CHECKING tile collision: TOP-LEFT and TOP-RIGHT
                if (!tileManager.isSolid(xFutureLeft, yFutureTop) &&
                        !tileManager.isSolid(xFutureRight, yFutureTop)) {
                    // CHECKING entity collision AND item collision
                    if (!checkEntityCollision(0f, yMove) &&
                            !checkItemCollision(0f, yMove, false)) {

                        if (checkTransferPointCollision(0f, yMove)) {
                            return; // Entity.respondToTransferPointCollision(String key) was triggered.
                        }

                        ///////////
                        y += yMove;
                        ///////////
                    }
                }
                break;
            case DOWN:
                yMove = moveSpeed; // POSITIVE
                // to_move: DOWN
                yFutureTop = (int) (y + yMove);
                yFutureBottom = (int) ((y + height) + yMove - 1);
                xFutureLeft = (int) (x);
                xFutureRight = (int) (x + width - 1);

                // CHECKING tile collision: BOTTOM-LEFT and BOTTOM-RIGHT
                if (!tileManager.isSolid(xFutureLeft, yFutureBottom) &&
                        !tileManager.isSolid(xFutureRight, yFutureBottom)) {
                    // CHECKING entity collision AND item collision
                    if (!checkEntityCollision(0f, yMove) &&
                            !checkItemCollision(0f, yMove, false)) {

                        if (checkTransferPointCollision(0f, yMove)) {
                            return; // Entity.respondToTransferPointCollision(String key) was triggered.
                        }

                        ///////////
                        y += yMove;
                        ///////////
                    }
                }
                break;
            case UP_LEFT:
                xMove = -moveSpeed; // NEGATIVE
                yMove = -moveSpeed; // NEGATIVE
                // to_move: UP_LEFT
                xFutureLeft = (int) (x + xMove);
                xFutureRight = (int) ((x + width) + xMove - 1);
                yFutureTop = (int) (y + yMove);
                yFutureBottom = (int) ((y + height) + yMove - 1);

                // CHECKING tile collision: LEFT-TOP and LEFT-BOTTOM and RIGHT-TOP
                if (!tileManager.isSolid(xFutureLeft, yFutureTop) &&
                        !tileManager.isSolid(xFutureLeft, yFutureBottom) &&
                        !tileManager.isSolid(xFutureRight, yFutureTop)) {
                    // CHECKING entity collision AND item collision
                    if (!checkEntityCollision(xMove, yMove) &&
                            !checkItemCollision(xMove, yMove, false)) {

                        if (checkTransferPointCollision(xMove, yMove)) {
                            return; // Entity.respondToTransferPointCollision(String key) was triggered.
                        }

                        ///////////
                        x += xMove;
                        y += yMove;
                        ///////////
                    }
                }
                break;
            case UP_RIGHT:
                xMove = moveSpeed; // POSITIVE
                yMove = -moveSpeed; // NEGATIVE
                // to_move: UP_RIGHT
                xFutureLeft = (int) (x + xMove);
                xFutureRight = (int) ((x + width) + xMove - 1);
                yFutureTop = (int) (y + yMove);
                yFutureBottom = (int) ((y + height) + yMove - 1);

                // CHECKING tile collision: RIGHT-TOP and RIGHT-BOTTOM and LEFT-TOP
                if (!tileManager.isSolid(xFutureRight, yFutureTop) &&
                        !tileManager.isSolid(xFutureRight, yFutureBottom) &&
                        !tileManager.isSolid(xFutureLeft, yFutureTop)) {
                    // CHECKING entity collision AND item collision
                    if (!checkEntityCollision(xMove, yMove) &&
                            !checkItemCollision(xMove, yMove, false)) {

                        if (checkTransferPointCollision(xMove, yMove)) {
                            return; // Entity.respondToTransferPointCollision(String key) was triggered.
                        }

                        ///////////
                        x += xMove;
                        y += yMove;
                        ///////////
                    }
                }
                break;
            case DOWN_LEFT:
                xMove = -moveSpeed; // NEGATIVE
                yMove = moveSpeed; // POSITIVE
                // to_move: DOWN_LEFT
                xFutureLeft = (int) (x + xMove);
                xFutureRight = (int) ((x + width) + xMove - 1);
                yFutureTop = (int) (y + yMove);
                yFutureBottom = (int) ((y + height) + yMove - 1);

                // CHECKING tile collision: LEFT-TOP and LEFT-BOTTOM and RIGHT_BOTTOM
                if (!tileManager.isSolid(xFutureLeft, yFutureTop) &&
                        !tileManager.isSolid(xFutureLeft, yFutureBottom) &&
                        !tileManager.isSolid(xFutureRight, yFutureBottom)) {
                    // CHECKING entity collision AND item collision
                    if (!checkEntityCollision(xMove, yMove) &&
                            !checkItemCollision(xMove, yMove, false)) {

                        if (checkTransferPointCollision(xMove, yMove)) {
                            return; // Entity.respondToTransferPointCollision(String key) was triggered.
                        }

                        ///////////
                        x += xMove;
                        y += yMove;
                        ///////////
                    }
                }
                break;
            case DOWN_RIGHT:
                xMove = moveSpeed; // POSITIVE
                yMove = moveSpeed; // POSITIVE
                // to_move: DOWN_RIGHT
                xFutureLeft = (int) (x + xMove);
                xFutureRight = (int) ((x + width) + xMove - 1);
                yFutureTop = (int) (y + yMove);
                yFutureBottom = (int) ((y + height) + yMove - 1);

                // CHECKING tile collision: RIGHT-TOP and RIGHT-BOTTOM and LEFT-BOTTOM
                if (!tileManager.isSolid(xFutureRight, yFutureTop) &&
                        !tileManager.isSolid(xFutureRight, yFutureBottom) &&
                        !tileManager.isSolid(xFutureLeft, yFutureBottom)) {
                    // CHECKING entity collision AND item collision
                    if (!checkEntityCollision(xMove, yMove) &&
                            !checkItemCollision(xMove, yMove, false)) {

                        if (checkTransferPointCollision(xMove, yMove)) {
                            return; // Entity.respondToTransferPointCollision(String key) was triggered.
                        }

                        ///////////
                        x += xMove;
                        y += yMove;
                        ///////////
                    }
                }
                break;
        }
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}