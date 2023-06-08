package com.sxia.springbootcache.service;

import com.sxia.mapper.PointMapper;
import com.sxia.model.Point;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@CacheConfig(cacheNames="myCache")
public class PointService {
    @Resource
    private PointMapper repository;

    @Cacheable(key = "#id")
    public Point getEntityById(Integer id) {
        return repository.selectByPrimaryKey(id);
    }

    @CachePut(key = "#entity.id")
    public void saveEntity(Point entity) {
        repository.insert(entity);
    }

    @CacheEvict(key = "#id")
    public void deleteEntityById(Integer id) {
        repository.deleteByPrimaryKey(id);
    }

}
