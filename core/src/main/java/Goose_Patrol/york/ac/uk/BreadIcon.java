package Goose_Patrol.york.ac.uk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class BreadIcon {
    private final Texture activeTexture;
    private final Texture inactiveTexture;
    private float x, y, width, height;

    public BreadIcon(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        activeTexture = new Texture(Gdx.files.internal("bread.png"));
        inactiveTexture = new Texture(Gdx.files.internal("bread_inactive.png"));
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void render(SpriteBatch batch, Matrix4 uiProjection) {
        Texture tex = BreadPoints.getCounter() > 0 ? activeTexture : inactiveTexture;

        batch.setProjectionMatrix(uiProjection);
        batch.begin();
        batch.draw(tex, x, y, width, height);
        batch.end();
    }

    public void dispose() {
        activeTexture.dispose();
        inactiveTexture.dispose();
    }
}
