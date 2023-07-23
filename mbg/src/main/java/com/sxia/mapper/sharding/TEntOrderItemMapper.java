package com.sxia.mapper.sharding;

import com.sxia.model.sharding.TEntOrderItem;
import com.sxia.model.sharding.TEntOrderItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TEntOrderItemMapper {
    long countByExample(TEntOrderItemExample example);

    int deleteByExample(TEntOrderItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TEntOrderItem record);

    int insertSelective(TEntOrderItem record);

    List<TEntOrderItem> selectByExample(TEntOrderItemExample example);

    TEntOrderItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TEntOrderItem record, @Param("example") TEntOrderItemExample example);

    int updateByExample(@Param("record") TEntOrderItem record, @Param("example") TEntOrderItemExample example);

    int updateByPrimaryKeySelective(TEntOrderItem record);

    int updateByPrimaryKey(TEntOrderItem record);
}