package com.mage.ziplrdelivery.common;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask {

    private TaskListener taskListener;

    public MyTimerTask(TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    @Override
    public void run() {
        taskListener.updateUi();
    }

    public interface TaskListener {
        void updateUi();
    }
}
