package screens;

import Goose_Patrol.york.ac.uk.GoosePatrol;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenu extends ScreenAdapter {
    private GoosePatrol game;
    private Texture startMenu;
    private FitViewport viewport;

    public MainMenu(GoosePatrol game) {
        this.game = game;
    }

    @Override
    public void show() {
        startMenu = new Texture(Gdx.files.internal("start_menu.png"));
        viewport = new FitViewport(800,450);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        viewport.apply();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(startMenu, 0, 0,viewport.getWorldWidth(),viewport.getWorldHeight());
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
