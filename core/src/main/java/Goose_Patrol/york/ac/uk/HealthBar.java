package Goose_Patrol.york.ac.uk;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HealthBar {
    
    private ShapeRenderer bar_health;
    
    public float health = 100;
    private float health_max = 100;
    
    public float getHealth() {
        return health;
    }
    
    public void updatehealth(float newHealth) {
        health = newHealth;
    }
    
    
    public HealthBar() {
        bar_health = new ShapeRenderer();
    }
    
    public void display() {
        
       
        
        float x_health_bar = 10;
        float y_health_bar = 450;
        
        float width = 200;
        float height = 20;
        
        float healthPercentage =  health/health_max;
        
        bar_health.begin(ShapeRenderer.ShapeType.Filled);
        
        bar_health.setColor(Color.GRAY);
        bar_health.rect(x_health_bar,y_health_bar,width,height);
        
        if (health > 0){
            bar_health.setColor(Color.GREEN);
            bar_health.rect(x_health_bar,y_health_bar,width*healthPercentage,height);
        }
        
        bar_health.end();
        
    }
    
    public void dispose() {
        bar_health.dispose();
    }
    
}
