package screens;

import Goose_Patrol.york.ac.uk.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import static com.badlogic.gdx.Gdx.app;

public class GameScreen extends ScreenAdapter {
    private GoosePatrol game;
    private Player player;
    private Array<Goose> gooseArray;
    private Texture map;
    private OrthographicCamera camera;
    private OrthographicCamera uiCamera;
    private Viewport viewport;
    private BitmapFont font;
    private String myText;
    private Timer timer;
    private String displayTime;
    private HealthBar healthBar;
    public BreadPoints breadPoints;
    public static BreadPoints staticBreadPoints;
    private int pointCounter;
    public Sound sound;
    private boolean gameOver = false;
    private ItemCounter ItemCounter;
    private GameOver GameOver;
    private Stage inventory;
    private Texture inventoryImage;
    private Wall wall;
    private float duckTimer;
    private ResetGame resetGame;
    private Toaster toaster;

    private Minimap minimap;
    private Array<MapEntity> mapEntities;

    public GameScreen(GoosePatrol game) {
        this.game = game;
    }

    @Override
    public void show() {
        map = new Texture(Gdx.files.internal("map.png"));
        gooseArray = new Array<>();
        toaster = new Toaster("toaster.png", new Vector2(900,300),39, 31);
        player = new Player("Player.png", new Vector2(140, 210), 200f, map.getWidth(), map.getHeight());
        wall = new Wall("Wall.png", new Vector2(300, 300), 100, 200);

        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 450, camera);
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

        healthBar = new HealthBar();
        // --- Create minimap ---
        // Create list of MapEntity to pass to minimap
        mapEntities = new Array<>();
        mapEntities.add(player);

        // Calculate position values
        int minimapWidth = 400;
        int minimapHeight = 400;
        int margin = 10;
        int minimapPosX = Gdx.graphics.getWidth() - minimapWidth - margin;

        minimap = new Minimap(
            map, mapEntities,
            minimapWidth, minimapHeight,
            minimapPosX, margin,
            800, 800,
            map.getWidth(), map.getHeight(),
            1f
        );


        ItemCounter = new ItemCounter(game.batch);
        GameOver = new GameOver(healthBar);
        resetGame = new ResetGame();



        inventory = new Stage(new ScreenViewport());
        inventoryImage = new Texture(Gdx.files.internal("inventory.png"));
        Image image1 = new Image(inventoryImage);
        image1.setSize(127, 63);
        image1.setPosition(((float) Gdx.graphics.getWidth() / 2) - 64, 10);
        inventory.addActor(image1);

    }

    @Override
    public void render(float delta) {
        logic();
        draw();
        if(gameOver) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    private void logic() {
        player.update(); // Updates player hitbox and takes inputs

        for (Goose goose : gooseArray) { // Cycles through all geese
            goose.update(player.getPos()); // Goose movement logic

            if (player.hitbox.overlaps(goose.hitbox)) { // Checks if goose collides with player
                goose.dispose();
                gooseArray.removeValue(goose, false); // Deletes goose if goose hits player
                mapEntities.removeValue(goose, false);
                app.log("Collision", "GOOSE");
                healthBar.health = healthBar.health - 1;
                if (healthBar.health <= 0) {
                    gameOver = true;
                }
            }
        }

        if (toaster != null && player.hitbox.overlaps(toaster.hitbox)) {
            toaster.dispose();
            toaster = null;
            app.log("Collision", "TOASTER");
            //actiave minigame
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
            for (Goose goose : gooseArray) {
                boolean destroy = goose.beFed(player.getPos(), BreadPoints.getCounter());
                if (destroy) {
                    gooseArray.removeValue(goose, false);
                    app.log("Collision", "Target Neutralised");
                }
            }
        }



        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){ // && goose is in range
            BreadPoints.feedGoose();
        }

        if (player.hitbox.overlaps(wall.hitbox)) {
            app.log("Collision", "WALL");
            wall.checkCollisions(player.pos);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) { // && goose is in range
            BreadPoints.feedGoose();
        }

        float delta = Gdx.graphics.getDeltaTime();
        duckTimer += delta;
        if (duckTimer > 1f) { // Spawns geese over time
            duckTimer = 0;
            addGoose();
            app.log("Goose added", "big if true");
        }

        minimap.update();
        minimap.render(batch);
        viewport.apply();
    }

        private void draw() {
        ScreenUtils.clear(1f, 1f, 1f, 1f);

        camera.position.set(
            MathUtils.clamp(MathUtils.clamp(
                camera.position.x,
                player.getPos().x - (camera.viewportWidth / 3) + player.texture.getWidth(),
                player.getPos().x + (camera.viewportWidth / 3)
            ), camera.viewportWidth / 2, map.getWidth() - camera.viewportWidth / 2),

            MathUtils.clamp(MathUtils.clamp(
                camera.position.y,
                player.getPos().y - (camera.viewportHeight / 3) + player.texture.getHeight(),
                player.getPos().y + (camera.viewportHeight / 3)
            ), camera.viewportHeight / 2, map.getHeight() - camera.viewportHeight / 2),
            0
        );

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.batch.draw(map, 0, 0, 2270, 1182);
        displayTime = Timer.refreshTimer();
        pointCounter = BreadPoints.getCounter();
        player.render(game.batch);
        for (Goose goose : gooseArray) {
            goose.render(game.batch);
        }

        if (toaster != null) {
            toaster.render(game.batch);
        }
        wall.render(game.batch);


        uiCamera.update();
        game.batch.setProjectionMatrix(uiCamera.combined);

        displayTime = Timer.refreshTimer();
        pointCounter = BreadPoints.getCounter();

        font.draw(
            game.batch,
            displayTime,
            Gdx.graphics.getWidth() - 200,
            Gdx.graphics.getHeight() - 50
        );

        font.draw(
            game.batch,
            (pointCounter+""),
            Gdx.graphics.getWidth() - 200,
            Gdx.graphics.getHeight() - 100
        );

        healthBar.display();

        ItemCounter.display();

        inventory.act();
        inventory.draw();

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        uiCamera.setToOrtho(false, width, height);
        viewport.update(width, height, true);
        GameOverScreen.resize(width,height);

        // --- Handle minimap resize ---
        // Calculate position values
        int minimapWidth = width/5;
        int minimapHeight = minimapWidth; // Guarantees square shape, change wanting to mirror window aspect ratio
        int margin = 10;
        int minimapPosX = width - minimapWidth - margin;
        minimap.resize(minimapWidth, minimapHeight);
        minimap.setPos(minimapPosX, margin);
    }

    @Override
    public void dispose() {
        game.batch.dispose();
        player.dispose();
        for (Goose goose : gooseArray) {
            goose.dispose();
        }
        if (toaster != null) {
            toaster.dispose();
        }
        map.dispose();
        minimap.dispose();
        wall.dispose();
        font.dispose();
        healthBar.dispose();
        ItemCounter.dispose();
        sound.dispose();
        ItemCounter.dispose();
        inventory.dispose();
        inventoryImage.dispose();
    }

    private void addGoose() {
        Goose newGoose = new Goose("Goose.png", new Vector2((MathUtils.random(0f, map.getWidth())),(MathUtils.random(0f, map.getHeight()))));
        gooseArray.add(newGoose);
        mapEntities.add(newGoose);
    }
}
