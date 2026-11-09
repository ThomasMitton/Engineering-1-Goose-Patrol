package Goose_Patrol.york.ac.uk;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class Player extends Entity {
    private float speed;

    public Player(String texture, Vector2 pos, float speed) {
        super(texture, pos);
        this.speed = speed;
    }

    public void update() {
        float deltaSpeed = speed * Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            pos.y += deltaSpeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            pos.y -= deltaSpeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            pos.x -= deltaSpeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            pos.x += deltaSpeed;
        }
    }
}
