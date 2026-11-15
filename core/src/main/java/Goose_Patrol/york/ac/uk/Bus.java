package Goose_Patrol.york.ac.uk;

import Goose_Patrol.york.ac.uk.minimap.EntityType;
import Goose_Patrol.york.ac.uk.minimap.MapEntity;
import com.badlogic.gdx.math.Vector2;

public class Bus extends Entity implements MapEntity {

    public Bus(String texture, Vector2 pos) {
        super(texture, pos);
        super.hitbox();
    }

    public boolean isVisibleOnMap() {
        return true;
    }

    public EntityType getEntityType() {
        return EntityType.BUS;
    }
}
