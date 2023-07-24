package com.sxia.city.mapper;

import com.sxia.city.entity.CityCn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxia.common.BaseDaoMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sxia
 * @since 2023-07-23
 */
@Mapper
public interface CityCnMapper extends BaseDaoMapper<CityCn> {
    void batchInsertData(List<CityCn> list);
}
