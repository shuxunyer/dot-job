# dot-job整体架构

dot-job是一个分布式任务执行器，分为调度器job-schedule和执行器job-executor两部分,执行器可看成是集成dot-job服务的客户端，即我们的业务服务
调度器job-schedule的职责:
1 对任务job进行管理，可执行注册任务、禁用任务、重试任务、重启(恢复)任务
2 服务启动时初始化一个后台线程，重复循环将库表中可执行的任务存入任务队列中


执行器job-executor的职责
1 从任务队列中获取当前执行器关联的任务,将待执行任务扔给线程池执行
2 将执行结果保存至log日志表中


整体架构如下图所示

![jm-job架构](https://github.com/agncao/jm-job/blob/master/jm-job%E6%9E%B6%E6%9E%84.png)

注：代码只能作为设计参考，未能完全调试代码，但以足够丰富完整。


## 数据库表相关设计
1、dot_execute_group，执行器组表字段信息

| 字段 | 字段注释 | 字段类型 | 备注         |
| ---- | -------- | -------- | ------------ |
| id   | 组id   | bigint   | 自增长       |
| group_name | 组名     | varchar  |              |
| group_desc | 组说明信息     | varchar  |              |

2、dot_executor，执行节点表字段信息(每一个客户端实例相当于一条记录)

| 字段    | 字段注释     | 字段类型 | 备注            |
| ------- | ------------ | -------- | --------------- |
| id      | 关键字       | bigint   | 自增长          |
| ip      | 节点ip地址   | varchar  | not null        |
| port    | 节点端口号   | varchar  |                 |
| executor_status | 节点是否禁用 | int      | 0否1是,not null |
| group_id | 所属分组     | bigint   |                 |

3、dot_job_info，任务表字段信息

| 字段     | 关键字   | 字段类型 | 备注                                 |
| -------- | -------- | -------- | ------------------------------------ |
| id       | 关键字   | bigint   | 自增长                               |
| name     | 任务名   | varchar  | not null                             |
| executor_handler | 执行类的handler对象名称 | varchar  | not null                             |
| executor_timeout | 任务执行超时时间 | varchar  | not null                             |
| executor_fail_retry_count | 任务执行失败重试次数 | varchar  | not null                             |
| trigger_status   | 状态     | int      | 0未开始1待执行2执行中3异常, not null |
| disable  | 是否禁用 | int      | 0否1是,not null                      |
| retry    | 是否重试 | int      | 0否1是,not null                      |
| job_group  | 所属分组 | bigint   |                                      |


# 调度器对任务的管理

TaskController

```java
    /**
     * 新增一个任务
     */
    public CommonResult<String> addJob(@RequestBody @Valid DotJobInfo dotJobInfo)


    /**
     * 禁用,重新启用任务，相当于更新任务的状态
     * @param id 任务id
     * @return
     */
    public CommonResult<String> udpateJob(@RequestBody @Valid DotJobInfo dotJobInfo)

  
```


