package com.sxia.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class EasyExcelUtils {
    /**
     * 每隔1000条存储数据库，然后清理list，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;
    private static final int countOfSheet = 10000;
    /**
     * 临时存储
     */
    private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    /**
     * 单线程导出到的excel的第一个sheet中
     * @param t 实体类
     * @param mapper 实体类对应的mapper
     * @param queryWrapper 查询条件
     * @param response
     * @throws IOException
     */
    public <T,M extends BaseMapper<T>, S extends ServiceImpl<M,T>> void exportExcelSingle(T t,M mapper, LambdaQueryWrapper queryWrapper , HttpServletResponse response) throws IOException {

        setExportHeader(response);

        List<T> list = mapper.selectList(queryWrapper);

        EasyExcel.write(response.getOutputStream(), t.getClass()).sheet().doWrite(list);
    }
    /**
     * 多线程导出
     * @param clazz 实体类对应的class
     * @param mapper 实体类对应的mapper
     * @param queryWrapper 查询条件
     * @param response
     * @throws InterruptedException
     * @throws IOException
     */
    public <T,M extends BaseMapper<T>, S extends ServiceImpl<M,T>> void exportExcel(Class clazz, M mapper, LambdaQueryWrapper queryWrapper , HttpServletResponse response) throws InterruptedException, IOException {
        setExportHeader(response);
        Long count = mapper.selectCount(queryWrapper);
        Integer pages = count % countOfSheet == 0 ? Long.valueOf(count / countOfSheet).intValue() : Long.valueOf(count / countOfSheet).intValue() + 1;
        ExecutorService executorService = Executors.newFixedThreadPool(pages);
        CountDownLatch countDownLatch = new CountDownLatch(pages);
        Map<Integer, Page<T>> pageMap = new HashMap<>();
        for (int i = 0; i < pages; i++) {
            int finalI = i;
            executorService.submit(()->{
                Page<T> page = new Page<>();
                page.setCurrent(finalI + 1);
                page.setSize(countOfSheet);
                Page<T> selectPage = mapper.selectPage(page, queryWrapper);
                pageMap.put(finalI, selectPage);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), clazz).build()) {
            for (Map.Entry<Integer, Page<T>> entry : pageMap.entrySet()) {
                Integer num = entry.getKey();
                Page<T> pageInfo = entry.getValue();
                WriteSheet writeSheet = EasyExcel.writerSheet(num, "模板" + num).build();
                excelWriter.write(pageInfo.getRecords(), writeSheet);
            }
            excelWriter.finish();
        }
    }

    /**
     * 单独处理多种sheet
     * @param clazz 实体类
     * @param service 实体类对应的service
     * @param file
     * @throws IOException
     */
    public <T,M extends BaseMapper<T>, S extends ServiceImpl<M,T>> void importExcelSingle(Class clazz , S service , MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), clazz, new ReadListener<T>() {
            List<T> cachedData = new ArrayList<>(BATCH_COUNT);
            @Override
            public void invoke(T t, AnalysisContext analysisContext) {
                cachedData.add(t);
                if (cachedData.size() >= BATCH_COUNT) {
                    batchSaveData();
                    cachedData = new ArrayList<>(BATCH_COUNT);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                batchSaveData();
            }

            @Async
            public void batchSaveData() {
                log.info("{}次数据，将要存傽数据啦!", cachedData.size());
                service.saveBatch(cachedData);
                log.info("存傽数据啦成功!");
            }
        }).doReadAll();
    }
    /**
     *多线程导入
     * @param clazz 实体类class
     * @param service 实体类对应的service
     * @param numberOfSheets 多少个sheet页
     * @param file
     * @throws IOException
     * @throws InterruptedException
     */
    public <T,M extends BaseMapper<T>, S extends ServiceImpl<M,T>> void importExcel(Class clazz, S service ,int numberOfSheets,MultipartFile file) throws IOException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Object>> tasks = new ArrayList<>();
        for (int i = 0; i < numberOfSheets; i++) {
            int num = i;
            tasks.add(() -> {
                EasyExcel.read(file.getInputStream(), clazz, new ReadListener<T>() {
                            List<T> cachedData = new ArrayList<>(BATCH_COUNT);
                            @Override
                            public void invoke(T t, AnalysisContext analysisContext) {
                                cachedData.add(t);
                                if (cachedData.size() >= BATCH_COUNT) {
                                    batchSaveData();
                                    cachedData = new ArrayList<>(BATCH_COUNT);
                                }
                            }

                            @Override
                            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                                batchSaveData();
                            }

                            @Async
                            public void batchSaveData() {
                                log.info("{}条数据，开始存储数据库!", cachedData.size());
                                service.saveBatch(cachedData);
                                log.info("存储数据库成功!");
                            }
                        })
                        .sheet(num).doRead();
                return null;
            });
        }
        executorService.invokeAll(tasks);
    }

    private static void setExportHeader(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + System.currentTimeMillis()+ ".xlsx");
    }


}
