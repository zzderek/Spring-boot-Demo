package com.y2t.akeso.pojo.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ZiTung
 * @description: TODO
 * @date 2020/7/2 14:25
 */
@Data
public class MessagePO {

    private  long  id;
    private String phone;
    private String validCode;
    private LocalDateTime sendTime;
    private LocalDateTime expiredTime;


}
