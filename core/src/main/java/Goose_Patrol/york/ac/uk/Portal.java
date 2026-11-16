package Goose_Patrol.york.ac.uk;

import Goose_Patrol.york.ac.uk.minimap.EntityType;
import Goose_Patrol.york.ac.uk.minimap.MapEntity;
import com.badlogic.gdx.math.Vector2;

public class Portal extends Entity implements MapEntity {
    private boolean mapVisible;
    public Portal(String texture, Vector2 pos) {
        super(texture, pos);
        super.hitbox();
        mapVisible = false;
    }

    @Override
    public boolean isVisibleOnMap() {
        return mapVisible;
    }

    public void setMapVisibility(boolean visibility) { mapVisible = visibility; }

    @Override
    public EntityType getEntityType() {
        return EntityType.PORTAL;
    }
}
