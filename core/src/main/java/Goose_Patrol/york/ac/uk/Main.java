package Goose_Patrol.york.ac.uk;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import static com.badlogic.gdx.Gdx.app;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private Goose goose;
    private Texture map;
    private OrthographicCamera camera;
    private BitmapFont font;
    private String myText;
    private Timer timer;
    private String displayTime;


    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player("Player.png", new Vector2(140, 210), 200f);
        goose = new Goose("Goose.png", new Vector2());
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        font = new BitmapFont(Gdx.files.internal("Open Sans.fnt"));
        displayTime = Timer.refreshTimer();

        map = new Texture(Gdx.files.internal("map.png"));
    }

    @Override
    public void render() {
        if (player.hitbox.overlaps(goose.hitbox)) {
            app.log("Collision","You died!");
            // TODO: Decrease health when hit
        }

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
        displayTime = Timer.refreshTimer();
        //font.draw(batch, displayTime, Gdx.graphics.getWidth()-200, Gdx.graphics.getHeight()-50);
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
