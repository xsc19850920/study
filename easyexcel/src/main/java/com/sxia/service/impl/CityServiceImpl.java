package com.sxia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxia.EasyexcelApplication;
import com.sxia.city.entity.CityCn;
import com.sxia.city.mapper.CityCnMapper;
import com.sxia.excel.EasyExcelUtils;
import com.sxia.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service(value = "cityService")
public class CityServiceImpl extends ServiceImpl<CityCnMapper, CityCn> implements CityService {

    @Resource
    private CityCnMapper cityCnMapper;

    @Override
    public void importExcel(MultipartFile file,int numberOfSheets) throws IOException, InterruptedException {
        long beforeTime = System.currentTimeMillis();
        new EasyExcelUtils().importExcel(CityCn.class,(CityServiceImpl)(EasyexcelApplication.ac.getBean("cityService")),numberOfSheets,file);
        long afterTime = System.currentTimeMillis();
        log.info("耗时:{}", afterTime - beforeTime);
    }

    @Override
    public void exportExcel(HttpServletResponse response) throws IOException, InterruptedException {
        long beforeTime = System.currentTimeMillis();
        LambdaQueryWrapper<CityCn> queryWrapper = new LambdaQueryWrapper<>();
        new EasyExcelUtils().exportExcel(CityCn.class,cityCnMapper,queryWrapper,response);
        long afterTime = System.currentTimeMillis();
        log.info("耗时:{}", afterTime - beforeTime);
    }
}

