package com.krld.Life;

import android.util.Log;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.lang.reflect.Array;

/**
 * Created with IntelliJ IDEA.
 * User: krld
 * Date: 07.05.13
 * Time: 21:05
 * To change this template use File | Settings | File Templates.
 */
public class MyLifeField extends Actor {
    public static final int HEIGHT_GAME_WINDOW = 800;
    public static final int WIDTH_GAME_WINDOW = 1040;
    private static final int SIZE = 4;
    public static final int DIMENSION_X = WIDTH_GAME_WINDOW / SIZE;
    public static final int DIMENSION_Y = HEIGHT_GAME_WINDOW / SIZE;
    public static final int SUB_BUTTON_X = SIZE * DIMENSION_X + 100;
    public static final int SEPARATE_LINE_X = SIZE * DIMENSION_X;
    private static final String DEBUG_TAG = "LIFE";
    public static final int HEIGHT_WINDOW = 800;
    public static final float HEIGHT_ONE_MENU_BUTTON = (1f / 5f) * HEIGHT_WINDOW;
    public static final int WIDTH_SEPARATE_LINE = 5;
    public static final int WIDTH_BUTTON_SEPARATE_LINE = 200;
    public static final int HEIGHT_SEPARATE_BUTTON_LINE = 5;
    private static final float WIDTH_SUB_BUTTON_LINE = 5;
    public static final int UPDATE_DELAY = 50;
    private final TextureRegion region;
    private byte[][] field;
    private ShapeRenderer renderer;
    private long lastRenderTime;
    private byte[][] oldField;
    private int createType;
    private boolean run;
    private long lastRunStop;
    private long lastUpdate;

