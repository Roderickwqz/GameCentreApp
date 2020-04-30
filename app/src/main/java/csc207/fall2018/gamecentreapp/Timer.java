package csc207.fall2018.gamecentreapp;

import android.os.SystemClock;
import android.widget.Chronometer;

public class Timer {

    private Chronometer chronometer;


    public Timer(Chronometer chronometer) {
        this.chronometer = chronometer;
    }

    public void setUpTimer(int seconds) {
        long lastPause = (long) (seconds * 1000);
        this.chronometer.setBase(SystemClock.elapsedRealtime() - lastPause);
    }

    public String returnStringTime() {
        return this.chronometer.getText().toString();
    }

    public void start() {
        this.chronometer.start();
    }

    public void stop(){
        this.chronometer.stop();
    }
}
