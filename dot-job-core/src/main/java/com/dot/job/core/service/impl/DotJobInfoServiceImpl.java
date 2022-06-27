package com.dot.job.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.dot.job.core.entity.DotJobInfo;
import com.dot.job.core.mapper.DotJobInfoMapper;
import com.dot.job.core.service.IDotJobInfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shuxun
 * @since 2022-06-26
 */
@Service
public class DotJobInfoServiceImpl extends ServiceImpl<DotJobInfoMapper, DotJobInfo> implements IDotJobInfoService {

    public List<DotJobInfo> getAllRunningJobList(){
        return new ArrayList<>();
    }

    public void  updateStatus(int jobId,int status){

    }

    public List<DotJobInfo> getListByGroupId(int groupId) {
        return new ArrayList<>();
    }
}
