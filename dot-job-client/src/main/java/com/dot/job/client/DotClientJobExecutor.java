package com.dot.job.client;


import com.dot.job.core.entity.DotJobInfo;
import com.dot.job.core.common.enums.TaskStatus;
import com.dot.job.core.handle.IJobHandler;
import com.dot.job.core.handle.model.DelayItem;
import com.dot.job.core.handle.model.JobQueueManager;
import com.dot.job.core.service.impl.DotJobInfoServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.DelayQueue;

/**
 * @author shuxun
 */
@Component
public class DotClientJobExecutor implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private DotJobInfoServiceImpl dotJobInfoService;


    /**
     * 通过配置文件来设置
     */
    private String GROUP_NAME = "job-client1";
    /**
     * 执行器所属组名称，通过组名称去获取该组下队列中的任务
     * 或者通过配置文件中的GROUP_NAME来关联到GROUP_ID
     */

    private Long GROUP_ID = 1L;


    private ApplicationContext applicationContext;

    /**
     * 任务执行线程池
     */
    private DotJobExecutorPool dotJobExecutorPool;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        /**
         * 初始化执行任务的线程池
         */
        dotJobExecutorPool = new DotJobExecutorPool(1000);

        /**
         * 启动监听线程监听任务队列，让任务线程池线程去执行任务
         */
        listeningTaskExecute();
    }

    /**
     * 监听线程不断从任务队列中拿去任务执行
     */
    public void listeningTaskExecute() {
        try {
            while (true) {
                try {
                    //时间到了就可以从延时队列拿出任务对象
                    DelayQueue<DelayItem> taskQueue = JobQueueManager.executorGroupMap.get(GROUP_ID);
                    DelayItem item = taskQueue.take();
                    if (item == null || item.getItem() == null) {
                        continue;
                    }
                    DotJobInfo dotJobInfo = item.getItem();
                    dotJobExecutorPool.execute(() -> {
                        //将任务设置成执行中
                        dotJobInfoService.updateStatus(dotJobInfo.getId(), TaskStatus.Doing.getVal());
                        String beanName = dotJobInfo.getExecutorHandler();
                        IJobHandler jobHandler = applicationContext.getBean(beanName, IJobHandler.class);
                        jobHandler.execute();

                        //任务执行完成
                        dotJobInfoService.updateStatus(dotJobInfo.getId(), TaskStatus.Done.getVal());
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
