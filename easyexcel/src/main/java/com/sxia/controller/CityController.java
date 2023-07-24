package com.sxia.controller;

import com.sxia.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping(value="/excel/import")
    public String importExcel(@RequestParam(name = "file") MultipartFile file,@RequestParam(name = "numberOfSheets") Integer numberOfSheets) throws IOException, InterruptedException {
        cityService.importExcel(file,numberOfSheets);
        return "导入成功";
    }

    @PostMapping(value = "/excel/export")
    public void exportExcel(HttpServletResponse response) throws IOException, InterruptedException {
        cityService.exportExcel(response);
    }
}
