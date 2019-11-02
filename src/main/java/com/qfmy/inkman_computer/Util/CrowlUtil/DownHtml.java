package com.qfmy.inkman_computer.Util.CrowlUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileWriter;
import java.io.IOException;

public class DownHtml {

    //获取页面
    public static Document getDocument(String url)
    {
        Document page=null;
        if(url.equals("") || url==null)
        {
            System.out.println("链接地址为空");
        }
        else
        {
            try
            {
                page = Jsoup.connect(url).get();
                System.out.println("已连接:"+url);
            } catch (IOException e1)
            {
                // TODO 自动生成的 catch 块
                e1.printStackTrace();
            }
        }
        return page;
    }

    public static String DownHtml(String URL)
    {
        Document page=getDocument(URL);

        String html=page.html();

        // js资源获取地址重新定位
        // js资源获取失败会导致本地html加载慢
        html=html.replace("res.wx.qq.com","http://res.wx.qq.com");
        html=html.replace("mp.weixin.qq.com", "http://mp.weixin.qq.com");
        //图片资源获取地址重新定位
        //微信的图片链接都是在data-src属性中,src属性的链接则是无效的链接，所以要把data-src的链接赋值给src
        html = html.replace("data-src", "src");

        return html;
//        FileWriter writer;
//        try {
//            writer = new FileWriter("D:\\工作站\\SpringBoot\\ink-man_computer\\this.html");
//            writer.write(html);
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
