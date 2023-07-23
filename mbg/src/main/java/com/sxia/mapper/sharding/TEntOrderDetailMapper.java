package com.sxia.mapper.sharding;

import com.sxia.model.sharding.TEntOrderDetail;
import com.sxia.model.sharding.TEntOrderDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TEntOrderDetailMapper {
    long countByExample(TEntOrderDetailExample example);

    int deleteByExample(TEntOrderDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TEntOrderDetail record);

    int insertSelective(TEntOrderDetail record);

    List<TEntOrderDetail> selectByExample(TEntOrderDetailExample example);

    TEntOrderDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TEntOrderDetail record, @Param("example") TEntOrderDetailExample example);

    int updateByExample(@Param("record") TEntOrderDetail record, @Param("example") TEntOrderDetailExample example);

    int updateByPrimaryKeySelective(TEntOrderDetail record);

    int updateByPrimaryKey(TEntOrderDetail record);
}