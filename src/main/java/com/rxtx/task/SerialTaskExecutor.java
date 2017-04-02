package com.rxtx.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SerialTaskExecutor implements TaskExecutor {

    private final ExecutorService executorService;

    public SerialTaskExecutor(String threadPrefix) {
        executorService = Executors.newSingleThreadExecutor(TaskThreadFactoryBuilder.newThreadFactory(threadPrefix));
    }

    @Override
    public void post(Task task) {
        executorService.submit(new TaskWrapper(task));
    }

}
