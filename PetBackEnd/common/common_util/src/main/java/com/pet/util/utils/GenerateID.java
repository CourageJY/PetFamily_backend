package com.pet.util.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateID {

    //根据时间生成14位不重复的申请表ID
    static public String generateApplicationID(){
        //获得当前时间戳
        Date currentTime = new Date();
        //设置时间解析与格式化的形式
        //注意：yyyyMMddHHmmss里面不能有 - : . 等
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(currentTime);

    }
}
