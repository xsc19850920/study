package com.sxia.mapper.sharding;

import com.sxia.model.sharding.TEntOrder;
import com.sxia.model.sharding.TEntOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TEntOrderMapper {
    long countByExample(TEntOrderExample example);

    int deleteByExample(TEntOrderExample example);

    int insert(TEntOrder record);

    int insertSelective(TEntOrder record);

    List<TEntOrder> selectByExample(TEntOrderExample example);

    int updateByExampleSelective(@Param("record") TEntOrder record, @Param("example") TEntOrderExample example);

    int updateByExample(@Param("record") TEntOrder record, @Param("example") TEntOrderExample example);
}