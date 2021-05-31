package com.y2t.akeso.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

/**
 * @author ZiTung
 * @description: Id生产器
 * @date 2020/5/9 16:53
 */
public class IDUtils {
    private static int DEFAULT_PREFIX_LENGTH = 5;
    private static int DEFAULT_SUFFIX_LENGTH = 5;
    /**
     * 生成验证码
     * @param length
     * @return
     */
    public static String getValidCode(int length) {
        String SYMBOLS = "0123456789"; // 数字
        StringBuilder sb=new StringBuilder(length);
        for(int i=0;i<length;i++)
        {
            char ch=SYMBOLS.charAt(new Random().nextInt(SYMBOLS.length()));
            sb.append(ch);
        }
        return sb.toString();
    }
    /**
     * 返回ID
     * @return
     */
    public  static String  getID() {
        return getID(DEFAULT_PREFIX_LENGTH)+ getTimestamp(LocalDateTime.now())+getID(DEFAULT_SUFFIX_LENGTH);
    }

    public static String  getID(int length) {
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,length);
    }

    /**
     * 根据LocalDateTime得到形如'20200807121223'的14位字符串
     * @param localDateTime
     * @return
     */
    private static String getTimestamp(LocalDateTime localDateTime){
        String times = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return times.replaceAll("-","").replaceAll(":","").replaceAll(" ","");
    }


}
