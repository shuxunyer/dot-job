package com.dot.job.core.handle;


import com.dot.job.core.handle.model.TaskResult;

public interface IJobHandler {

	/**
	 * 作业的接口，需要用户实现
	 * @return
	 * @throws Exception
	 */
	TaskResult execute();
	
}
