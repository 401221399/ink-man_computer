package com.qfmy.inkman_computer;

import com.qfmy.inkman_computer.Util.CrowlUtil.Computer_Crowl;
import com.qfmy.inkman_computer.dao.UserDao;
import org.junit.Test;
import org.junit.runner.Computer;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InkManComputerApplicationTests{
    @Test
    public void Test()
    {
        Computer_Crowl crowl=new Computer_Crowl();
        crowl.crowl();
    }

}