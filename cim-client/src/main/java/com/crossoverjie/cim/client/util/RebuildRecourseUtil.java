package com.crossoverjie.cim.client.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * @program: cim
 * @description:
 * @author: 王强
 * @create: 2020-12-08 11:23
 */
public class RebuildRecourseUtil {


    /***
     * build an thread executor
     * @return
     */
    private ExecutorService buildExecutor(ExecutorService executorService) {
        if (executorService == null || executorService.isShutdown()) {
            ThreadFactory sche = new ThreadFactoryBuilder()
                    .setNameFormat("reConnect-job-%d")
                    .setDaemon(true)
                    .build();
            executorService = new ScheduledThreadPoolExecutor(1, sche);
            return executorService;
        } else {
            return executorService;
        }
    }
}
