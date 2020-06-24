package com.vaapglkns.todolist.utils;

import android.os.Handler;

public class ExitStrategy {
    private static boolean isAbleToExit = false;

    private static Handler h = new Handler();

    public static boolean canExit() {
        return isAbleToExit;
    }

    public static void startExitDelay(long delayMillis) {
        isAbleToExit = true;
        h.postDelayed(runnable, delayMillis);
    }

    static Runnable runnable = new Runnable() {

        @Override
        public void run() {
            isAbleToExit = false;
        }
    };

    public static void shutDown() {
        isAbleToExit = false;
        h.removeCallbacks(runnable);
    }
}
