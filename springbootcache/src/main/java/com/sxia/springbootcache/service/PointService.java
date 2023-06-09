package com.sxia.springbootcache.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxia.mapper.PointMapper;
import com.sxia.model.Point;
import com.sxia.model.PointExample;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PointService {
    @Resource
    private PointMapper pointMapper;
    @Caching(
            evict = {@CacheEvict(
                    condition = "#point!=null",
                    cacheResolver = "dynamicPageCacheNames",
                    allEntries = true)
            },
            put = @CachePut(key = "#point.id",
                    condition = "#point!=null",
                    unless = "#result==null",
                    cacheNames = "point")
    )
    public Point add (Point point) {
        pointMapper.insert(point);
        return pointMapper.selectByPrimaryKey(point.getId());
    }

    @Caching(
            evict = {@CacheEvict(
                    condition = "#point!=null",
                    cacheResolver = "dynamicPageCacheNames",
                    allEntries = true),

                    @CacheEvict(key = "#point.id",
                            condition = "#point!=null",
                            cacheNames = "point")
            }
    )
    public void deleteById (Point point) {
        pointMapper.deleteByPrimaryKey(point.getId());
    }
    /**
     * @CachePut 一般更新用
     */
//    @CachePut(cacheNames = "point", key = "#point.id")

    @Caching(
            evict = {@CacheEvict(
                    condition = "#point!=null",
                    cacheResolver = "dynamicPageCacheNames",
                    allEntries = true),

            },
            put = @CachePut(key = "#point.id",
                    condition = "#point!=null",
                    unless = "#result==null",
                    cacheNames = "point")
    )
    public Point updateById(Point point) {
        pointMapper.updateByPrimaryKeySelective(point);
        return pointMapper.selectByPrimaryKey(point.getId());
    }
    @Cacheable(cacheNames = "point", key = "#id")
    public Point selectById(Integer id) {
        return pointMapper.selectByPrimaryKey(id);
    }


    @Cacheable(
//            key = "#point.name+':'+#pageable.pageSize+'_'+#pageable.pageNumber",
            cacheResolver = "dynamicPageCacheNames",
            condition = "#point!=null && #pageable!=null",
            unless = "#result==null",
            cacheNames = "point"
    )
    public PageInfo<Point> selectByPage(Point point, Pageable pageable){
        PointExample example = new PointExample();
        example.createCriteria().andPointGreaterThan(point.getPoint());
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<Point> list = pointMapper.selectByExample(example);
        PageInfo<Point> result = new PageInfo<>(list,5);
        return result;
    }

}
