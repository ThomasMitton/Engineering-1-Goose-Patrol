package Goose_Patrol.york.ac.uk;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class GameOverScreen {

    private Stage stage;
    private Skin skin;
    private OrthographicCamera uiCamera;
    public ResetGame resetGame;
    private Main game;




    public GameOverScreen(Main game) {
        this.game = game;
        //stage setup
        stage = new Stage(new FitViewport(1600, 900));
        Gdx.input.setInputProcessor(stage);
        //importing skins for buttons ect
        skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));

        //creating table for button to be shown on screen
        Table GameOver_button = new Table();
        GameOver_button.setFillParent(true);
        GameOver_button.center();

        // Creating Play button
        TextButton playButton = new TextButton("Play again", skin);
        //clicking logic for play button
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //add restarting the game
                game.resetGame();
            }
        });

        // quit button
        TextButton exitButton = new TextButton("Quit", skin);

        //clicking logic for quit button
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Add buttons to the table
        GameOver_button.add(playButton).width(200).height(50).pad(10);
        GameOver_button.row();
        GameOver_button.add(exitButton).width(200).height(50).pad(10);

        stage.addActor(GameOver_button);
    }

    public void display() {
        //clears the stage to put up new stage
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
