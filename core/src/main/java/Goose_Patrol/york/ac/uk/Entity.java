package Goose_Patrol.york.ac.uk;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Entity {
    protected Texture texture;
    protected Vector2 pos;

    protected Entity(String texture, Vector2 pos) {
        this.texture = new Texture(texture);
        this.pos = new Vector2(pos.x, pos.y);
    }

    public Vector2 getVector() {
        return pos;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y);
    }

    public void dispose() {
        texture.dispose();
    }
}
