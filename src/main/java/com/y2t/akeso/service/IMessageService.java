package com.y2t.akeso.service;


import com.y2t.akeso.pojo.Message;

/**
 * @author ZiTung
 * @date 2020/4/2 14:54
 */
public interface IMessageService {


    /**
     * 新增一条短信发送记录
     * @param message 短信对象
     * @return true：添加成功 false：添加失败
     */
    Boolean addMessage(Message message);

    /**
     * 根据手机号码查询短信记录
     * @param telephone 手机号码
     * @return 短信记录
     */
    Message getMessageByPhone(String telephone);

    /**
     * 根据手机号码更新短信记录
     * @param message 新的短信对象
     * @return true：添加成功 false：添加失败
     */
    Boolean updateMessage(Message message);
}
