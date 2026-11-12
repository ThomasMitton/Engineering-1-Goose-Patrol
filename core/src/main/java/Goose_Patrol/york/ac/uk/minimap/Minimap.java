package Goose_Patrol.york.ac.uk.minimap;

import Goose_Patrol.york.ac.uk.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * A minimap representing the main map with the passed texture.
 * Shows the positions of entities passed.
 */
public class Minimap {
    private Texture mapTexture;
    private Array<MapEntity> entities;
    private float zoom;
    private Vector2 size;

    /**
     * Create a new minimap
     * @param mapTexture Texture to be used to represent the main map (should have same dimensions)
     * @param entities List of MapEntities representing each entity to be rendered on the minimap
     * @param size  Vector2 The size on screen of the minimap
     * @param zoom Initial zoom level
     */
    public Minimap(Texture mapTexture, Array<MapEntity> entities, Vector2 size, float zoom) {
        this.mapTexture = mapTexture;
        this.entities = entities;
        this.size = size;
        this.zoom = zoom;
    }

    public void update() {
        //TODO: update camera position/entity positions on map
    }

    public void render() {
        //TODO
    }


    public void setZoom() {
        //TODO
    }
}
