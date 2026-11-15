package Goose_Patrol.york.ac.uk;
import Goose_Patrol.york.ac.uk.minimap.EntityType;
import Goose_Patrol.york.ac.uk.minimap.MapEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Player extends Entity implements MapEntity {
    private final float speed;
    private final float boundaryX;
    private final float boundaryY;

    // walking animations
    private Animation<TextureRegion> walkDown;
    private Animation<TextureRegion> walkUp;
    private Animation<TextureRegion> walkLeft;
    private Animation<TextureRegion> walkRight;

    // current animation img
    private Animation<TextureRegion> currentAnimation;

    // animation timer
    private float stateTime = 0;

    // check if player is moving
    private boolean moving = false;

    // remember last inputted direction
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Direction direction = Direction.DOWN;

    // store textures
    private final Array<Texture> animationTextures = new Array<>();

    private Animation<TextureRegion> loadAnimation(String direction) {

        TextureRegion[] frames = new TextureRegion[4];

        for (int i = 0; i < 4; i++) {

            Texture texture = new Texture(
                Gdx.files.internal(
                    "spriteanimation/walking_" + direction + "_" + (i + 1) + ".png"
                )
            );

            animationTextures.add(texture);

            frames[i] = new TextureRegion(texture);
        }

        Animation<TextureRegion> animation =
            new Animation<>(0.12f, frames);

        animation.setPlayMode(Animation.PlayMode.LOOP);

        return animation;
    }

    public Player(String texture, Vector2 pos, float speed, int mapWidth, int mapHeight) {

        super(texture, pos);

        this.speed = speed;
        this.boundaryX = mapWidth;
        this.boundaryY = mapHeight;

        // Load the four walking animations
        walkDown = loadAnimation("down");
        walkUp = loadAnimation("up");
        walkLeft = loadAnimation("left");
        walkRight = loadAnimation("right");

        // Start by facing down
        currentAnimation = walkDown;
    }

    public void update() {
        moving = false;
        super.hitbox();

        float deltaSpeed = speed * Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
            direction = Direction.UP;
            currentAnimation = walkUp;
            moving = true;
            if(pos.y + deltaSpeed <= boundaryY - texture.getHeight()){
                pos.y += deltaSpeed;
            } else{
                pos.y = boundaryY - texture.getHeight();
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            direction = Direction.DOWN;
            currentAnimation = walkDown;
            moving = true;
            if(pos.y - deltaSpeed >= 0){
                pos.y -= deltaSpeed;
            }else{
                pos.y = 0;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            direction = Direction.LEFT;
            currentAnimation = walkLeft;
            moving = true;
            if(pos.x - deltaSpeed >= 0){
                pos.x -= deltaSpeed;
            }else{
                pos.x = 0;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            direction = Direction.RIGHT;
            currentAnimation = walkRight;
            moving = true;
            if(pos.x + deltaSpeed <= boundaryX - texture.getWidth()){
                pos.x += deltaSpeed;
            }else{
                pos.x = boundaryX - texture.getWidth();
            }
        }
        if (moving) {
            stateTime += Gdx.graphics.getDeltaTime();
        } else {
            stateTime = 0;
        }
    }
    public TextureRegion getCurrentFrame() {

        if (moving) {
            return currentAnimation.getKeyFrame(stateTime, true);
        }

        return currentAnimation.getKeyFrames()[0];
    }

    @Override
    public void render(SpriteBatch batch) {

        batch.draw(
            getCurrentFrame(),
            pos.x,
            pos.y
        );

    }
    @Override
    public void dispose() {

        super.dispose();

        for (Texture texture : animationTextures) {
            texture.dispose();
        }
    }

    public Vector2 getPos(){
        return super.pos;
    }

    // --- MapEntity implementation ---
     public EntityType getEntityType() {
        return EntityType.PLAYER;
    }
     public boolean isVisibleOnMap() {
        // TODO: potential unlock for showing player position on map?
        return true;
    }
}
