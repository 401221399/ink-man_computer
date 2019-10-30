package com.qfmy.inkman_computer;

import com.qfmy.inkman_computer.Util.CrowlUtil.Article_Crowl;
import com.qfmy.inkman_computer.Util.CrowlUtil.Computer_Crowl;
import com.qfmy.inkman_computer.Util.CrowlUtil.DownHtml;
import com.qfmy.inkman_computer.Util.CrowlUtil.WeChatLogin;
import com.qfmy.inkman_computer.dao.UserDao;
import org.junit.Test;
import org.junit.runner.Computer;
import org.junit.runner.RunWith;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InkManComputerApplicationTests{
    @Test
    public void Test() throws IOException, InterruptedException {
        //new Article_Crowl().ArticlCrowl("笔吧评测室");
        String url="http://mp.weixin.qq.com/s?__biz=MzIxMTAyNjk0OA==&mid=2654594731&idx=1&sn=d3f178497cb410bcc56792089ce4f64a&chksm=8c96d3c0bbe15ad627a5b799b6433cb6ed237fe2a2518ca6ae87e2fb9e16ba9d068e8e98cd5b#rd";
        new DownHtml().DownHtml(url);
    }

}