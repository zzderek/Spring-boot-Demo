package com.y2t.akeso.cqe;

import com.y2t.akeso.annotation.MobileNumber;

import javax.validation.constraints.NotBlank;

/**
 * @author Root
 */
public class SendMessageCommand {

    @NotBlank(message = "手机号不能为空")
    @MobileNumber
    private String phoneNumber;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
