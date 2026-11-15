package Goose_Patrol.york.ac.uk;

import Goose_Patrol.york.ac.uk.minimap.EntityType;
import Goose_Patrol.york.ac.uk.minimap.MapEntity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class BreadItem extends Entity implements MapEntity {

    public BreadItem(String texture, Vector2 pos) {
        super(texture, pos);
        super.hitbox();
    }

    public void pickedUp(){
        BreadPoints.incrementPoints();
        this.dispose();
    }

    public Vector2 getPosition() {
        return new Vector2(pos.x, pos.y);
    }

    @Override
    public boolean isVisibleOnMap() {
        return true;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.BREAD_ITEM;
    }
}
