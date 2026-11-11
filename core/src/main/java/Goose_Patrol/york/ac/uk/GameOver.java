package Goose_Patrol.york.ac.uk;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOver {
   public boolean GameOver = false;
   private HealthBar health_bar;
   private Timer timer;
   
   public GameOver (HealthBar health_bar) {
       this.health_bar = health_bar;
   }
 
   
   public void display(){
       //logic for game over 
       if (health_bar.health <= 0) {
           //needs to replaced with final gameover screen
           ScreenUtils.clear(Color.BLACK);
        }
       //else if (Timer.refreshTimer().equals("0")); {
            //ScreenUtils.clear(Color.BLACK);
            
        //} 
       
   }
   
   
    
}
