package Goose_Patrol.york.ac.uk;

public class BreadPoints {
    public double counter = 0;

    public void increment(){
        this.counter++;
    }

    public void decrement(){
        this.counter--;
    }

    public double getCounter(){
        return counter;
    }

    public void resetCounter(){
        this.counter = 0;
    }
}
