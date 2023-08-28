package com.sxia.jsoup;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class GetWorkInfo {

    private static List<WorkInfo> all = new LinkedList<>();
    public static void main(String[] args) {

        // 注意几个请求参数
        String workName="java";//职业名称

        for (int i = 1; i < 5; i++) {//当前页码i
            String url = "https://www.zhipin.com/c101280100/?query="+workName+"&page="+i+"&ka=page-"+i;

            String html = getHtml(url);
            cleanHtml(html);
        }

        System.out.println("一共获取到"+all.size()+"条与["+workName+"相关]的招聘信息");
        for (WorkInfo workInfo : all) {

            System.out.println(workInfo);
        }

//        //保存所有workinfo tag 到文本
//        FileWriter writer;
//        try {
//            writer = new FileWriter("E:/tags.txt");
//
//            for (WorkInfo workInfo : all) {
//                for (String tag : workInfo.getTags()) {
//                    writer.write(tag+"  ");
//                }
//            }
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //保存数据到excel
//        ExcelExportUtil.exportToFile( all,"E:\\re.xls");
    }

    /**
     * 模拟浏览器请求，获取网页内容
     * @param url
     * @return
     */
    public static String getHtml(String url)  {

        HttpRequest httpGet = HttpUtil.createGet(url);
        httpGet.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36 Edg/89.0.774.54");
        httpGet.header("Cookie", "lastCity=101070200; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1690708911; wd_guid=5db079fb-1d96-47cd-bed4-48116f20c86d; historyState=state; boss_login_mode=wechat; _bl_uid=zql2gkyhpbv8F8gCR30wb2Ogky6w; collection_pop_window=1; wt2=DjRWB8gN4qlC6oPd24O-jSCG9Vt68DDbew265yhKKSECc9Iq2sPl9WcKJbv_cmPCuzbtF8epHu5Jyni1J5C1QDQ~~; wbg=0; __zp_seo_uuid__=4e8bdecc-e5f1-4d81-bf8c-a4989e255cb0; __g=-; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1692622066; __zp_stoken__=8286eIz8eeWJsf1dAGhQEUHgcVCN6eA9sMjQXYwh%2FeFZJAXMybExxKSNiBzsaFwpILSBNOwhlYQBOdWoYFkgEGXwMawwCWjljcXI%2BVzIVCF4VR0cdFGN2HUEHbTYyeDpNbxl%2FRHVsbAVyNBY%3D; __c=1690708911; __l=r=https%3A%2F%2Fwww.bing.com%2F&l=%2Fwww.zhipin.com%2Fweb%2Fgeek%2Fjob%3Fquery%3D%26city%3D101070200&s=3&g=&friend_source=0&s=3&friend_source=0; __a=96934796.1690708911..1690708911.48.1.48.48");
        httpGet.setConnectionTimeout(35000);
        HttpResponse response = httpGet.execute();
        if(response.isOk()){
            return response.body();
        }

        return null;
    }

    public static Object cleanHtml(String html){
        org.jsoup.nodes.Document document = Jsoup.parseBodyFragment(html);
        //获取body
        Element body = document.body();
        Elements jobPrimaries = body.getElementById("main").getElementsByClass("job-primary");

        //遍历所有的招聘信息
        for (int i = 0; i < jobPrimaries.size(); i++) {
            //第i条招聘信息
            Element job = jobPrimaries.get(i);
            //招聘概要信息
            WorkInfo workInfo = new WorkInfo();
            String jobName = job.getElementsByClass("job-name").get(0).text();
            String jobArea = job.getElementsByClass("job-area-wrapper").get(0).text();
            String jobPubTime = job.getElementsByClass("job-pub-time").get(0).text();
            String educationWork = job.getElementsByClass("job-limit").get(0).getElementsByTag("p").get(0).outerHtml();
            String education = educationWork.substring(educationWork.lastIndexOf("</em>")+5,educationWork.indexOf("</p>"));
            String jobLimit = job.getElementsByClass("job-limit").get(0).getElementsByTag("p").get(0).text().replace(education,"");
            String url = "https://www.zhipin.com"+job.getElementsByClass("primary-box").get(0).attr("href");
            List<String> tagList = new LinkedList<>();
            Elements tags = job.getElementsByClass("tag-item");
            for (Element tag : tags) {
                tagList.add(tag.text());
            }

            //公司信息
            Company company = new Company();
            String companyName = job.getElementsByClass("company-text").get(0).getElementsByTag("h3").text();
            String companyType = job.getElementsByClass("company-text").get(0).getElementsByTag("p").get(0).getElementsByTag("a").text();
            String benefits = job.getElementsByClass("info-desc").get(0).text();

            company.setName(companyName);
            company.setType(companyType);
            company.setBenefits(benefits);

            workInfo.setJobName(jobName);
            workInfo.setJobArea(jobArea);
            workInfo.setJobPubTime(jobPubTime);
            workInfo.setEducation(education);
            workInfo.setJobLimit(jobLimit);
            workInfo.setTags(tagList);
            workInfo.setDetailUrl(url);
            workInfo.setCompany(company);

            all.add(workInfo);
        }
        return null;
    }
}
