package Goose_Patrol.york.ac.uk;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private Goose goose;
    private Texture map;
    private OrthographicCamera camera;

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player("Player.png", 140f, 210f, 200f);
        goose = new Goose("Goose.png", new Vector2());
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        map = new Texture(Gdx.files.internal("map.png"));
    }

    @Override
    public void render() {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        player.update();
        goose.update(player.getVector());

        camera.position.set(
            MathUtils.clamp(
                player.getVector().x,
                camera.viewportWidth / 2,
                map.getWidth() - camera.viewportWidth / 2
            ),
            MathUtils.clamp(
                player.getVector().y,
                camera.viewportHeight / 2,
                map.getHeight() - camera.viewportHeight / 2
            ),
            0
        );

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(map, 0, 0, 2270, 1182);

        batch.end();
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
        map.dispose();
    }
}
