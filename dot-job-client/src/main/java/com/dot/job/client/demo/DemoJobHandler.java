package com.dot.job.client.demo;


import com.dot.job.core.handle.IJobHandler;
import com.dot.job.core.handle.annotation.DotJobHandle;
import com.dot.job.core.handle.model.TaskResult;

/**
 * @author shuxun
 */
@DotJobHandle
public class DemoJobHandler implements IJobHandler {

    @Override
    public TaskResult execute() {
        System.out.println("demoJobHandler execute success");
        return TaskResult.SUCCESS;
    }
}
