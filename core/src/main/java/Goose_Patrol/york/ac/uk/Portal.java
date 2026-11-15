package Goose_Patrol.york.ac.uk;

import Goose_Patrol.york.ac.uk.minimap.EntityType;
import Goose_Patrol.york.ac.uk.minimap.MapEntity;
import com.badlogic.gdx.math.Vector2;

public class Portal extends Entity implements MapEntity {
    public Portal(String texture, Vector2 pos) {
        super(texture, pos);
        super.hitbox();
    }

    @Override
    public boolean isVisibleOnMap() {
        return true;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.PORTAL;
    }
}
