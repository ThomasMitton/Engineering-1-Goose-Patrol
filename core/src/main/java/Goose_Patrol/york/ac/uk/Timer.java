package Goose_Patrol.york.ac.uk;

public class Timer {
    static long startTime = System.currentTimeMillis();

    public static void resetTimer(){
        startTime = System.currentTimeMillis();
    }

    public static String refreshTimer(){
        long currentTime = System.currentTimeMillis();
        long timeDifference = 300000 - (currentTime - startTime);
        long minutes = (timeDifference /1000/60) %60;
        long seconds = (timeDifference /1000) %60;
        if (seconds < 10){
            return (minutes + ":0" + seconds);
        }else{
            return (minutes + ":" + seconds);
        }
    }
}
