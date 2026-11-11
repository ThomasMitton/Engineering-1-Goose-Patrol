package Goose_Patrol.york.ac.uk;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import static com.badlogic.gdx.Gdx.app;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private Array<Goose> gooseArray;
    private Texture map;
    private OrthographicCamera camera;
    private OrthographicCamera uiCamera;
    private BitmapFont font;
    private String myText;
    private Timer timer;
    private String displayTime;
    private HealthBar health_bar;
    private ItemCounter ItemCounter;
    //private GameOver GameOver;
    private Stage inventory;
    private Texture inventoryImage;
    private Wall wall;
    private float duckTimer;

    @Override
    public void create() {
        map = new Texture(Gdx.files.internal("map.png"));
        gooseArray = new Array<>();
        batch = new SpriteBatch();
        player = new Player("Player.png", new Vector2(140, 210), 200f, map.getWidth(), map.getHeight());
        wall = new Wall("Wall.png", new Vector2(300, 300), 100, 200);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        font = new BitmapFont(Gdx.files.internal("Open Sans.fnt"));
        displayTime = Timer.refreshTimer();

        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(
            false,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        map = new Texture(Gdx.files.internal("map.png"));


        health_bar = new HealthBar();
        ItemCounter = new ItemCounter(batch);

        inventory = new Stage(new ScreenViewport());
        inventoryImage = new Texture(Gdx.files.internal("inventory.png"));
        Image image1 = new Image(inventoryImage);
        image1.setSize(127, 63);
        image1.setPosition(((float) Gdx.graphics.getWidth() / 2) - 64, 10);
        inventory.addActor(image1);
    }

    @Override
    public void render() {
        for (Goose goose : gooseArray) {
            if (player.hitbox.overlaps(goose.hitbox)) {
                gooseArray.removeValue(goose, false);
                health_bar.health = health_bar.health - 1;
            }
        }

        if (player.hitbox.overlaps(wall.hitbox)) {
            app.log("Collision", "WALL");
            wall.checkCollisions(player.pos);
        }
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        player.update();
        for (Goose goose : gooseArray) {
            goose.update(player.getVector());
        }

        float delta = Gdx.graphics.getDeltaTime();
        duckTimer += delta;
        if (duckTimer >1f) {
            duckTimer = 0;
            addGoose();
            app.log("Goose added","big if true");
        }

        camera.position.set(
            MathUtils.clamp(MathUtils.clamp(
                camera.position.x,
                player.getVector().x - (camera.viewportWidth / 3) + player.texture.getWidth(),
                player.getVector().x + (camera.viewportWidth / 3)
            ), camera.viewportWidth / 2, map.getWidth() - camera.viewportWidth / 2),

            MathUtils.clamp(MathUtils.clamp(
                camera.position.y,
                player.getVector().y - (camera.viewportHeight / 3) + player.texture.getHeight(),
                player.getVector().y + (camera.viewportHeight / 3)
            ), camera.viewportHeight / 2, map.getHeight() - camera.viewportHeight / 2),
            0
        );

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(map, 0, 0, 2270, 1182);
        displayTime = Timer.refreshTimer();
        player.render(batch);
        for (Goose goose : gooseArray) {
            goose.render(batch);
        }
        wall.render(batch);

        batch.end();

        uiCamera.update();
        batch.setProjectionMatrix(uiCamera.combined);

        batch.begin();

        font.draw(
            batch,
            displayTime,
            Gdx.graphics.getWidth() - 200,
            Gdx.graphics.getHeight() - 50
        );

        batch.end();

        health_bar.display();

        batch.begin();

        ItemCounter.display();

        inventory.act();
        inventory.draw();


        batch.end();

        //GameOver.display();
    }

    @Override
    public void resize(int width, int height) {
        uiCamera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        for (Goose goose : gooseArray) {
            goose.dispose();
        }
        map.dispose();
        wall.dispose();
        font.dispose();
        health_bar.dispose();
        ItemCounter.dispose();
        inventory.dispose();
        inventoryImage.dispose();
    }

    private void addGoose() {
        gooseArray.add(new Goose("Goose.png", new Vector2((MathUtils.random(0f, map.getWidth())),(MathUtils.random(0f, map.getHeight())))));
    }
}
