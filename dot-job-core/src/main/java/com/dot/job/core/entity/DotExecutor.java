package com.dot.job.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
@TableName("dot_executor")
public class DotExecutor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("id")
    private Integer id;

    @TableField("group_id")
    private Integer groupId;

    @TableField("executor_status")
    private Integer executorStatus;

    @TableField("ip")
    private String ip;

    @TableField("port")
    private Integer port;


}
