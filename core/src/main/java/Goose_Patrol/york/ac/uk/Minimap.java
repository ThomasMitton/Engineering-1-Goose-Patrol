package Goose_Patrol.york.ac.uk;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;

/**
 * A minimap representing the main map with the passed texture.
 * Shows the positions of entities passed.
 */
public class Minimap {
    private Texture mapTexture;
    private Entity[] entities;
    private int sizeX;
    private int sizeY;
    private float zoom;

    /**
     * Create a new minimap
     * @param mapTexture Texture to be used to represent the main map (should have same dimensions)
     * @param entities List of all entities on the map at present
     * @param sizeX Horizontal size of the rendered mini-map
     * @param sizeY Vertical size of the rendered mini-map
     * @param zoom Initial zoom level
     */
    public Minimap(Texture mapTexture, Entity[] entities, int sizeX, int sizeY, float zoom) {
        this.mapTexture = mapTexture;
        this.entities = entities;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.zoom = zoom;

        // Initialize the minimap
        //TODO

        update(); // Update the newly initialized minimap
    }

    public void update() {
        //TODO: update the minimap from the entity list
    }

    /**
     * Updates the list of entities displayed on the minimap
     * @param updatedEntities the new complete list of active entities
     */
    public void updateEntities(Entity[] updatedEntities) {
        this.entities = updatedEntities;
        //TODO: Filter to only desired entities and determine which one to follow
    }

    public void setZoom() {
        //TODO
    }
}
