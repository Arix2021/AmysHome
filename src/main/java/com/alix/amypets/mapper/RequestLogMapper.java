package com.alix.amypets.mapper;

import com.alix.amypets.bean.RequestLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface RequestLogMapper extends BaseMapper<RequestLog> {


}
