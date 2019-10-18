package com.qfmy.inkman_computer;

import com.qfmy.inkman_computer.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InkManComputerApplicationTests{


    @Resource
    UserDao dao;

    @Test
    public void contextLoads() {
        System.out.println(dao.selectList(null).get(0).getUsername());

    }

    @Test
    public void Test()
    {
        int[] arr = {1,4,7,3,8,9,2,6,5};
        //System.out.println(Arrays.toString(insertSort(arr)));
//        for (int i=0;i<arr.length;i++)
//        {
//            new sleepSort(arr[i]).start();
//        }
        QSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));

//        for (int i=0;i<arr.length;i++)
//        {
//            for (int j=i+1;j<arr.length;j++)
//            {
//                if (arr[i]>arr[j])
//                {
//                    swap(arr,i,j);
//                }
//            }
//        }
//        System.out.println(Arrays.toString(arr));
    }

    private int[] insertSort(int[] a)
    {
        int temp=0;
        for (int i=1;i<a.length;i++)
        {
            if(a[i]<a[i-1])
            {
                temp=a[i];
                int j=i-1;
                for (;(j>=0)&&(a[j]>temp);j--)
                {
                    a[j+1]=a[j];
                }
                a[j+1]=temp;
            }
        }
        return a;
    }

    private void QSort(int[] a,int low,int hight)
    {
        if(low<hight)
        {
            int index=getIndex(a,low,hight);
            QSort(a,low,index);
            QSort(a,index+1,hight);
        }
    }

    private int getIndex(int[] a,int low,int hight)
    {
        int mid=(low+hight)/2;
        if(a[mid]>a[hight]) swap(a,mid,hight);
        if(a[low]<a[mid]) swap(a,low,mid);
        if(a[mid]>a[hight]) swap(a,mid,hight);


        int temp=a[low];

        while (low<hight)
        {
            if(a[hight]>temp && low<hight)
                hight--;
            a[low]=a[hight];

            if(a[low]<temp && low<hight)
                low++;
            a[hight]=a[low];

        }
        a[low]=temp;
        return low;
    }

    private void swap(int[] a,int x,int y)
    {
        int t=a[x];
        a[x]=a[y];
        a[y]=t;
    }


}
class sleepSort extends Thread
{
    int x=0;
    public sleepSort(int x)
    {
        this.x=x;
    }
    public void run()
    {
        try {
            sleep(x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(x);
    }
}