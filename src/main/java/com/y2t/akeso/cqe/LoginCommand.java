package com.y2t.akeso.cqe;

import com.y2t.akeso.annotation.MobileNumber;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 验证登陆接口参数
 */
public class LoginCommand {


    @NotBlank(message = "手机号不能为空")
    @MobileNumber
    private String phoneNumber;


    @NotBlank(message = "验证码不能为空")
    @Length(max = 4,min = 4,message = "验证码为4位")
    private String vcode;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

}
