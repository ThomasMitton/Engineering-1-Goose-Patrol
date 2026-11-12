package Goose_Patrol.york.ac.uk.minimap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * A minimap representing the main map with the passed texture.
 * Shows the positions of entities passed.
 */
public class Minimap {
    // Textures
    private Texture mapTexture;
    private Texture playerIcon;
    private Texture gooseIcon;

    private Array<MapEntity> entities;
    private float zoom;
    private Vector2 size;
    private Vector2 worldSize;


    private final OrthographicCamera camera;
    private final Viewport viewport;

    /**
     * Create a new minimap
     * @param mapTexture Texture to be used to represent the main map (should be same size)
     * @param entities List of MapEntities representing each entity to be rendered on the minimap
     * @param size  Vector2 The size on screen of the minimap
     * @param worldSize How much of the game world is shown by the map (i.e., pre-zoom)
     * @param zoom Initial zoom level
     */
    public Minimap(Texture mapTexture, Array<MapEntity> entities, Vector2 size, Vector2 worldSize, float zoom) {
        this.mapTexture = mapTexture;
        this.entities = entities;
        this.size = size;
        this.zoom = zoom;
        this.worldSize = worldSize;

        // Map has it's own independent camera to allow zoom/different scale to game world
        camera = new OrthographicCamera();

        // Use FitViewport to ensure minimap aspect ratio is fixed
        viewport = new FitViewport(worldSize.x, worldSize.y, camera);

        // Set textures, should be readable at small scale
        mapTexture = new Texture("map.png");
        playerIcon = new Texture("Player.png");
        gooseIcon = new Texture("Goose.png");

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
