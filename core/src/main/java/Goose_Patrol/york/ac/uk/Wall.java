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

}
