package com.qfmy.inkman_computer.Util.CrowlUtil;

import javax.validation.constraints.Max;

//最长子串算法
public class BigSubString {
    //求最长子串
    public static String getMaxSubString(String str1,String str2){
        //定义两个变量，用来存储待判断的字符串
        str1=str1.replace(" ","");
        str2=str2.replace(" ","");
        String max="";
        String min="";
        //一些基础的判断
        if(str1==null||str2==null||str1.isEmpty()||str2.isEmpty()){
            return null;
        }
        if(str1.length()>=str2.length()){
            max=str1;
            min=str2;
        }else{
            max=str2;
            min=str1;
        }
        //对长度较短的字符串依次取其子串（n,n-1,n-2......1）
        for(int i=0;i<min.length();i++){
            //每次子串长度减一，判断大串是否包含子串
            for(int beg=0,end=min.length()-i;end<=min.length();beg++,end++){
                //取子串
                String s=min.substring(beg,end);
                //如果大串中含有子串，则返回子串，该子串就是最大的公共子串
                //这里也可以使用java中String类提供的contains()方法，这里选择自己写一个类似的方法
                if(containString(max,s)){
                    return s;
                }
            }
        }
        //否则返回null，表示不存在公共子串
        return null;
    }

    //计算出最短串是否是最长串的子串
    public  static boolean isMaxSubString(String str1,String str2) {
        str1=str1.replace(" ","");
        str2=str2.replace(" ","");
        String max="";
        String min="";
        if(str1.length()>=str2.length()){
            max=str1;
            min=str2;
        }else{
            max=str2;
            min=str1;
        }
        String MaxSubString = getMaxSubString(str1,str2);
        return min.equals(MaxSubString);
    }

    //判断一个字符串是否包含另一个字符串
    public static boolean containString(String str1,String str2){
        //定义两个变量，用来存储两个子串
        String min="";
        String max="";
        if(str1==null||str2==null){
            return false;
        }
        if(str1.length()>=str2.length()){
            max=str1;
            min=str2;
        }else{
            max=str2;
            min=str1;
        }
        //定义一个变量，用来存储判断过程中出现的字符串
        String s="";
        //循环判断两个字符串
        for(int i=0;i<max.length();i++){
            //定义并保存一个变量i的副本
            int index=i;
            for(int j=0;j<min.length();j++){
                //如果两个字符相等，保存这个字符，并将index+1
                if(max.charAt(index)==min.charAt(j)){
                    s+=min.charAt(j)+"";
                    index++;
                    if (index>=max.length())
                    {
                        break;
                    }
                }else{//否则的话重置所有中间变量，并退出内层循环，使得i++
                    s="";
                    j=0;
                    index=i;
                    break;
                }

            }
            //每次内层循环结束后，判断s是否等于min，如果相等则返回true，否则继续循环
            if(s.equals(min)){
                return true;
            }
        }
        return false;
    }
}
