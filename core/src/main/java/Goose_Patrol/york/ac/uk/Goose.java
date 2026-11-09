package Goose_Patrol.york.ac.uk;

import com.badlogic.gdx.math.Vector2;

public class Goose extends Entity{

    public Goose(String texture, Vector2 pos) {
        super(texture, pos);
    }

    public void update(Vector2 targetPos) {

        float targetX = targetPos.x;
        float targetY = targetPos.y;

        super.hitbox();

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
}
