package com.y2t.akeso.pojo.po;

import com.y2t.akeso.common.sql.Invisible;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ZiTung
 * @description: 用户领取红包记录
 * @date 2020/7/2 16:18
 */
@Data
@Builder
public class UserLottery {

    private int id;
    private String userId;
    private  String PoolName;
    private int amount;
    private LocalDateTime time;
}
