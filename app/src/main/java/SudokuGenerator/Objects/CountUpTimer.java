package SudokuGenerator.Objects;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * Created by Dylan on 2017-09-07.
 */

public abstract class CountUpTimer {

    private static final int MSG = 1;
    private final long interval;
    private long base;
    private long lTimePrePause = 0;
    private boolean paused = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            synchronized (CountUpTimer.this) {
                long elapsedTime = SystemClock.elapsedRealtime() - base;
                onTick(elapsedTime);
                sendMessageDelayed(obtainMessage(MSG), interval);
            }
        }
    };

    public CountUpTimer(long interval) {
        this.interval = interval;
    }

    public void start() {
        base = SystemClock.elapsedRealtime();
        handler.sendMessage(handler.obtainMessage(MSG));
    }

    public void pause() {
        lTimePrePause = SystemClock.elapsedRealtime() - base;
        paused = true;
    }

    public void resume() {
        base = SystemClock.elapsedRealtime() - lTimePrePause;
        paused = false;
    }

    abstract public void onTick(long elapsedTime);

    public boolean getPaused() {
        return paused;
    }
}