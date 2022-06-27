# dot-job整体架构

dot-job是一个分布式任务执行器，分为调度器job-schedule和执行器job-executor两部分。<br />
1.1 调度器相当于是dot-job服务端，执行器相当于是业务服务集成dot-job服务的客户端。<br />
通过将调度器和执行器隔离，使得任务的执行流程解耦，同时提供扩展性。<br />
每个服务注册成一个执行器，服务下的每个实例相当于执行节点，可通过任务容量决定是否需要扩展执行节点。<br />

**调度器job-schedule的职责**:<br />
1 对任务job进行管理，可执行注册任务、提供任务管理的增删改查.<br />
2 服务启动时初始化一个后台线程，重复循环将库表中可执行的任务存入任务队列中.<br />

**执行器job-executor的职责:**  
1 从任务队列中获取当前执行器关联的任务,将待执行任务扔给线程池执行.<br />
2 将执行结果保存至log日志表中.<br />


**整体架构和流程图如下图所示**

![dot-job架构](https://github.com/shuxunyer/dot-job/blob/main/dot-job%20%E6%9E%B6%E6%9E%84%E5%9B%BE.jpg)

![dot-job流程图](https://github.com/shuxunyer/dot-job/blob/main/dot-job%E6%B5%81%E7%A8%8B%E5%9B%BE.jpg)


# 其他要求
**1 监控，如何超时检测:**<br />  
超时:如果在指定时间内没有返回结果，就不再等待结果，这个时候就不再是同步执行了，而是用FutureTask异步获取
结果，如果在指定超时时间内没有得到结果，就抛出异常，赋值结果502(也是属于失败的一种)。<br />
**2 可重试:**<br />
2.1 调度器怎么知道任务执行失败了。<br />    
2.2 执行失败后怎么处理。<br /> 
如果任务失败，在job_log表中会有相应记录，服务启动时附加一个后台线程不断扫描失败的线程，将任务包装后放入待执行任务队列。<br />
**3 如果你在使用数据库，如何让交互更加快:**<br /> 
可以加一层缓存，后端管理业务涉及到任务的更新时，通过api接口触发缓存的更新，调度器只需从缓存里拿到最新的任务状态。<br />


注：代码没能全部调试，已足够丰富完整。

## 数据库表相关设计
![dot-job表关联图](https://github.com/shuxunyer/dot-job/blob/main/dot-job%E8%A1%A8%E5%85%B3%E8%81%94%E5%85%B3%E7%B3%BB%E5%9B%BE.jpg)

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
| group_id | 所属分组     | bigint   |                 |
| ip      | 节点ip地址   | varchar  | not null        |
| port    | 节点端口号   | varchar  |                 |
| executor_status | 节点是否禁用 | int      | 0否1是,not null |

3、dot_job_info，任务表字段信息

| 字段     | 关键字   | 字段类型 | 备注                                 |
| -------- | -------- | -------- | ------------------------------------ |
| id       | 关键字   | bigint   | 自增长                               |
| job_group  | 所属分组 | bigint   |                                      |
| name     | 任务名   | varchar  | not null                             |
| executor_handler | 执行类的handler对象名称 | varchar  | not null                             |
| executor_timeout | 任务执行超时时间 | varchar  | not null                             |
| executor_fail_retry_count | 任务执行失败重试次数 | varchar  | not null                             |
| trigger_status   | 状态     | int      | 0未开始1待执行2执行中3异常, not null |
                 |


# 调度器对任务的管理

DotJobInfoController

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

