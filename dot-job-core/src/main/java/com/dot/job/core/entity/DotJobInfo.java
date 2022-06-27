package com.dot.job.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author shuxun
 * @since 2022-06-26
 */
@Getter
@Setter
@TableName("dot_job_info")
public class DotJobInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 执行器主键ID
     */
    @TableField("job_group")
    private Integer jobGroup;

    /**
     * 任务执行CRON
     */
    @TableField("job_cron")
    private String jobCron;

    @TableField("job_desc")
    private String jobDesc;

    @TableField("add_time")
    private LocalDateTime addTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 作者
     */
    @TableField("author")
    private String author;

    /**
     * 报警邮件
     */
    @TableField("alarm_email")
    private String alarmEmail;

    /**
     * 执行器任务handler
     */
    @TableField("executor_handler")
    private String executorHandler;

    /**
     * 执行器任务参数
     */
    @TableField("executor_param")
    private String executorParam;

    /**
     * 任务执行超时时间，单位秒
     */
    @TableField("executor_timeout")
    private Integer executorTimeout;

    /**
     * 失败重试次数
     */
    @TableField("executor_fail_retry_count")
    private Integer executorFailRetryCount;

    /**
     * 调度状态：0-停止，1-运行
     */
    @TableField("trigger_status")
    private Integer triggerStatus;

    /**
     * 上次调度时间
     */
    @TableField("trigger_last_time")
    private Long triggerLastTime;

    /**
     * 下次调度时间
     */
    @TableField("trigger_next_time")
    private Long triggerNextTime;


}
