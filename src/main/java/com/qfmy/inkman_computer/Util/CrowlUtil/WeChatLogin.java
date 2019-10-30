package com.qfmy.inkman_computer.Util.CrowlUtil;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WeChatLogin {
    private String FormURL;
    private String Username;
    private String Password;
    private String imgURL;
    private String cookiesURL;
    public WeChatLogin()
    {
        this.FormURL="https://mp.weixin.qq.com/";
        this.Username="870436415@qq.com";
        this.Password="1998417feng=";
        this.imgURL="D:\\工作站\\SpringBoot\\ink-man_computer\\qrccodeimg.png";
        this.cookiesURL="D:\\工作站\\SpringBoot\\ink-man_computer\\cookies.txt";
    }

    public void Login() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\工作站\\SpringBoot\\ink-man_computer\\chromedriver.exe");// chromedriver服务地址
        String NowURL=this.FormURL;
        ChromeOptions ChromeOptions=new ChromeOptions();
        ChromeOptions.addArguments("-headless");//设置为不打开浏览器模式
        WebDriver driver = new ChromeDriver(ChromeOptions); // 新建一个WebDriver 的对象，但是new 的是谷歌的驱动
        String url = FormURL;
        driver.get(url); // 打开指定的网站
        System.out.println("----开始模拟登陆");
        driver.findElement(By.name("account")).clear();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("account")).sendKeys(Username);
        driver.findElement(By.name("password")).sendKeys(Password);
        driver.findElement(By.className("btn_login")).click();

        while (driver.getCurrentUrl().equals(NowURL))
        {
            Thread.sleep(500);
        }
        NowURL=driver.getCurrentUrl();

        System.out.println("----账号密码输入成功");

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String savePath = imgURL;
            //复制内容到指定文件中
            FileUtils.copyFile(scrFile, new File(savePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("----二维码已保存，请扫码");

        while (driver.getCurrentUrl().equals(NowURL))
        {
            Thread.sleep(500);
        }
        NowURL=driver.getCurrentUrl();

        System.out.println("----登陆成功");
        Set<Cookie> cookieSet = driver.manage().getCookies();
        Map<String ,String > map=new HashMap<>();
        for (Cookie c:cookieSet)
        {
            map.put(c.getName(),c.getValue());
        }
        JSONObject json=new JSONObject(map);
        FileWriter writer;
        try {
            writer = new FileWriter(cookiesURL);
            writer.write(json.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver.close();
        driver.quit();
        return ;
    }

    public String getCookiesURL() {
        return cookiesURL;
    }

    public String getImgURL() {
        return imgURL;
    }
}
