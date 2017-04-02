package com.rxtx.task;


public class TaskUtils {

    private final static ConcurrentTaskExecutor gConcurrentTaskExecutor = new ConcurrentTaskExecutor("GlobalConcurrent");

    @Deprecated
    public static TaskExecutor getGlobalExecutor() {
        return gConcurrentTaskExecutor;
    }

    public static TaskExecutor createSerialExecutor(String threadPrefix) {
        return new SerialTaskExecutor(threadPrefix);
    }

    public static void postGlobleTask(Task task) {
        gConcurrentTaskExecutor.post(task);
    }

}
