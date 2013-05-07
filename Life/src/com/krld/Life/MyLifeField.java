package com.krld.Life;

import android.util.Log;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created with IntelliJ IDEA.
 * User: krld
 * Date: 07.05.13
 * Time: 21:05
 * To change this template use File | Settings | File Templates.
 */
public class MyLifeField extends Actor {
    public static final int DIMENSION = 400;
    private static final int SIZE = 2;
    private static final String DEBUG_TAG = "LIFE";
    private final TextureRegion region;
    private final short[][] field;
    private ShapeRenderer renderer;
    private long lastRenderTime;
    private short[][] oldField;

    public MyLifeField() {
        field = new short[DIMENSION][DIMENSION];
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
    }

    private void generateRandomField() {
        for (int x = 0; x < DIMENSION; x++) {
            for (int y = 0; y < DIMENSION; y++) {
                field[x][y] = (short) (Math.random() * 2d);
            }
        }
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        try {
            field[((int) (x / SIZE) + 1)][((int) (y / SIZE) + 1)] = 1;
            field[((int) (x / SIZE) + 1)][((int) (y / SIZE))] = 1;
            field[((int) (x / SIZE))][((int) (y / SIZE) + 1)] = 1;
            field[((int) (x / SIZE))][((int) (y / SIZE))] = 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(DEBUG_TAG, "HIT " + x + " " + y + " xy " + ((int) (x / SIZE)) + " " + ((int) (y / SIZE)));
            e.printStackTrace();
        }
        return super.hit(x, y, touchable);
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
        update(getDeltaTime());
        renderer.setColor(Color.GREEN);
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());
        renderer.translate(getX(), getY(), 0);

        renderer.begin(ShapeRenderer.ShapeType.FilledRectangle);
        for (int x = 0; x < DIMENSION; x++) {
            for (int y = 0; y < DIMENSION; y++) {
                if (field[x][y] == 1) {
                    renderer.filledRect(x * SIZE, y * SIZE, SIZE, SIZE);
                }
            }
        }
        renderer.end();

        batch.begin();
    }

    private void update(long deltaTime) {
        // generateRandomField();
        oldField = field.clone();

        short neighborhood;
        for (int x = 0; x < DIMENSION; x++) {
            for (int y = 0; y < DIMENSION; y++) {
                neighborhood = getNeighborhood(x, y);
                if (neighborhood == 3) {
                    field[x][y] = 1;
                } else if (neighborhood < 2 || neighborhood > 3) {
                    field[x][y] = 0;
                }
                // oldField[x][y] =1;
            }
        }
    }

    private short getNeighborhood(int x1, int y1) {
        short sum = (short) -oldField[x1][y1];
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
