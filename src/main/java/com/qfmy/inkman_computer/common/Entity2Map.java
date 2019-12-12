package com.qfmy.inkman_computer.common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Entity2Map {

    public static Map<String,Object> object2Map(Object object){
        Map<String,Object> result=new HashMap<>();
        //获得类的的属性名 数组
        Field[]fields=object.getClass().getDeclaredFields();
        try {


            for (Field field : fields) {
                field.setAccessible(true);
                String name = new String(field.getName());
                result.put(name, field.get(object));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String,Object> object2Map(Map<String,Object> result,Object object){
        Field[]fields=object.getClass().getDeclaredFields();
        try {


            for (Field field : fields) {
                field.setAccessible(true);
                String name = new String(field.getName());
                result.put(name, field.get(object));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
