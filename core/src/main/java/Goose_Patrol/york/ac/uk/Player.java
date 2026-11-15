package Goose_Patrol.york.ac.uk;
import Goose_Patrol.york.ac.uk.minimap.EntityType;
import Goose_Patrol.york.ac.uk.minimap.MapEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;


public class Player extends Entity implements MapEntity {
    private final float speed;
    private final float boundaryX;
    private final float boundaryY;

    public Player(String texture, Vector2 pos, float speed, int mapWidth, int mapHeight) {
        super(texture, pos);
        this.speed = speed;
        this.boundaryX = mapWidth;
        this.boundaryY = mapHeight;
    }

    public void update() {
        super.hitbox();

        float deltaSpeed = speed * Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
            if(pos.y + deltaSpeed <= boundaryY - texture.getHeight()){
                pos.y += deltaSpeed;
            } else{
                pos.y = boundaryY - texture.getHeight();
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            if(pos.y - deltaSpeed >= 0){
                pos.y -= deltaSpeed;
            }else{
                pos.y = 0;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            if(pos.x - deltaSpeed >= 0){
                pos.x -= deltaSpeed;
            }else{
                pos.x = 0;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if(pos.x + deltaSpeed <= boundaryX - texture.getWidth()){
                pos.x += deltaSpeed;
            }else{
                pos.x = boundaryX - texture.getWidth();
            }
        }
    }

    // --- MapEntity implementation ---
    public Vector2 getPosition() {
        return super.getVector();
    }
     public EntityType getEntityType() {
        return EntityType.PLAYER;
    }
     public boolean isVisibleOnMap() {
        // TODO: potential unlock for showing player position on map?
        return true;
    }
}
