package Goose_Patrol.york.ac.uk;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    //Used for swapping between the Main Menu and Game states.
    public enum GameState {
        MAIN_MENU,
        GAME
    }
    private GameState currentState = GameState.MAIN_MENU;
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
    private Inventory Inventory;
    private Wall wall;
    private float duckTimer;
    private ResetGame resetGame;
    private Toaster toaster;
    private ShapeRenderer shape;
    private Texture greyBackgroundSquare;
    private ShapeRenderer shapeRenderer;

    private boolean toastGameActive = false;
    private float markerPosition = 0;
    private boolean movingRight = true;
    private float toasterSpeed = 400f;

    private KeyItem keyItem;
    private Stage stage;


    @Override
    public void create() {
        GameOverScreen = new GameOverScreen(this);
        map = new Texture(Gdx.files.internal("map.png"));
        gooseArray = new Array<>();
        toaster = new Toaster("toaster.png", new Vector2(900,300),39, 31);
        keyItem = new KeyItem("key.png", new Vector2(900,600),29, 13);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        player = new Player("Player.png", new Vector2(140, 210), 200f, map.getWidth(), map.getHeight());
        wall = new Wall("Wall.png", new Vector2(300, 300), 100, 200);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        font = new BitmapFont(Gdx.files.internal("font2.fnt"));
        displayTime = Timer.refreshTimer();
        pointCounter = BreadPoints.getCounter();
        sound = Gdx.audio.newSound(Gdx.files.internal("beep.mp3"));
        greyBackgroundSquare = new Texture(Gdx.files.internal("greySquare.png"));


        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(
            false,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );





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
        Inventory = new Inventory();

    }

    @Override
    public void render() {

        if(currentState == GameState.MAIN_MENU) {
            renderMainMenu();
            return;
        }






        for (Goose goose : gooseArray) {
            if (player.hitbox.overlaps(goose.hitbox)) {
                gooseArray.removeValue(goose, false);
                //health_bar.health = health_bar.health - 1;
                if (health_bar.health <=0) {
                    gameOver = true;
                    showGameOverButtons();
                }
            }
        }
        if (System.currentTimeMillis() -Timer.startTime >= 300000){
            gameOver = true;
            showGameOverButtons();
        }

        if (toaster != null && player.hitbox.overlaps(toaster.hitbox)) {
            toastGameActive = true;

            toaster.dispose();
            toaster=null;

            markerPosition = 0;
            movingRight = true;

            app.log("Collision", "TOASTER");
            //activate minigame
        }

        if (keyItem.collected == false && player.hitbox.overlaps(keyItem.hitbox)) {
            app.log("Collision", "KEY");
            keyItem.collect(Inventory);
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
            for (Goose goose : gooseArray) {
                boolean destroy = goose.beFed(player.getPos(), BreadPoints.getCounter());
                if (destroy) {
                    gooseArray.removeValue(goose, false);
                    app.log("Collision", "Target Neutralised");
                }
            }
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

        batch.draw(map, 0, 0, 1904, 1964);
        displayTime = Timer.refreshTimer();
        pointCounter = BreadPoints.getCounter();
        player.render(batch);
        for (Goose goose : gooseArray) {
            goose.render(batch);
        }

        if (toaster != null) {
            toaster.render(batch);
        }


        if (keyItem != null && keyItem.collected == false) {
            keyItem.render(batch);
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

        batch.draw(greyBackgroundSquare, Gdx.graphics.getWidth()-140, Gdx.graphics.getHeight()-115);

        font.draw(
            batch,
            displayTime,
            Gdx.graphics.getWidth() - 135,
            Gdx.graphics.getHeight() - 15
        );

        font.draw(
            batch,
            (pointCounter+""),
            Gdx.graphics.getWidth() - 80,
            Gdx.graphics.getHeight() - 65
        );


        if (toastGameActive) {
            font.draw(
                batch, "Toast the bread", 500, 770
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





        batch.end();



        Inventory.draw();





        if(gameOver) {
            GameOverScreen.display();
        }




    }

    @Override
    public void resize(int width, int height) {
        uiCamera.setToOrtho(false, width, height);
        GameOverScreen.resize(width,height);
        Inventory.resize(width,height);
    }

    public void resetGame() {
        resetGame.reset(player,health_bar,  gooseArray, ItemCounter ,breadPoints, inventory, inventoryImage);
        resetGame.reset(player,health_bar,  gooseArray, ItemCounter ,breadPoints, Inventory);


        if (toaster != null) {
            toaster.dispose();
        }
        toaster = new Toaster("toaster.png", new Vector2(900,300), 39,31);

        if (keyItem != null) {
            keyItem.dispose();
        }

        keyItem = new KeyItem("key.png", new Vector2(900,600),29, 13);
        hideGameOverButtons();
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

        if (keyItem != null) {
            keyItem.dispose();
        }
        map.dispose();
        wall.dispose();
        font.dispose();
        health_bar.dispose();
        ItemCounter.dispose();
        sound.dispose();
        Inventory.dispose();
        GameOverScreen.dispose();
    }

    private void addGoose() {
        gooseArray.add(new Goose("Goose.png", new Vector2((MathUtils.random(0f, map.getWidth())),(MathUtils.random(0f, map.getHeight())))));
    }


    //Renders the Main Menu Screen (Work in progress).
    private void renderMainMenu() {
        com.badlogic.gdx.utils.ScreenUtils.clear(1f,1f,1f,1f);

        uiCamera.update();
        batch.setProjectionMatrix(uiCamera.combined);

        batch.begin();
        font.draw(batch, "GOOSE PATROL", 500,2*uiCamera.viewportHeight/3);
        font.draw(batch, "Click anywhere or press space to play", 500, uiCamera.viewportHeight/2);
        batch.end();

        if(Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            currentState = GameState.GAME;
        }
    }

    public void showGameOverButtons() {
        GameOverScreen.show();
    }

    public void hideGameOverButtons() {
        GameOverScreen.hide();
    }







}
