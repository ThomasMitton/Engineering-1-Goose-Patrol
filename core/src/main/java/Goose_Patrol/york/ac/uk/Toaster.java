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
    

    public void dispose() {
        super.dispose();
    }
}

    

