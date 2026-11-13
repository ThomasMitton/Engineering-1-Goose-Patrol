package Goose_Patrol.york.ac.uk.minimap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * A minimap representing the main map with the passed texture.
 * Shows the positions of entities passed.
 */
public class Minimap {
    // Minimap textures
    private final Texture mapTexture;
    private final Texture playerIcon;
    private final Texture gooseIcon;
    private final Texture otherIcon;


    private Array<MapEntity> entities;
    private float zoom;
    private int sizeX;
    private int sizeY;
    private int minimapPosX;
    private int minimapPosY;
    private int worldSizeX;
    private int worldSizeY;

    private MapEntity mapPlayer; // Used to update the location of the player

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private float worldWidth;

    /**
     * Create a new minimap.
     * Shows all entities included in the given list. The map camera only tracks the first entity of EntityType.PLAYER
     * @param mapTexture Texture to be used to represent the main map (should be same size)
     * @param entities List of entities representing each entity to be rendered on the minimap, should include player
     * @param sizeX The width on screen of the minimap
     * @param sizeY The height on screen of the minimap
     * @param minimapPosX The x-coordinate of the bottom-left corner of the minimap
     * @param minimapPosY The y-coordinate of the bottom-left corner of the minimap
     * @param worldSizeX The width of the game world
     * @param worldSizeY The height of the game world
     * @param zoom Initial zoom level
     */
    public Minimap(Texture mapTexture, Array<MapEntity> entities, int sizeX, int sizeY,
                   int minimapPosX, int minimapPosY, int worldSizeX, int worldSizeY, float zoom)
    {
        this.mapTexture = mapTexture;
        this.entities = entities;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.minimapPosX = minimapPosX;
        this.minimapPosY = minimapPosY;
        this.zoom = zoom;
        this.worldSizeX = worldSizeX;
        this.worldSizeY = worldSizeY;

        // Determine which entity is the player
        for (int i = 0; i < entities.size; i++) {
            MapEntity currentEntity = entities.items[i];
            if (currentEntity.getEntityType() == EntityType.PLAYER) {
                mapPlayer = currentEntity;
                break;
            }
        }

        // Map has its own independent camera to allow zoom/different scale to game world
        camera = new OrthographicCamera();
        camera.zoom = zoom;

        // Initialize minimap viewport with world size, FitViewport matches the aspect ratio of the world
        // TODO: Use a different scaling method for the viewport?
        viewport = new FitViewport(worldSizeX, worldSizeY, camera);

        // Set textures, should be readable at small scale
        mapTexture = new Texture("map.png");
        playerIcon = new Texture("Player.png");
        gooseIcon = new Texture("Goose.png");
        otherIcon = new Texture("GenericIcon.png");

    }

    public void update() {
        // Determine player position from list of entities passed
        Vector2 position = Vector2.Zero;
        if (mapPlayer != null) {
            position = mapPlayer.getPosition();
        }

        // Set and clamp camera position
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.set(
            MathUtils.clamp(position.x, effectiveViewportWidth/2f, worldSizeX - (effectiveViewportWidth/2f)),
            MathUtils.clamp(position.y, effectiveViewportHeight/2f, worldSizeY - (effectiveViewportHeight/2f)),
            0
        );
    }

    public void render(SpriteBatch batch) {
        // Configure viewport to render minimap in correct screen area
        viewport.update(sizeX, sizeY, false);
        viewport.setScreenPosition(minimapPosX, minimapPosY);

        // Switch batch over to minimap camera projection matrix
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Draw the minimap background
        batch.draw(mapTexture,
            camera.position.x - worldSizeX * camera.zoom/2f,
            camera.position.y - worldSizeY * camera.zoom/2f,
            worldSizeX * camera.zoom,
            worldSizeY * camera.zoom
        );

        // Draw entity markers on minimap, leave out player icon
        for (int i = 0; i < entities.size; i++) {
            MapEntity entity = entities.items[i];
            Texture icon;

            // Skip null entities, or entities not to be displayed on minimap
            if (entity == null || !entity.isVisibleOnMap()) continue;

            // Select between different entity types to determine which icon to use
            switch (entity.getEntityType()) {
                case GOOSE:
                    icon = gooseIcon;
                case PLAYER:
                    continue; // Don't draw player yet
                default:
                    icon = otherIcon;
            }

            // Draw the entity icon
            Vector2 entityPos = entity.getPosition();
            batch.draw(icon, entityPos.x - 32, entityPos.y - 32); //TODO: scale/centre icons
        }

        // Draw player icon last so always on top of the minimap
        Vector2 playerPos = mapPlayer.getPosition();
        batch.draw(playerIcon, playerPos.x - 32, playerPos.y - 32); //TODO: scale/centre icon

        batch.end();
    }


    public void setZoom(float zoom) {
        camera.zoom = zoom;
    }

    //TODO: Implement methods to allow minimap to be resized with the rest of the window
    public void resize(int screenWidth, int screenHeight) {

    }

    public void dispose() { //TODO: check everything that should be disposed has been
        mapTexture.dispose();
        playerIcon.dispose();
        gooseIcon.dispose();
        otherIcon.dispose();
    }
}
