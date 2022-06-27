package com.dot.job.core.handle.model;

import com.dot.job.core.entity.DotJobInfo;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延时队列中的任务
 */
public class DelayItem implements Delayed {

    private final long delay;
//    private final Task task;
    private final DotJobInfo jobInfo;

    public DelayItem(DotJobInfo jobInfo) {
        this.jobInfo=jobInfo;
        this.delay=jobInfo.getTriggerNextTime()-System.currentTimeMillis();
    }


    /**
     * 需要实现的接口，获得延迟时间   用过期时间-当前时间
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.delay , TimeUnit.MILLISECONDS);
    }

    /**
     * 用于延迟队列内部比较排序   当前时间的延迟时间 - 比较对象的延迟时间
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) -o.getDelay(TimeUnit.MILLISECONDS));
    }

    public DotJobInfo getItem(){
        return this.jobInfo;
    }
}
