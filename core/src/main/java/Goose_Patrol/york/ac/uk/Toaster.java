package Goose_Patrol.york.ac.uk;

import com.badlogic.gdx.math.Vector2;

public class Toaster  extends Entity{
    private int width;
    private int height;

    public Toaster(String texture, Vector2 pos, int width, int height) {
        super(texture, pos);
        this.width = width;
        this.height = height;
        super.hitbox();
    }

    public void checkCollisions(Vector2 targetPos) {
       if (targetPos.x > pos.x - 8 && targetPos.x < pos.x - 8) {
            //collision with right side
            targetPos.x = pos.x - 8;
        }
        if (targetPos.x < pos.x + 8 && targetPos.x > pos.x + 8) {
            //collision with left side
            targetPos.x = pos.y + 8;
        }
        if (targetPos.y < pos.y + 8 && targetPos.y > pos.y + 8) {
            //collision with top side
            targetPos.y = pos.y + 8;
        }
        if (targetPos.y > pos.y - 8 && targetPos.y < pos.y - 8) {
            //collision with bottom side
            targetPos.y = pos.y - 8;
        }
    }

    

    public void dispose() {
        super.dispose();
    }
}

    

