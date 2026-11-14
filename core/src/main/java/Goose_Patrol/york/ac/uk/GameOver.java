package Goose_Patrol.york.ac.uk;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import screens.GameOverScreen;

public class GameOver {
   public boolean GameOver = false;
   private HealthBar health_bar;
   private Timer timer;
   public GameOverScreen GameOverScreen;
   public boolean gameOver = false;

   public GameOver (HealthBar health_bar) {
       this.health_bar = health_bar;
   }

   public static boolean Time_zero() {
       return System.currentTimeMillis() - Timer.startTime >= 300000;
   }


   public void display(){
       //logic for game over
       if (health_bar.health <= 0) {
           //needs to replaced with final gameover screen
           gameOver = true;
        }
        if (Time_zero()) {
            ScreenUtils.clear(Color.BLACK);

        }


   }



}
