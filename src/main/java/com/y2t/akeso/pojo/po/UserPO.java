package com.y2t.akeso.pojo.po;

import lombok.Builder;
import lombok.Data;
import org.apache.ibatis.annotations.Result;

import java.time.LocalDateTime;

/**
 * @author ZiTung
 * @description: TODO
 * @date 2020/7/2 16:13
 */
@Data
@Builder
public class UserPO {
    private String userId;
    private String username;
    private String phone;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
