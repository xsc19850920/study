package com.sxia.springbootcache.controller;

import com.github.pagehelper.PageInfo;
import com.sxia.model.Point;
import com.sxia.model.User;
import com.sxia.springbootcache.service.PointService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/point/")
public class PointController {
    @Resource
    private PointService pointService;
    @PostMapping("selectPages/{pn}")
    public PageInfo<Point> selectUser(@PathVariable("pn") Integer pn, @RequestBody Point point){
        return pointService.selectByPage(point, PageRequest.of(pn, 5));
    }

    @PostMapping("selectById/{id}")
    public Point selectById(@PathVariable("id") Integer id){
        return pointService.selectById(id);
    }

    @PostMapping("updateById")
    public Point updateById(@RequestBody Point point){
        return pointService.updateById(point);
    }

    @PostMapping("deleteById")
    public void deleteById(@RequestBody Point point){
         pointService.deleteById(point);
    }
    @PostMapping("add")
    public Point add(@RequestBody Point point){
        return pointService.add(point);
    }
}
