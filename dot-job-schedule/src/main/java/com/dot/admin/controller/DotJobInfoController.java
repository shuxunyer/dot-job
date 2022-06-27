package com.dot.admin.controller;


import com.dot.admin.common.CommonResult;
import com.dot.job.core.entity.DotJobInfo;
import com.dot.job.core.service.impl.DotJobInfoServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author shuxun
 * @since 2022-06-26
 */
@RestController
@RequestMapping("/dot-job-info")
public class DotJobInfoController {

    @Autowired
    private DotJobInfoServiceImpl dotJobInfoService;

    @ApiOperation("获取所有的任务列表")
    @PostMapping("/getAllJobList")
    public CommonResult<List<DotJobInfo>> getAllJobList() {
        List<DotJobInfo> dotJobInfoList = dotJobInfoService.list();
        return CommonResult.success(dotJobInfoList);
    }

    @ApiOperation("获取指定分组下的所有的任务列表")
    @PostMapping("/getAllJobListByGroupId/{groupId}")
    public CommonResult<List<DotJobInfo>> getAllJobListByGroupId(@PathVariable int groupId) {
        List<DotJobInfo> dotJobInfoList = dotJobInfoService.getListByGroupId(groupId);
        return CommonResult.success(dotJobInfoList);
    }

    @ApiOperation("新增任务")
    @PostMapping("/addJob")
    public CommonResult<String> addJob(@RequestBody @Valid DotJobInfo dotJobInfo) {
        dotJobInfoService.save(dotJobInfo);
        return CommonResult.success("新增成功");
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{jobId}")
    public CommonResult<String> removeByJobId(@PathVariable int jobId) {
        dotJobInfoService.removeById(jobId);
        return CommonResult.success("删除成功");
    }

    @ApiOperation("修改任务")
    @PostMapping("/updateJob")
    public CommonResult<String> udpateJob(@RequestBody @Valid DotJobInfo dotJobInfo) {
        dotJobInfoService.updateById(dotJobInfo);
        return CommonResult.success("新增成功");
    }
}
