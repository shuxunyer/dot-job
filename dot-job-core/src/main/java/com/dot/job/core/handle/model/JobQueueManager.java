package com.dot.job.core.handle.model;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

/**
 * @author shuxun
 */
@Slf4j
public class JobQueueManager {
    public static ConcurrentHashMap<Long, DelayQueue<DelayItem>> executorGroupMap = new ConcurrentHashMap<>();


    public static synchronized void putDelayItem(DelayItem delayItem) {
        Task task = delayItem.getItem();
        //时间到了就可以从延时队列拿出任务对象
        DelayQueue<DelayItem> taskQueue = JobQueueManager.executorGroupMap.get(task.getGroupId());
        if (taskQueue == null) {
            taskQueue = new DelayQueue();
        }
        taskQueue.offer(delayItem);
        JobQueueManager.executorGroupMap.put(task.getGroupId(), taskQueue);
    }


    /**
     * 创建任务到期延时队列
     */
//    public static DelayQueue<DelayItem> taskQueue = new DelayQueue<>();

}
