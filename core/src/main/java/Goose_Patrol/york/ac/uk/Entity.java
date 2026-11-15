package Goose_Patrol.york.ac.uk;

import Goose_Patrol.york.ac.uk.minimap.EntityType;
import Goose_Patrol.york.ac.uk.minimap.MapEntity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Entity superclass for player, goose, and items
 */
public class Entity implements MapEntity {
    protected Texture texture;
    protected Vector2 pos;
    protected Rectangle hitbox;
    private EntityType type;

    protected Entity(String texture, Vector2 pos) {
        this.texture = new Texture(texture);
        this.pos = new Vector2(pos.x, pos.y);
        this.hitbox = new Rectangle();
        this.type = EntityType.OTHER;
    }

    public Vector2 getPos() {
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

    public boolean isVisibleOnMap() {
        return true;
    }

    public EntityType getEntityType() {
        return type;
    }

    public void setEntityType(EntityType type) {
        this.type = type;
    }
}
