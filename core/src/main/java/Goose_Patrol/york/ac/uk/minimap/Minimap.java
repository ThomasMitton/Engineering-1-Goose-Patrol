package Goose_Patrol.york.ac.uk.minimap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Gdx;

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
    private int maxWorldX;
    private int maxWorldY;

    private MapEntity mapPlayer; // Used to update the location of the player

    private final OrthographicCamera camera;
    private final Viewport viewport;

    private boolean visible;

    /**
     * Create a new minimap.
     * Shows all entities included in the given list. The map camera only tracks the first entity of EntityType.PLAYER
     * @param mapTexture Texture to be used to represent the main map (should be same size)
     * @param entities List of entities representing each entity to be rendered on the minimap, should include player
     * @param sizeX The width on screen of the minimap
     * @param sizeY The height on screen of the minimap
     * @param minimapPosX The x-coordinate of the bottom-left corner of the minimap
     * @param minimapPosY The y-coordinate of the bottom-left corner of the minimap
     * @param maxWorldX The width of the game world to determine camera bounds
     * @param maxWorldY The height of the game world to determine camera bounds
     * @param zoom Initial zoom level
     */
    public Minimap(Texture mapTexture, Array<MapEntity> entities, int sizeX, int sizeY,
                   int minimapPosX, int minimapPosY,
                   int maxWorldX, int maxWorldY, float zoom, boolean visible) {

        this.mapTexture = mapTexture;
        this.entities = entities;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.minimapPosX = minimapPosX;
        this.minimapPosY = minimapPosY;
        this.zoom = zoom;
        this.maxWorldX = maxWorldX;
        this.maxWorldY = maxWorldY;

        this.visible = visible;

        // Determine which entity is the player
        for (int i = 0; i < entities.size; i++) {
            MapEntity currentEntity = entities.get(i);
            if (currentEntity.getEntityType() == EntityType.PLAYER) {
                mapPlayer = currentEntity;
                break;
            }
        }

        // Map has its own independent camera to allow zoom/different scale to game world
        camera = new OrthographicCamera();
        camera.zoom = zoom;

        // Initialize minimap viewport with world size, FitViewport matches the aspect ratio of the world
        viewport = new FillViewport(sizeX, sizeY, camera);

        // Set textures, should be readable at small scale
        playerIcon = new Texture(Gdx.files.internal("minimap_icon.png"));
        gooseIcon = new Texture("GenericIcon.png");
        otherIcon = new Texture("GenericIcon.png");

    }

    public void update() {
        if (!visible) { return; }
        // Configure viewport to render minimap in correct screen area
        viewport.update(sizeX, sizeY, true);
        viewport.setScreenPosition(minimapPosX, minimapPosY);


        // Determine player position from list of entities passed
        Vector2 position = Vector2.Zero;
        if (mapPlayer != null) {
            position = mapPlayer.getPos();
        }

        // Set and clamp camera position
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.set(
            MathUtils.clamp(position.x, effectiveViewportWidth/2f, maxWorldX - (effectiveViewportWidth/2f)),
            MathUtils.clamp(position.y, effectiveViewportHeight/2f, maxWorldY - (effectiveViewportHeight/2f)),
            0
        );
        camera.update();
    }

    public void render(SpriteBatch batch) {
        if (!visible) { return;}
        viewport.apply();

        // Switch batch over to minimap camera projection matrix
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Draw the minimap background
        batch.draw(mapTexture, 0, 0, maxWorldX, maxWorldY);

        // Draw entity markers on minimap, leave out player icon
        for (int i = 0; i < entities.size; i++) {
            MapEntity entity = entities.get(i);
            Texture icon;

            // Skip null entities, or entities not to be displayed on minimap
            if (entity == null || !entity.isVisibleOnMap()) continue;

            // Select between different entity types to determine which icon to use
            switch (entity.getEntityType()) {
                case GOOSE:
                    icon = gooseIcon;
                    break;
                case PLAYER:
                    continue; // Don't draw player yet
                default:
                    icon = otherIcon;
            }

            // Draw the entity icon
            Vector2 entityPos = entity.getPos();
            batch.draw(
                icon, entityPos.x - icon.getWidth()/2f,
                entityPos.y - icon.getHeight()/2f
            );
        }

        // Draw player icon last so always on top of the minimap
        Vector2 playerPos = mapPlayer.getPos();
        batch.draw(playerIcon, playerPos.x - playerIcon.getWidth()/2f,
            playerPos.y - playerIcon.getHeight()/2f
        );

        batch.end();
    }

    /**
     * Replace the list of entities to be rendered on the minimap
     * @param entities The new list of entities to render on the minimap
     */
    public void setEntities(Array<MapEntity> entities) {
        this.entities = entities;
    }

    public void setZoom(float zoom) {
        camera.zoom = zoom;
    }

    public void show() {
        visible = true;
    }

    public void hide() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void resize(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void setPos(int posX, int posY) {
        this.minimapPosX = posX;
        this.minimapPosY = posY;
    }


    public void dispose() { //TODO: check everything that should be disposed has been
        mapTexture.dispose();
        playerIcon.dispose();
        gooseIcon.dispose();
        otherIcon.dispose();
    }
}
