package com.sxia.common;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface BaseDaoMapper<T> extends BaseMapper<T> {

    void batchInsertData(List<T> list);
}

