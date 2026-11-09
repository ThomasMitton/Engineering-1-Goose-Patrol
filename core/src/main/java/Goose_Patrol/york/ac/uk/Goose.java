package Goose_Patrol.york.ac.uk;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Goose {
    private Vector2 pos;
    private Texture texture;

    public Goose(String texture,Vector2 pos) {
        this.texture = new Texture(texture);
        this.pos = new Vector2();
    }

    public void update(Vector2 targetPos) {

        float targetX = targetPos.x;
        float targetY = targetPos.y;

        if (pos.x < targetX) {
            pos.x++;
        } else {
            pos.x--;
        }

        if (pos.y < targetY) {
            pos.y++;
        } else {
            pos.y--;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y);
    }

    public void dispose() {
        texture.dispose();
    }
}
