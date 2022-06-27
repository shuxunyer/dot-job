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
@TableName("dot_execute_group")
public class DotExecuteGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("group_name")
    private String groupName;

    @TableField("group_desc")
    private String groupDesc;

    @TableField("create_time")
    private LocalDateTime createTime;


}
