package screens;

import Goose_Patrol.york.ac.uk.GoosePatrol;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenu extends ScreenAdapter {
    private GoosePatrol game;
    private Texture startMenu;

    public MainMenu(GoosePatrol game) {
        this.game = game;
    }

    @Override
    public void show() {
        startMenu = new Texture(Gdx.files.internal("start_menu.png"));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        game.batch.begin();
        game.batch.draw(startMenu, 0, 0);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }
}