    public MyLifeField() {
        createType = 1;
        run = true;
        field = new byte[DIMENSION_X][DIMENSION_Y];
        // generateRandomField();
        region = new TextureRegion();
        renderer = new ShapeRenderer();
        this.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("down");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("up");
            }
        });
        lastRenderTime = System.currentTimeMillis();
        lastRunStop = System.currentTimeMillis();
        lastUpdate = System.currentTimeMillis();
    }

    private void generateRandomField() {
        for (int x = 0; x < DIMENSION_Y; x++) {
            for (int y = 0; y < DIMENSION_Y; y++) {
                field[x][y] = (byte) (Math.random() * 2d);
            }
        }
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (x > SIZE * DIMENSION_X) {
            if (y < HEIGHT_ONE_MENU_BUTTON) {
                if (x > SUB_BUTTON_X) {
                    stopRun();
                } else {
                    clearField();
                }
            } else if (y < HEIGHT_ONE_MENU_BUTTON * 2) {
                if (x > SUB_BUTTON_X) {
                    setCreateType(9);
                } else {
                    setCreateType(1);
                }
            } else if (y < HEIGHT_ONE_MENU_BUTTON * 3) {
                if (x > SUB_BUTTON_X) {
                    setCreateType(7);
                } else {
                    setCreateType(2);
                }
            } else if (y < HEIGHT_ONE_MENU_BUTTON * 4) {
                if (x > SUB_BUTTON_X) {
                    setCreateType(6);
                } else {
                    setCreateType(3);
                }
            } else if (y < HEIGHT_ONE_MENU_BUTTON * 5) {
                if (x > SUB_BUTTON_X) {
                    setCreateType(5);
                } else {
                    setCreateType(4);
                }
            }

        } else {
            createCells(x, y);
        }
        return super.hit(x, y, touchable);
    }

    private void stopRun() {
        if (System.currentTimeMillis() - lastRunStop > 1000) {
            lastRunStop = System.currentTimeMillis();
            run = !run;
        }
    }

    private void createCells(float x, float y) {
        try {
            if (createType == 1) {                             //block
                field[((int) (x / SIZE) + 1)][((int) (y / SIZE) + 1)] = 1;
                field[((int) (x / SIZE) + 1)][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE))][((int) (y / SIZE) + 1)] = 1;
                field[((int) (x / SIZE))][((int) (y / SIZE))] = 1;
            } else if (createType == 2) { // glider
                field[((int) (x / SIZE))][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE))][((int) (y / SIZE)) + 1] = 1;
                field[((int) (x / SIZE))][((int) (y / SIZE)) + 2] = 1;
                field[((int) (x / SIZE))][((int) (y / SIZE)) + 2] = 1;
                field[((int) (x / SIZE)) - 1][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) - 2][((int) (y / SIZE)) + 1] = 1;
            } else if (createType == 3) {                //LWSS
                field[((int) (x / SIZE))][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE))][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) - 1][((int) (y / SIZE)) + 3] = 1;
                field[((int) (x / SIZE))][((int) (y / SIZE)) + 2] = 1;
                field[((int) (x / SIZE))][((int) (y / SIZE)) + 1] = 1;


                field[((int) (x / SIZE)) - 1][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) - 2][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) - 3][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) - 4][((int) (y / SIZE)) + 1] = 1;
                field[((int) (x / SIZE)) - 4][((int) (y / SIZE)) + 3] = 1;
            } else if (createType == 4) {       // blinky
                field[((int) (x / SIZE) + 1)][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE) - 1)][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE))][((int) (y / SIZE))] = 1;
            } else if (createType == 5) {       // die hard
                field[((int) (x / SIZE))][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) - 1][((int) (y / SIZE)) + 2] = 1;
                field[((int) (x / SIZE)) - 1][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) - 2][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) - 6][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) - 6][((int) (y / SIZE)) + 1] = 1;
                field[((int) (x / SIZE)) - 7][((int) (y / SIZE)) + 1] = 1;
            } else if (createType == 6) {       // R pentomino
                field[((int) (x / SIZE))][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE))][((int) (y / SIZE)) + 1] = 1;
                field[((int) (x / SIZE)) - 1][((int) (y / SIZE)) + 1] = 1;
                field[((int) (x / SIZE))][((int) (y / SIZE)) + 2] = 1;
                field[((int) (x / SIZE)) + 1][((int) (y / SIZE)) + 2] = 1;
            } else if (createType == 7) {       //
                field[((int) (x / SIZE))][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +1][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +2][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +3][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +4][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +5][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +6][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +7][((int) (y / SIZE))] = 1;

                field[((int) (x / SIZE)) +9][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +10][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +11][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +12][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +13][((int) (y / SIZE))] = 1;

                field[((int) (x / SIZE)) +17][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +18][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +19][((int) (y / SIZE))] = 1;

                field[((int) (x / SIZE)) +26][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +27][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +28][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +29][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +30][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +31][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +32][((int) (y / SIZE))] = 1;

                field[((int) (x / SIZE)) +34][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +35][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +36][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +37][((int) (y / SIZE))] = 1;
                field[((int) (x / SIZE)) +38][((int) (y / SIZE))] = 1;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(DEBUG_TAG, "HIT " + x + " " + y + " xy " + ((int) (x / SIZE)) + " " + ((int) (y / SIZE)));
            e.printStackTrace();
        }
    }

    private void setCreateType(int i) {

        this.createType = i;
    }


    private void clearField() {
        field = new byte[DIMENSION_X][DIMENSION_Y];

    }

    private long getDeltaTime() {
        long currentTime = System.currentTimeMillis();
        long delta = currentTime - lastRenderTime;
        lastRenderTime = currentTime;
        return delta;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        batch.end();
        if (run) {
            update(getDeltaTime());
        }
        renderer.setColor(Color.GREEN);
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());
        renderer.translate(getX(), getY(), 0);

        renderer.begin(ShapeRenderer.ShapeType.FilledRectangle);
        for (int x = 0; x < DIMENSION_X; x++) {
            for (int y = 0; y < DIMENSION_Y; y++) {
                if (field[x][y] == 1) {
                    renderer.filledRect(x * SIZE, y * SIZE, SIZE, SIZE);
                }
            }
        }
        drawSeparateLines(batch, parentAlpha);
        renderer.end();

        batch.begin();
    }

    private void drawSeparateLines(SpriteBatch batch, float parentAlpha) {
        renderer.setColor(Color.CYAN);
        renderer.filledRect(SEPARATE_LINE_X, 0, WIDTH_SEPARATE_LINE, HEIGHT_WINDOW);
        renderer.setColor(Color.LIGHT_GRAY);
        renderer.filledRect(SUB_BUTTON_X, 0, WIDTH_SEPARATE_LINE, HEIGHT_WINDOW);
        renderer.filledRect(SEPARATE_LINE_X, HEIGHT_ONE_MENU_BUTTON, WIDTH_BUTTON_SEPARATE_LINE, HEIGHT_SEPARATE_BUTTON_LINE);
        renderer.filledRect(SEPARATE_LINE_X, HEIGHT_ONE_MENU_BUTTON * 2, WIDTH_BUTTON_SEPARATE_LINE, HEIGHT_SEPARATE_BUTTON_LINE);
        renderer.filledRect(SEPARATE_LINE_X, HEIGHT_ONE_MENU_BUTTON * 3, WIDTH_BUTTON_SEPARATE_LINE, HEIGHT_SEPARATE_BUTTON_LINE);
        renderer.filledRect(SEPARATE_LINE_X, HEIGHT_ONE_MENU_BUTTON * 4, WIDTH_BUTTON_SEPARATE_LINE, HEIGHT_SEPARATE_BUTTON_LINE);
    }

    private void update(long deltaTime) {
        // generateRandomField();
        if (System.currentTimeMillis() - lastUpdate < UPDATE_DELAY) {
            return;
        }
        oldField = field;
        field = new byte[DIMENSION_X][DIMENSION_Y];
        short neighborhood;
        for (int x = 0; x < DIMENSION_X; x++) {
            for (int y = 0; y < DIMENSION_Y; y++) {
                neighborhood = getNeighborhood(x, y);
                if (neighborhood == 3) {
                    field[x][y] = 1;
                } else if (neighborhood == 2) {
                    field[x][y] = oldField[x][y];
                } else if (neighborhood < 2 || neighborhood > 3) {
                    field[x][y] = 0;
                }
                // oldField[x][y] =1;
            }
        }
        lastUpdate = System.currentTimeMillis();
    }

    private short getNeighborhood(int x1, int y1) {
        short sum = (short) -oldField[x1][y1];
        if (sum == -1) {
            System.out.println("");
        }
        for (int x = x1 - 1; x <= x1 + 1; x++) {
            for (int y = y1 - 1; y <= y1 + 1; y++) {
                try {
                    sum += oldField[x][y];
                } catch (ArrayIndexOutOfBoundsException e) {
                    //TODO fix borders
                }
            }
        }
        return sum;
    }
}
