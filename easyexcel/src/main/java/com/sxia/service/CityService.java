package com.sxia.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CityService {
    void importExcel(MultipartFile file,int numberOfSheets) throws IOException, InterruptedException;

    void exportExcel(HttpServletResponse response) throws IOException, InterruptedException;
}
