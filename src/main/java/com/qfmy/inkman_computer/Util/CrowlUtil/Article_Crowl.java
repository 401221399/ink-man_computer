package com.qfmy.inkman_computer.Util.CrowlUtil;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dongliu.requests.RawResponse;
import net.dongliu.requests.Requests;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Article_Crowl {
    String search_url;//查询公众号链接
    String appmsg_url;//获取文章链接
    WebDriver driver;//浏览器
    WeChatLogin WL;//微信登录器
    String token;
    Map<String,String> cookiesMap;//cookies
    Map<String, Object> headers;//报头

    public Article_Crowl() throws InterruptedException {
        this.search_url="https://mp.weixin.qq.com/cgi-bin/searchbiz?";
        this.appmsg_url="https://mp.weixin.qq.com/cgi-bin/appmsg?";
        this.WL=new WeChatLogin();
        System.setProperty("webdriver.chrome.driver", "D:\\工作站\\SpringBoot\\ink-man_computer\\chromedriver.exe");// chromedriver服务地址
//        ChromeOptions ChromeOptions=new ChromeOptions();
//        ChromeOptions.addArguments("-headless");//设置为不打开浏览器模式
        this.driver = new ChromeDriver(); // 新建一个WebDriver 的对象，但是new 的是谷歌的驱动
        this.cookiesMap=readCookies(WL.getCookiesURL());
        addCookiesByMap(this.cookiesMap,driver);
        driver.get("https://mp.weixin.qq.com");
        if (driver.getCurrentUrl().indexOf("token=")>0)
        {
            this.token = driver.getCurrentUrl().substring(driver.getCurrentUrl().indexOf("token=")+6,driver.getCurrentUrl().length());
        }
        else{
            WL.Login();
            this.cookiesMap=readCookies(WL.getCookiesURL());
            addCookiesByMap(this.cookiesMap,driver);
            driver.get("https://mp.weixin.qq.com");
            this.token = driver.getCurrentUrl().substring(driver.getCurrentUrl().indexOf("token=")+6,driver.getCurrentUrl().length());
        }

        this.headers = new HashMap<>();
        headers.put("HOST", "mp.weixin.qq.com");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
    }

    //把时间戳转日期字符串
    public String stampToDate(String s){
        Long lt=new Long(s);
        String date = new java.text.SimpleDateFormat("yyyy/MM/dd/").format(new java.util.Date(lt * 1000));
        return date;
    }

    //获取文本文件的内容为cookies
    Map<String,String> readCookies(String dir)
    {
        String json="";
        try {
            FileReader fd = new FileReader(dir);
            BufferedReader bf = new BufferedReader(fd);
            String temp=bf.readLine();
            while (temp!= null) {
                json+=temp;
                temp=bf.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,String> map=(Map<String,String>)JSONUtils.parse(json);
        return map;
    }

    //给浏览器添加cookies
    void addCookiesByMap(Map<String,String> map,WebDriver driver)
    {
        driver.get("https://mp.weixin.qq.com"); // 加入cookies前先打开页面不然会报错
        driver.manage().deleteAllCookies();
        for (String key :map.keySet())
        {
            Cookie c = new Cookie(key,map.get(key));
            driver.manage().addCookie(c);
        }
    }

    String getFakeIdByName(String name)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("action", "search_biz");
        params.put("token", this.token);
        params.put("lang", "zh_CN");
        params.put("f", "json");
        params.put("ajax", "1");
        params.put("random", Math.random());
        params.put("query", name);
        params.put("begin", "0");
        params.put("count", "5");

        RawResponse resp = Requests.get(this.search_url).cookies(this.cookiesMap).headers(this.headers).params(params).send();
        String requestBody=resp.readToText();
        String fid=requestBody.substring(requestBody.indexOf("fakeid")+9,requestBody.indexOf("nickname")-3);
        return fid;
    }

    //根据公众号名爬取该公众号的文章
    public void ArticlCrowl(String name) throws IOException, InterruptedException {
        String fid=getFakeIdByName(name);//获取公众号id

        Map<String, Object> query_id_data = new HashMap<>();//设置请求参数
        query_id_data.put("action", "list_ex");
        query_id_data.put("token", token);//token
        query_id_data.put("lang", "zh_CN");
        query_id_data.put("f", "json");
        query_id_data.put("ajax", "1");
        query_id_data.put("random", Math.random());//随机数
        query_id_data.put("query", "");
        query_id_data.put("begin", "0");//从第几条开始
        query_id_data.put("count", "5");//显示多少条
        query_id_data.put("fakeid",fid);//公众号id
        query_id_data.put("type", "9");

        RawResponse appmsg_response = Requests.get(appmsg_url).cookies(cookiesMap).headers(headers).params(query_id_data).send();
        String ArticleBody = appmsg_response.readToText();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode BodyNode = objectMapper.readTree(ArticleBody);

        int ArticleCount=BodyNode.get("app_msg_cnt").asInt();
        for (int i=0;i<=ArticleCount/5;i++)
        {
            query_id_data.put("random", Math.random());//随机数
            query_id_data.put("begin", i*5);//从第几条开始
            appmsg_response = Requests.get(appmsg_url).cookies(cookiesMap).headers(headers).params(query_id_data).send();
            ArticleBody = appmsg_response.readToText();
            objectMapper = new ObjectMapper();
            BodyNode = objectMapper.readTree(ArticleBody);
            BodyNode=BodyNode.get("app_msg_list");
            for (int j=0;j<BodyNode.size();j++)
            {
                System.out.println(BodyNode.get(j).get("title").asText());
                System.out.println(BodyNode.get(j).get("link").asText());
                System.out.println(stampToDate(BodyNode.get(j).get("update_time").asText()));
                System.out.println(BodyNode.get(j).get("cover").asText());
                System.out.println(BodyNode.get(j).get("itemidx").asText());
                System.out.println("--------------------------------------------");
            }
            Thread.sleep(2000);
        }
        //driver.close();
        //driver.quit();
    }
}

