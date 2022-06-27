package com.dot.job.core.dao;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class TaskDao {
    public void insert(Task task) {

    }

    public void disable(Long id, int val) {

    }

    public void retry(Long id) {

    }

    public void finish(Long taskId) {

    }

    public Collection<? extends Task> findRetriedTasks(Set<Long> groupIds) {
        return null;
    }

    public Collection<? extends Task> findTasksByGroupIds(Set<Long> groupIds) {
        return null;
    }

    public int updateStatus(Long id, int status) {
        return -1;
    }
}
