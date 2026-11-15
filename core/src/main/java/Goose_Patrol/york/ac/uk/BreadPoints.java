package Goose_Patrol.york.ac.uk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;


public class BreadPoints {
    private static final Sound sound = Gdx.audio.newSound(Gdx.files.internal("beep.mp3"));
    private static int counter = 10;

    public static void incrementPoints(){
        counter++;
    }

    public static void decrementPoints(){
        if (counter > 0) {
            counter--;
        }else{
            sound.play();
        }
    }

    public static int getCounter(){
        return counter;
    }

    public static void resetCounter(){
        counter = 20;
    }
}
