package com.krld.Life;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created with IntelliJ IDEA.
 * User: krld
 * Date: 07.05.13
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
public class Game implements ApplicationListener {
    private Stage stage;
    // private GameScreen game;

    @Override
    public void create() {
    //    game = new GameScreen();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(new MyLifeField());
    }

    @Override
    public void resize(int i, int i2) {
        stage.setViewport(i, i2, true);
    }


    @Override
    public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }



    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
