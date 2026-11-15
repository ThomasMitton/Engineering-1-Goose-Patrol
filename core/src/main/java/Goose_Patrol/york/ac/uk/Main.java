package Goose_Patrol.york.ac.uk;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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
    public BreadPoints breadPoints;
    public static BreadPoints staticBreadPoints;
    private int pointCounter;
    public Sound sound;
    private GameOverScreen GameOverScreen;
    private boolean gameOver = false;

    private ItemCounter ItemCounter;
    private GameOver GameOver;
    private Stage inventory;
    private Texture inventoryImage;
    private Wall wall;
    private float duckTimer;
    private ResetGame resetGame;
    private Toaster toaster;
    private ShapeRenderer shapeRenderer;

    private boolean toastGameActive = false;
    private float markerPosition = 0;
    private boolean movingRight = true;
    private float toasterSpeed = 400f;



    @Override
    public void create() {
        GameOverScreen = new GameOverScreen(this);
        map = new Texture(Gdx.files.internal("map.png"));
        gooseArray = new Array<>();
        toaster = new Toaster("toaster.png", new Vector2(900,300),39, 31);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        player = new Player("Player.png", new Vector2(140, 210), 200f, map.getWidth(), map.getHeight());
        wall = new Wall("Wall.png", new Vector2(300, 300), 100, 200);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        font = new BitmapFont(Gdx.files.internal("Open Sans.fnt"));
        displayTime = Timer.refreshTimer();
        pointCounter = BreadPoints.getCounter();
        sound = Gdx.audio.newSound(Gdx.files.internal("beep.mp3"));

        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(
            false,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        map = new Texture(Gdx.files.internal("map.png"));



        health_bar = new HealthBar();

        ItemCounter = new ItemCounter(batch);
        GameOver = new GameOver(health_bar);
        resetGame = new ResetGame();



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
                if (health_bar.health <=0) {
                    gameOver = true;
                }
            }
        }

        if (toaster != null && player.hitbox.overlaps(toaster.hitbox)) {
            toastGameActive = true;

            toaster.dispose();
            toaster=null;

            markerPosition = 0;
            movingRight = true;

            app.log("Collision", "TOASTER");
            //actiave minigame
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){ // && goose is in range
            BreadPoints.feedGoose();
        }

        if (player.hitbox.overlaps(wall.hitbox)) {
            app.log("Collision", "WALL");
            wall.checkCollisions(player.pos);
        }


        ScreenUtils.clear(1f, 1f, 1f, 1f);

        if (!toastGameActive) {
            player.update();
            for (Goose goose : gooseArray) {
                goose.update(player.getVector());
            }
        }

        float delta = Gdx.graphics.getDeltaTime();
        duckTimer += delta;

        if (toastGameActive) {

            if (movingRight) {
                markerPosition += toasterSpeed * delta;

            }
            else {
                markerPosition -= toasterSpeed * delta;
            }

            if (markerPosition >= 300) {
                movingRight = false;
            }

            if (markerPosition <= 0) {
                movingRight = true;
            }
        }

        if (toastGameActive && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

            if (markerPosition >= 150 && markerPosition <= 200) {
                app.log("Toast", "Perfect");
            }

            else if (markerPosition >= 100 && markerPosition <= 250) {
                app.log("Toast", "Good");
            }

            else {
                app.log("Toast", "Burnt");
            }

            toastGameActive = false;
        }

        if (!toastGameActive && duckTimer >1f) {
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
        pointCounter = BreadPoints.getCounter();
        player.render(batch);
        for (Goose goose : gooseArray) {
            goose.render(batch);
        }

        if (toaster != null) {
            toaster.render(batch);
        }
        wall.render(batch);


        batch.end();

        uiCamera.update();
        batch.setProjectionMatrix(uiCamera.combined);

        if (toastGameActive) {
            shapeRenderer.setProjectionMatrix(
                uiCamera.combined
            );

            shapeRenderer.begin(
                ShapeRenderer.ShapeType.Filled
            );

            //RED
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.rect(500, 500, 100, 30);

            //Yellow
            shapeRenderer.setColor(1, 1, 0, 1);
            shapeRenderer.rect(600, 500, 50, 30);

            //Green
            shapeRenderer.setColor(0, 1, 0, 1);
            shapeRenderer.rect(650, 500, 50, 30);

            //Yellow
            shapeRenderer.setColor(1, 1, 0, 1);
            shapeRenderer.rect(700, 500, 50, 30);

            //RED
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.rect(750, 500, 100, 30);

            shapeRenderer.end();
        }


        batch.begin();

        displayTime = Timer.refreshTimer();
        pointCounter = BreadPoints.getCounter();

        font.draw(
            batch,
            displayTime,
            Gdx.graphics.getWidth() - 200,
            Gdx.graphics.getHeight() - 50
        );

        font.draw(
            batch,
            (pointCounter+""),
            Gdx.graphics.getWidth() - 200,
            Gdx.graphics.getHeight() - 100
        );

        if (toastGameActive) {
            font.draw(
                batch, "Toast the bread", 500, 870
            );

            font.draw(
                batch, "Press Space", 520, 670
            );


            font.draw(
                batch, "v", 500 + markerPosition, 560
            );

        }

        batch.end();

        health_bar.display();

        batch.begin();

        ItemCounter.display();

        inventory.act();
        inventory.draw();





        batch.end();
        if(gameOver) {
            GameOverScreen.display();
        }




    }

    @Override
    public void resize(int width, int height) {
        uiCamera.setToOrtho(false, width, height);
        GameOverScreen.resize(width,height);
    }

    public void resetGame() {
        resetGame.reset(player,health_bar,  gooseArray, ItemCounter ,breadPoints, inventory, inventoryImage);

        if (toaster != null) {
            toaster.dispose();
        }
        toaster = new Toaster("toaster.png", new Vector2(900,300), 39,31);
        gameOver = false;

     }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        shapeRenderer.dispose();
        for (Goose goose : gooseArray) {
            goose.dispose();
        }
        if (toaster != null) {
            toaster.dispose();
        }
        map.dispose();
        wall.dispose();
        font.dispose();
        health_bar.dispose();
        ItemCounter.dispose();
        sound.dispose();
        ItemCounter.dispose();
        inventory.dispose();
        inventoryImage.dispose();
        GameOverScreen.dispose();
    }

    private void addGoose() {
        gooseArray.add(new Goose("Goose.png", new Vector2((MathUtils.random(0f, map.getWidth())),(MathUtils.random(0f, map.getHeight())))));
    }



}
