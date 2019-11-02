package com.qfmy.inkman_computer.Util.CrowlUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.qfmy.inkman_computer.entity.Article;
import com.qfmy.inkman_computer.entity.item;
import com.qfmy.inkman_computer.service.itemService;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import com.qfmy.inkman_computer.dao.*;
import java.io.IOException;
import java.util.*;

public class Computer_Crowl {
    itemService itemService;
    ArticleDao ArticleDao;

    public void setArticleDao(ArticleDao articleDao) {
        ArticleDao = articleDao;
    }

    private String domain="";//爬取的网站域名
    private String[] CrowlList; //爬取类型集合
    private Map<String,String> CrowlMap;//类型和关键字索引字典
    public Computer_Crowl()
    {
        domain="https://product.pconline.com.cn/";

        this.CrowlMap=new HashMap<>();
        this.CrowlMap.put("笔记本","notebook");
        this.CrowlMap.put("cpu","cpu");
        this.CrowlMap.put("显卡","vga");
        this.CrowlMap.put("内存","memory");
        this.CrowlMap.put("主板","mb");
        this.CrowlMap.put("硬盘","harddisk");
        this.CrowlMap.put("ssd","dianziyingpan");
        this.CrowlMap.put("电源","power");
        this.CrowlMap.put("散热","sanre");
        this. CrowlMap.put("机箱","case");

        this.CrowlList=new String[]{"notebook","cpu","vga","memory","mb","harddisk","dianziyingpan","power","sanre","case"};
    }

    public void setItemService(itemService itemService) {
        this.itemService = itemService;
    }

    //获取页面
    public Document getDocument(String url)
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



