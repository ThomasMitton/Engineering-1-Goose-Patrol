package Goose_Patrol.york.ac.uk;

import Goose_Patrol.york.ac.uk.minimap.MapEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class ResetGame {


    public void reset(Player player, HealthBar health, Array<Goose> gooseArray, ItemCounter itemCounter,
                      BreadPoints breadPoints, Stage inventory, Texture inventoryImage, Array<MapEntity> mapEntities) {
        player.pos.set(140,210);

        health.health = 100;

        gooseArray.clear();
        mapEntities.clear();

        Timer.resetTimer();

        BreadPoints.resetCounter();

        itemCounter.reset();

        inventory.clear();
        Image image1 = new Image(inventoryImage);
        image1.setSize(127,63);
        image1.setPosition(((float)Gdx.graphics.getWidth()/2)-64,10);
        inventory.addActor(image1);

    }



}
