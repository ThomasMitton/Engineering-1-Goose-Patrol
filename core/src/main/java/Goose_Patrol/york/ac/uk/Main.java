package Goose_Patrol.york.ac.uk;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private Goose goose;

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player("Player.png", new Vector2(140, 210), 200f);
        goose = new Goose("Goose.png", new Vector2());
    }

    @Override
    public void render() {
        player.update();
        goose.update(player.getVector());
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        batch.begin();
        player.render(batch);
        goose.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        goose.dispose();
    }
}
