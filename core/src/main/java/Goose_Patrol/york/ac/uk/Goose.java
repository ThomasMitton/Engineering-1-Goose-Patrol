package Goose_Patrol.york.ac.uk;

import Goose_Patrol.york.ac.uk.minimap.EntityType;
import Goose_Patrol.york.ac.uk.minimap.MapEntity;
import com.badlogic.gdx.math.Vector2;

public class Goose extends Entity implements MapEntity {

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

    public boolean beFed(Vector2 playerPos,int  pointValue) {
        float playerX = playerPos.x;
        float playerY = playerPos.y;

        float gooseX = pos.x;
        float gooseY = pos.y;

        System.out.println("playerX: " + playerX + " playerY: " + playerY);
        System.out.println("gooseX: " + gooseX + " gooseY: " + gooseY);

        if ((gooseX - playerX < 100 && gooseY - playerY < 100) && (gooseX - playerX > -110 && gooseY - playerY > -100)) {
            if (pointValue > 0) {
                BreadPoints.decrementPoints();
                this.dispose();
                return true;
            }
        }
        return false;
    }

    // --- MapEntity implementation ---
    public Vector2 getPosition() {
        return super.getVector();
    }

    public EntityType getEntityType() {
        return EntityType.GOOSE;
    }

    public boolean isVisibleOnMap() {
        //TODO: visible on map only if within certain radius of player?
        return true;
    }
}
