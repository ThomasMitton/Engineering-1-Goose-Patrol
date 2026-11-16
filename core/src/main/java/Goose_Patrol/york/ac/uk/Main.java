package Goose_Patrol.york.ac.uk;
import Goose_Patrol.york.ac.uk.minimap.EntityType;
import Goose_Patrol.york.ac.uk.minimap.MapEntity;
import Goose_Patrol.york.ac.uk.minimap.Minimap;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import static com.badlogic.gdx.Gdx.app;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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
    private Array<BreadItem>  breadItemArray;
    private Texture map;
    private OrthographicCamera camera;
    private OrthographicCamera uiCamera;
    private Viewport viewport;
    private BitmapFont font;
    private String myText;
    private Timer timer;
    private String displayTime;
    private Portal portal1;
    private Portal portal2;
    private HealthBar health_bar;
    public BreadPoints breadPoints;
    public static BreadPoints staticBreadPoints;
    private int pointCounter;
    public Sound sound;
    private GameOverScreen GameOverScreen;
    private boolean gameOver = false;
    private Texture titleScreen;
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
    private Entity mapItem;
    private Stage stage;

    private Minimap minimap;
    private Minimap fullscreenMap;
    private Array<MapEntity> mapEntities;
    private Bus bus;
    private boolean mapUnlocked;
    private boolean mapOpened;

    private BreadIcon breadIcon;

    private boolean gamePaused;
    private Texture pauseMenu;

    @Override
    public void create() {
        GameOverScreen = new GameOverScreen(this);
        map = new Texture(Gdx.files.internal("map.png"));
        gooseArray = new Array<>();
        breadItemArray = new Array<>();
        toaster = new Toaster("toaster.png", new Vector2(900,300),39, 31);
        keyItem = new KeyItem("key.png", new Vector2(900,600),29, 13);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        player = new Player("Player.png", new Vector2(140, 210), 200f, map.getWidth(), map.getHeight());
        wall = new Wall("Wall.png", new Vector2(300, 300), 100, 200);
        titleScreen = new Texture("TitleScreen.png");
        pauseMenu = new Texture("PauseMenu.png");
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(800, 450, camera);
        font = new BitmapFont(Gdx.files.internal("Open Sans.fnt"));
        camera.setToOrtho(false, 800, 600);
        font = new BitmapFont(Gdx.files.internal("font2.fnt"));
        displayTime = Timer.refreshTimer();
        pointCounter = BreadPoints.getCounter();
        sound = Gdx.audio.newSound(Gdx.files.internal("beep.mp3"));
        greyBackgroundSquare = new Texture(Gdx.files.internal("greySquare.png"));
        portal1 = new Portal("portal.png", new Vector2((MathUtils.random(0f, map.getWidth())),(MathUtils.random(0f, map.getHeight()))));
        portal2 = new Portal("portal.png", new Vector2((MathUtils.random(0f, map.getWidth())),(MathUtils.random(0f, map.getHeight()))));
        app.log("Portal1 pos",portal1.pos+"");
        app.log("Portal2 pos",portal2.pos+"");
        bus = new Bus("bus.png", new Vector2(map.getWidth() - 200, map.getHeight() - 250));

        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(
            false,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );


        // Create map unlock item
        mapItem = new Entity(
            "MapIcon.png",
            new Vector2(
                MathUtils.random(300f, map.getWidth() - 50f),
                (MathUtils.random(300f, map.getHeight()) - 50f)
            )
        );
        mapItem.hitbox();
        mapItem.setEntityType(EntityType.MAP);

        // --- Create minimap ---
        mapUnlocked = false;
        // Create list of MapEntity to pass to minimap
        mapEntities = new Array<>();
        mapEntities.add(player);
        mapEntities.add(bus);
        mapEntities.add(portal1);
        mapEntities.add(portal2);
        mapEntities.add(keyItem);
        mapEntities.add(mapItem);
        mapEntities.add(portal1, portal2);



        // Calculate position values
        int minimapWidth = 400;
        int minimapHeight = 400;
        int margin = 10;
        int minimapPosX = Gdx.graphics.getWidth() - minimapWidth - margin;

        minimap = new Minimap(
            map, mapEntities,
            minimapWidth, minimapHeight,
            minimapPosX, margin,
            map.getWidth(), map.getHeight(),
            2f, false
        );


        // --- Create full screen map ---
        mapOpened = false;
        margin = 50;
        int fullscreenMapWidth = Gdx.graphics.getWidth() - (margin * 2);
        int fullscreenMapHeight = Gdx.graphics.getHeight() - (margin * 4);
        // Center map if texture smaller than window
        int fMapPosX = (Gdx.graphics.getWidth()/2) -  (fullscreenMapWidth/2);
        int fMapPosY = (Gdx.graphics.getHeight()/2) -  (fullscreenMapHeight/2);

        fullscreenMap = new Minimap(
            map, mapEntities,
            fullscreenMapWidth, fullscreenMapHeight,
            fMapPosX, fMapPosY,
            map.getWidth(), map.getHeight(),
            3f, false
        );

        health_bar = new HealthBar();

        ItemCounter = new ItemCounter(batch);
        GameOver = new GameOver(health_bar);
        resetGame = new ResetGame();

        breadIcon = new BreadIcon((float) Gdx.graphics.getWidth() / 2 - 54, 30, 54, 28);

        Inventory = new Inventory();

    }

    //TODO: split update() and render() functions for clarity
    @Override
    public void render() {
        viewport.apply();

        if (currentState == GameState.MAIN_MENU) {
            renderMainMenu();
            return;
        }

        if (gamePaused) {
            renderPauseMenu();
            return;
        }

        for (BreadItem breadItem : breadItemArray) {
            if (player.hitbox.overlaps(breadItem.hitbox)) {
                breadItemArray.removeValue(breadItem, false);
                mapEntities.removeValue(breadItem, false);
                BreadPoints.incrementPoints();
            }
        }


        for (Goose goose : gooseArray) {
            if (player.hitbox.overlaps(goose.hitbox)) {
                gooseArray.removeValue(goose, false);
                mapEntities.removeValue(goose, false);
                health_bar.health = health_bar.health - 1;
                if (health_bar.health <= 0) {
                    gameOver = true;
                    showGameOverButtons();
                }
            }
        }

        for(BreadItem breadItem : breadItemArray){
            if (player.hitbox.overlaps(breadItem.hitbox)) {
                breadItem.pickedUp();
                breadItemArray.removeValue(breadItem, false);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            if (player.hitbox.overlaps(bus.hitbox) && BreadPoints.getCounter() >= 100){
                gameOver = true;
                showGameOverButtons();
            }
        }


        if (System.currentTimeMillis() - Timer.startTime >= 300000) {
            gameOver = true;
            showGameOverButtons();
        }
        if (keyItem.collected) {
            if (player.hitbox.overlaps(portal1.hitbox)) {
                player.pos = new Vector2(portal2.pos.x + 100, portal2.pos.y);
                ItemCounter.incrementEvents();
            }
            if (player.hitbox.overlaps(portal2.hitbox)) {
                player.pos = new Vector2(portal1.pos.x + 100, portal1.pos.y);
                ItemCounter.incrementEvents();
            }
        }
        if (toaster != null && player.hitbox.overlaps(toaster.hitbox)) {
            toastGameActive = true;

            ItemCounter.incrementEvents();

            toaster.dispose();
            toaster = null;

            markerPosition = 0;
            movingRight = true;

            app.log("Collision", "TOASTER");
            //activate minigame
        }

        if (!keyItem.collected && player.hitbox.overlaps(keyItem.hitbox)) {
            app.log("Collision", "KEY");
            keyItem.collect(Inventory);
            mapEntities.removeValue(keyItem, false);
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            for (Goose goose : gooseArray) {
                boolean destroy = goose.beFed(player.getPos(), BreadPoints.getCounter());
                if (destroy) {
                    gooseArray.removeValue(goose, false);
                    mapEntities.removeValue(goose, false);
                    ItemCounter.incrementItems();
                    app.log("Collision", "Target Neutralised");
                }
            }
        }

//        if (player.hitbox.overlaps(wall.hitbox)) {
//            app.log("Collision", "WALL");
//            wall.checkCollisions(player.pos);
//        }


        ScreenUtils.clear(1f, 1f, 1f, 1f);

        if (!toastGameActive && !gamePaused) {
            player.update();
            for (Goose goose : gooseArray) {
                goose.update(player.getPos());
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gamePaused = !gamePaused;
        };

        float delta = Gdx.graphics.getDeltaTime();
        duckTimer += delta;

        if (toastGameActive) {

            if (movingRight) {
                markerPosition += toasterSpeed * delta;

            } else {
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
                for (int i = 0; i < 10; i++) {
                    BreadPoints.incrementPoints();
                }
            } else if (markerPosition >= 100 && markerPosition <= 250) {
                app.log("Toast", "Good");
                for (int i = 0; i < 5; i++) {
                    BreadPoints.incrementPoints();
                }
            } else {
                app.log("Toast", "Burnt");
                for (int i = 0; i < 1; i++) {
                    BreadPoints.incrementPoints();
                }
            }

            toastGameActive = false;

            toaster = new Toaster(
                "toaster.png",
                new Vector2(
                    MathUtils.random(100, map.getWidth() - 100),
                    MathUtils.random(100, map.getHeight() - 100)
                ),
                39,
                31
            );
        }

        if (!toastGameActive && duckTimer > 1f) {
            duckTimer = 0;
            addGoose();
            addBreadItem();
            app.log("Goose added", "big if true");
        }


        camera.position.set(
            MathUtils.clamp(MathUtils.clamp(
                camera.position.x,
                player.getPos().x - (camera.viewportWidth / 10) + player.texture.getWidth(),
                player.getPos().x + (camera.viewportWidth / 10)
            ), camera.viewportWidth / 2, map.getWidth() - camera.viewportWidth / 2),

            MathUtils.clamp(MathUtils.clamp(
                camera.position.y,
                player.getPos().y - (camera.viewportHeight / 10) + player.texture.getHeight(),
                player.getPos().y + (camera.viewportHeight / 10)
            ), camera.viewportHeight / 2, map.getHeight() - camera.viewportHeight / 2),
            0
        );

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(map, 0, 0, 1904, 1964);
        if (!gamePaused) {
            displayTime = Timer.refreshTimer();
        };
        pointCounter = BreadPoints.getCounter();
        player.render(batch);
        bus.render(batch);
        for (Goose goose : gooseArray) {
            goose.render(batch);
        }

        for (BreadItem breadItem : breadItemArray) {
            breadItem.render(batch);
        }

        if (toaster != null) {
            toaster.render(batch);
        }


        if (keyItem != null && !keyItem.collected) {
            keyItem.render(batch);
        }
//        wall.render(batch);

        // Map item handling
        // TODO: when discovered, add to inventory
        if (mapItem != null) {
            mapItem.render(batch);
            if (mapItem.hitbox.overlaps(player.hitbox)) {
                app.log("Collision", "Map item");
                mapEntities.removeValue(mapItem, false);
                mapItem.dispose();
                mapItem = null;
                mapUnlocked = true;
            }
        }


        if (keyItem.collected) {
            portal1.render(batch);
            portal2.render(batch);
            portal1.setMapVisibility(true);
            portal2.setMapVisibility(true);
        }
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

        if (!gamePaused) {
            displayTime = Timer.refreshTimer();
        };

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
            Gdx.graphics.getWidth() - 135,
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

        // --- Map logic/rendering ---
        // Check if map opened
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)
                || Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            mapOpened = !mapOpened;
        }

        // Draw minimap/map depending on whether unlocked
        if (mapUnlocked) {
            if (mapOpened) {
                // Show fullscreen map
                fullscreenMap.show();
                minimap.hide();

            } else {
                // Show minimap
                minimap.show();
                fullscreenMap.hide();
            }
        }

        // Show minimap (if hidden, does nothing)
        minimap.update();
        minimap.render(batch);

        // Show fullscreen map (if hidden, does nothing)
        fullscreenMap.update();
        fullscreenMap.render(batch);
        viewport.apply(); // reset viewport to game viewport



        Inventory.draw();
        breadIcon.render(batch, uiCamera.combined);

        if(gameOver) {
            GameOverScreen.display();
        }


    }

    @Override
    public void resize(int width, int height) {
        uiCamera.setToOrtho(false, width, height);
        viewport.update(width, height, true);
        GameOverScreen.resize(width,height);
        Inventory.resize(width,height);
        breadIcon.setPosition(width / 2f - 57, 28);

        // --- Handle minimap resize ---
        // Calculate position values
        int minimapWidth = width/5;
        int minimapHeight = minimapWidth; // Guarantees square shape, change wanting to mirror window aspect ratio
        int margin = 10;
        int minimapPosX = width - minimapWidth - margin;
        minimap.resize(minimapWidth, minimapHeight);
        minimap.setPos(minimapPosX, margin);

        // --- Handle full screen map resize ---
        margin = 50;
        int fullscreenMapWidth = width - (margin * 2);
        int fullscreenMapHeight = height - (margin * 4);
        // Center map
        int fMapPosX = (width/2) -  (fullscreenMapWidth/2);
        int fMapPosY = (height/2) -  (fullscreenMapHeight/2);

        fullscreenMap.resize(fullscreenMapWidth, fullscreenMapHeight);
        fullscreenMap.setPos(fMapPosX, fMapPosY);
    }

    public void resetGame() {
        resetGame.reset(player,health_bar,  gooseArray, ItemCounter ,breadPoints, Inventory, mapEntities, breadItemArray);


        if (toaster != null) {
            toaster.dispose();
        }
        toaster = new Toaster("toaster.png", new Vector2(900,300), 39,31);

        if (keyItem != null) {
            keyItem.dispose();
        }

        keyItem = new KeyItem("key.png", new Vector2(900,600),29, 13);
        mapEntities.add(keyItem);
        hideGameOverButtons();
        gameOver = false;
        mapUnlocked = false;
        mapOpened = false;
        minimap.hide();
        fullscreenMap.hide();
        mapItem = new Entity(
            "MapIcon.png",
            new Vector2(
                MathUtils.random(300f, map.getWidth() - 50f),
                (MathUtils.random(300f, map.getHeight()) - 50f)
            )
        );
        mapItem.hitbox();
        mapItem.setEntityType(EntityType.MAP);


        mapEntities.removeValue(portal1, false);
        mapEntities.removeValue(portal2, false);
        portal1.dispose();
        portal2.dispose();
        portal1 = new Portal("portal.png", new Vector2((MathUtils.random(0f, map.getWidth())),(MathUtils.random(0f, map.getHeight()))));
        portal2 = new Portal("portal.png", new Vector2((MathUtils.random(0f, map.getWidth())),(MathUtils.random(0f, map.getHeight()))));
        mapEntities.add(portal1, portal2);

     }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        portal1.dispose();
        portal2.dispose();
        shapeRenderer.dispose();
        for (Goose goose : gooseArray) {
            goose.dispose();
        }
        for (BreadItem breadItem : breadItemArray){
            breadItem.dispose();
        }
        if (toaster != null) {
            toaster.dispose();
        }

        if (keyItem != null) {
            keyItem.dispose();
        }
        map.dispose();
        minimap.dispose();
        fullscreenMap.dispose();
        wall.dispose();
        font.dispose();
        health_bar.dispose();
        ItemCounter.dispose();
        sound.dispose();
        Inventory.dispose();
        GameOverScreen.dispose();
        bus.dispose();
        breadIcon.dispose();
    }

    private void addGoose() {
        Goose newGoose = new Goose("Goose.png", new Vector2((MathUtils.random(0f, map.getWidth())),(MathUtils.random(0f, map.getHeight()))));
        gooseArray.add(newGoose);
        mapEntities.add(newGoose);
    }

    private void addBreadItem(){
        BreadItem newBreadItem = new BreadItem("bread.png", new Vector2((MathUtils.random(0f, map.getWidth())),(MathUtils.random(0f, map.getHeight()))));
        breadItemArray.add(newBreadItem);
        mapEntities.add(newBreadItem);
    }


    //Renders the Main Menu Screen (Work in progress).
    private void renderMainMenu() {
        com.badlogic.gdx.utils.ScreenUtils.clear(1f,1f,1f,1f);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(titleScreen,0,0,viewport.getWorldWidth(),viewport.getWorldHeight());
        batch.end();

        if(Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            currentState = GameState.GAME;
        }
    }

    private void renderPauseMenu() {
        com.badlogic.gdx.utils.ScreenUtils.clear(1f,1f,1f,1f);

        viewport.apply();
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();
        batch.draw(pauseMenu,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gamePaused = false;
        }
    }

    public void showGameOverButtons() {
        GameOverScreen.show();
    }

    public void hideGameOverButtons() {
        GameOverScreen.hide();
    }







}
