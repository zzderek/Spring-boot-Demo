package com.y2t.akeso.service.impl;

import com.y2t.akeso.dao.IMessageDao;
import com.y2t.akeso.pojo.Message;
import com.y2t.akeso.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZiTung
 */
@Service
public class MessageServiceImpl implements IMessageService {
    private  static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private IMessageDao messageDao;

    @Override
    public Boolean addMessage(Message message) {
        Boolean result = false;
        if (messageDao.addMessage(message)>0){
            logger.info("新增短信记录成功:"+message.toString());
            result = true;
        }else{
            logger.info("新增短信失败!");
        }
        return result;
    }

    @Override
    public Message getMessageByPhone(String telephone) {
        Message message = messageDao.selectByPhone(telephone);
        if(message == null ){
            logger.info("根据手机号码："+telephone+"找到的短信记录为空");
        }else{
            logger.info("根据手机号码："+telephone+"找到的短信记录为:"+message.toString());
        }

        return message;
    }

    @Override
    public Boolean updateMessage(Message message) {
        Boolean result = false;
        if(messageDao.updateMessage(message)>0){
            logger.info("短信记录更新成功！");
            result = true;
        }else{
            logger.info("短信记录更新失败！");
        }

        return result;
    }
}
