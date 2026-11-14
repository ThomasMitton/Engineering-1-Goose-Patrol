package Goose_Patrol.york.ac.uk;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Entity superclass for player, goose, and items
 */
public class Entity {
    public Texture texture;
    public Vector2 pos;
    public Rectangle hitbox;

    protected Entity(String texture, Vector2 pos) {
        this.texture = new Texture(texture);
        this.pos = new Vector2(pos.x, pos.y);
        this.hitbox = new Rectangle();
    }

    public Vector2 getVector() {
        return pos;
    }

    public void hitbox() {
        hitbox.set(pos.x, pos.y, texture.getWidth(), texture.getHeight());
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y);
    }

    public void dispose() {
        texture.dispose();
    }
}
