package com.y2t.akeso.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author ZiTung
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String phone;
    private  String validCode;
    private LocalDateTime sendTime;
    private LocalDateTime expiredTime;
}
