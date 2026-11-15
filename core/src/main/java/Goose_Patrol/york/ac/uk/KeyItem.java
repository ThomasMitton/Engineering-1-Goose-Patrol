package Goose_Patrol.york.ac.uk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;



public class KeyItem extends Entity{
   
    private int width;
    private int height;
    private Texture keyImage;
    public boolean collected = false;
    public Image image2;


    public KeyItem(String texture, Vector2 pos, int width, int height) {
        super(texture, pos);
        this.width = width;
        this.height = height;
        super.hitbox();
    }
    
    public void collect(Inventory Inventory){
        if (collected) return;
        collected = true;
        
        
        keyImage = new Texture(Gdx.files.internal("key.png"));
        image2 = new Image(keyImage);
        image2.setSize(80, 80);
        Inventory.addKey(image2);
        
       
        
    }
    
    public void use(){
        //locks the pen when in use
    }
    

    public void dispose() {
        super.dispose();
    }
}

    

