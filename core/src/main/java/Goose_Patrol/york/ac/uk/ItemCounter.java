package Goose_Patrol.york.ac.uk;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;




public class ItemCounter {

    public int itemsUsed = 0;
    public int EventsActivated = 0;

    private BitmapFont font;
    private SpriteBatch batch;

    public ItemCounter(SpriteBatch batch) {
        this.batch = batch;
        this.font = new BitmapFont();
    }



    public void display() {
        font.setColor(Color.BLACK);
        font.getData().setScale(1);
        float lineHeight = font.getLineHeight();
        font.draw(batch,"Items used: " + itemsUsed, 10, lineHeight + 50);
        font.draw(batch,"Events Activated: " + EventsActivated, 10, lineHeight * 3);
    }



    public void reset() {
        itemsUsed = 0;
        EventsActivated = 0;
    }

    public void incrementItems(){
        itemsUsed++;
    }

    public void incrementEvents(){
        EventsActivated++;
    }

    public void dispose() {
        font.dispose();
    }

}
