package Goose_Patrol.york.ac.uk;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;




public class ItemCounter {

    public int itemsUsed = 0;
    
    private BitmapFont font;
    private SpriteBatch batch;
    
    public ItemCounter(SpriteBatch batch) {
        this.batch = batch;
        this.font = new BitmapFont();
    }
    
    
    
    public void display() {
        font.setColor(Color.BLACK);
        font.getData().setScale(2);
        font.draw(batch,"Items used: " + itemsUsed, 20, 30);
    }
    
    public void dispose() {
        font.dispose();
    }
    
}
