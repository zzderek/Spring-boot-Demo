package com.y2t.akeso.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author ZiTung
 * @description: TODO
 * @date 2020/7/2 14:03
 */
@Data
@ApiModel(description="登陆入参")
public class MessageVO {

    private String phone;
    private String validCode;
}
