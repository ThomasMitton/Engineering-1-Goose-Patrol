package Goose_Patrol.york.ac.uk;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.Gdx;



public class Inventory {
    private Stage inventory;
    private Texture inventoryImage;

    public Inventory(){
        inventory = new Stage(new ScreenViewport());
        inventory.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
        inventoryImage = new Texture(Gdx.files.internal("inventory.png"));
        createInv();
        layout();


    }

    private void createInv() {
        Image inv = new Image(inventoryImage);
        inv.setName("inv");
        inv.setSize(189,63);
        inventory.addActor(inv);
    }

    public void layout(){
        float x = inventory.getViewport().getWorldWidth()/2f -64;
        Actor inv = inventory.getRoot().findActor("inv");
        if (inv != null) {
            inv.setPosition(x,10);
        }
        Actor key = inventory.getRoot().findActor("key");
        if (key != null){
            key.setPosition(x + 55,8);
        }

    }

    public void resize(int width, int height) {
        inventory.getViewport().update(width,height,true);
        layout();
    }

    public void addKey(Image keyImage) {
        keyImage.setName("key");
        inventory.addActor(keyImage);
        layout();
    }

    public void reset() {
        inventory.clear();
        createInv();
        layout();
    }

    public void draw(){
        inventory.act();
        inventory.draw();
    }

    public void dispose() {
        inventory.dispose();
        inventoryImage.dispose();

    }
}
