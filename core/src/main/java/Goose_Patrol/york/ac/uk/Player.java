package Goose_Patrol.york.ac.uk;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class Player {
    private Texture texture;
    private float x, y;
    private float speed;

    public Player(String texture, float x, float y, float speed) {
        this.texture = new Texture(texture);
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update() {
        float deltaSpeed = speed * Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            y += deltaSpeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            y -= deltaSpeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            x -= deltaSpeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            x += deltaSpeed;
        }
    }
    public Vector2 getVector() {
        return new Vector2(x, y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x , y);
    }

    public void dispose() {
        texture.dispose();
    }
}
