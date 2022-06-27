package com.dot.admin.component;

import com.dot.job.core.entity.DotJobInfo;
import com.dot.job.core.common.enums.TaskStatus;
import com.dot.job.core.handle.model.DelayItem;
import com.dot.job.core.handle.model.JobQueueManager;
import com.dot.job.core.service.impl.DotJobInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author shuxun
 */
@Component
public class JobSchedule implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private DotJobInfoServiceImpl dotJobInfoService;

    /**
     * 调度线程池，默认为1个,可提供扩展性
     * 不断从库表中奖待执行任务放入队列
     */
    private ExecutorService schedulePool = Executors.newFixedThreadPool(1);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        /**
         * 启动调度线程池
         */
        schedulePool.execute(new TaskSchedule());
    }

    /**
     * 调度线程执行将即将待执行任务放入延迟队列中
     */
    class TaskSchedule implements Runnable {
        public TaskSchedule() {
        }

        @Override
        public void run() {
            while (true) {
                try {
                    //查询当前节点的所有任务
//                    List<Task> taskList = taskService.getNotStartedList(IpUtil.getIp());
                    List<DotJobInfo> taskList = dotJobInfoService.getAllRunningJobList();
                    if (taskList == null || taskList.isEmpty()) {
                        Thread.sleep(100);
                        continue;
                    }

                    //找到还有10分钟才执行的任务
                    long expectedTime = System.currentTimeMillis() + 10 * 60 * 1000;
                    taskList = taskList.stream().filter(ele -> ele.getTriggerNextTime() <= expectedTime).collect(Collectors.toList());
                    if (taskList == null || taskList.isEmpty()) {
                        continue;
                    }

                    for (DotJobInfo dotJobInfo : taskList) {
                        //将任务设置为待执行
                        dotJobInfo.setTriggerStatus(TaskStatus.Waiting.getVal());
                        DelayItem delayTask = new DelayItem(dotJobInfo);
                        JobQueueManager.putDelayItem(delayTask);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