    //爬虫入口，遍历需要爬取的设备类型、页号，分别爬取
    public void crowl()
    {
        try
        {
            for (String s:CrowlList)
            {
                String visiturl=domain+s+"/s5.shtml";
                String pagecount=getDocument(visiturl).select("div[id=Jpager] em i").text();
                for (int i=0;i<Integer.parseInt(pagecount);i++)
                {
                    if (i==0) Main_Page_Crowl(visiturl,s);
                    else {
                        visiturl=domain+s+"/"+(i-1)*25+"s5.shtml";
                        Main_Page_Crowl(visiturl,s);
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }



    //爬取页面信息，爬起当前页面的每个商品
    public void Main_Page_Crowl(String url,String classname)
    {
        Document ComputerPage=getDocument(url);

        Elements itemList = ComputerPage.select("ul[id=JlistItems] li[class=item]");//获取页面下商品的集合
        for(int i=0;i<itemList.size();i++)
        {
            item item=getItem_MainParameter(itemList.get(i));//解析商品为一个类
            //获取商品地址
            String item_url="https:"+itemList.get(i).select("a[class=item-title-name]").get(0).attr("href");
            item=Item_Page_Crowl(item_url,item);
            item=Item_Detail_Page_Crowl(item_url,item);
            item.setClassname(classname);
            itemService.addItem(item);
            break;
        }
    }


    public item getItem_MainParameter(Element element)
    {
        item i=new item();
        //爬取商品名，商品详细信息地址，商品价格，商品简介
        i.setName(element.select("a[class=item-title-name]").get(0).html());
        if (element.select("div[class=price price-now] a").size()<=0)
        {
            i.setPrice("￥0");
        }
        else i.setPrice(element.select("div[class=price price-now] a").get(0).html());
        i.setProfile(element.select("span[class=item-title-des]").get(0).html());
        return i;
    }

    //爬取商品详细页面
    public item Item_Page_Crowl(String url,item item)
    {
        Document ItemPage=getDocument(url);

        //爬取图片
        Elements ImgList = ItemPage.select("div[id=JareaTop] div[class=smallpics] ul li");//获取商品属性下的图片集合
        List<String> ImgUrlList=new ArrayList<>();
        for(int j=0;j<ImgList.size();j++)
        {
            String imgurl="https:"+ImgList.get(j).select("a img").attr("src").replace("_120x90","");
            ImgUrlList.add(imgurl);
        }
        JSONArray jsonArray=new JSONArray(ImgUrlList);
        item.setImglist(jsonArray.toString());

        item.setDivbox(getDiv(ItemPage.select("div[id=JareaTop] div[class=product-detail-main]")));

        item.setArticelList(getArticle(ItemPage.select("div[id=Jpingce]"),item.getName()));

        return item;
    }

    //形参是一个div[id=Jpingce] 的元素
    //获取评测信息
    public List<Article> getArticle(Elements e,String PCname)
    {
        List<Article> ArticleList=new ArrayList<>();
        Elements li_list=e.select("ul[class=m-pingce] li");
        for(int i=0;i<li_list.size();i++)
        {
            Article a=new Article();
            a.setTitle(li_list.get(i).select("dl dt").html());
            a.setData(li_list.get(i).select("span[class=time]").html().replace("<ins></ins>",""));
            a.setPc(PCname);
            a.setProfile(li_list.get(i).select("dl dd[class=u-summary]").html());
            a.setUrl("https:"+li_list.get(i).select("a[class=blk-img]").attr("href"));
            a.setImgurl("https:"+li_list.get(i).select("a[class=blk-img] img").attr("src"));
            a.setForm("太平洋:https://product.pconline.com.cn");
            ArticleDao.add(a);
            ArticleList.add(a);
        }
        return ArticleList;
    }

    public String getDiv(Elements e)
    {
        Elements divElement=e.select("div[class^=comment-main]");//以comment-main开头的类
        if(divElement.html().equals(""))
        {
            divElement=e.select("dl[class^=baseParam]");
            return "<dl class=\"baseParam clearfix\">"+divElement.html()+"</dl>";
        }
        else
        {
            return "<div class=\"chart-box\">"+divElement.select("div[class=chart-box]").html()+"</div>";
        }
    }


    /*
    * 生成的属性json格式为[
    *   {属性类型：{[
    *       {属性名：属性}，{属性名：属性}，{属性名：属性}
    *       ]}，
    * {属性类型：{[
     *       {属性名：属性}，{属性名：属性}，{属性名：属性}
     *       ]}，
    * ]
    *
    * */
    public item Item_Detail_Page_Crowl(String url,item item)
    {
        Document Item_Detail_Page=getDocument(url.replace(".html","_detail.html"));
        //获取大表单
        Elements TableList = Item_Detail_Page.select("div[id=area-detailparams] div[class=bd] table[class=dtparams-table]");

        List<Map> ParameterList=new ArrayList<>();//最外层的list

        //解析表单合成Map
        for (int i=0;i<TableList.size();i++)
        {
            Map<String,List> MainMap=new HashMap<>();//外层Map
            List<Map<String,String>> MainList=new ArrayList<>();//外层List

            String title=TableList.get(i).select("thead tr th i").html();//标题
            //遍历属性
            Elements TrList=TableList.get(i).select("tbody tr");
            Map<String,String> itemMap=new HashMap<>();//子层Map
            for(int j=0;j<TrList.size();j++)
            {
                String key=TrList.get(j).select("th").html();//key
                String vulue="";//vulue
                Elements vulueElements=TrList.get(j).select("td a[class=poptxt]");//属性值有链接和非链接
                if (vulueElements.size()==0) vulueElements=TrList.get(j).select("td");
                for (int k=0;k<vulueElements.size();k++)//属性值有多个值，遍历组成字符串
                {
                    if(vulue.indexOf(vulueElements.html())<0)
                    {
                        if (k==0)
                            vulue=vulue+vulueElements.get(k).html();
                        else
                            vulue=vulue+"、"+vulueElements.get(k).html();
                    }

                }
                itemMap.put(key,vulue);
                MainList.add(itemMap);
            }

            MainMap.put(title,MainList);
            ParameterList.add(MainMap);
        }
        JSONArray jsonObj=new JSONArray(ParameterList);
        item.setParameter(jsonObj.toString());
        List list = (List) JSONUtils.parse(item.getParameter());
        return item;
    }
}
