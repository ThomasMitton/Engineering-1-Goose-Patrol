package Goose_Patrol.york.ac.uk;

import com.badlogic.gdx.math.Vector2;

public class Wall extends Entity {
    private int width;
    private int height;

    public Wall(String texture, Vector2 pos, int width, int height) {
        super(texture, pos);
        this.width = width;
        this.height = height;
        super.hitbox();
    }

    public void checkCollisions(Vector2 targetPos) {
        if (targetPos.x > pos.x - 64 && targetPos.x < pos.x - 56) {
            //collision with right side
            targetPos.x = pos.x - 64;
        }
        if (targetPos.x < pos.x + 64 && targetPos.x > pos.x + 56) {
            //collision with left side
            targetPos.x = pos.y + 64;
        }
        if (targetPos.y < pos.y + 64 && targetPos.y > pos.y + 56) {
            //collision with top side
            targetPos.y = pos.y + 64;
        }
        if (targetPos.y > pos.y - 64 && targetPos.y < pos.y - 56) {
            //collision with bottom side
            targetPos.y = pos.y - 64;
        }
    }
}
