package com.rxtx.task;

import org.apache.log4j.Logger;
import com.rxtx.Main;

public class TaskWrapper implements Runnable {

    private final Task task;

    public TaskWrapper(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
    	Logger logger = Logger.getLogger(Main.class);
        try {
            task.run();
        } catch (Exception ex) {
            // Logger.error(ex);
            logger.error("msg:" + ex.getMessage(), ex);
        }
    }
}
