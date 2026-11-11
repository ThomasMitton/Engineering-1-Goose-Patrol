package Goose_Patrol.york.ac.uk;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;


public class Player extends Entity {
    private final float speed;

    public Player(String texture, Vector2 pos, float speed) {
        super(texture, pos);
        this.speed = speed;
    }

    public void update() {
        super.hitbox();

        float deltaSpeed = speed * Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
            pos.y += deltaSpeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            pos.y -= deltaSpeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            pos.x -= deltaSpeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            pos.x += deltaSpeed;
        }
    }
}
