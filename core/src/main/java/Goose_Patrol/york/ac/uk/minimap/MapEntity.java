package Goose_Patrol.york.ac.uk.minimap;

import com.badlogic.gdx.math.Vector2;

/**
 * Implement this interface to allow an entity to be displayed on a minimap.
 */
public interface MapEntity {
    Vector2 getPos();
    boolean isVisibleOnMap();
    EntityType getEntityType();
}

