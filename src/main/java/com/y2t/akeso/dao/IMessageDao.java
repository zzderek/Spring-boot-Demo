package com.y2t.akeso.dao;


import com.y2t.akeso.common.sql.SimpleInsertLangDriver;
import com.y2t.akeso.common.sql.SimpleUpdateLangDriver;
import com.y2t.akeso.pojo.Message;
import org.apache.ibatis.annotations.*;

/**
 * @author ZiTung
 * @date 2020/4/2 14:54
 */
@Mapper
public interface IMessageDao {

    /**
     * 新增短信记录
     *
     * @param message
     * @return
     */
    @Insert("INSERT INTO message (#{message})")
    @Lang(SimpleInsertLangDriver.class)
    public int addMessage(Message message);

    /**
     * 根据手机号码查询短信
     * @param telephone 手机号码
     * @return
     */
    @Select("select * from message where phone = #{phone}")
    @Results({
            @Result(property="userId",column="user_id"),
            @Result(property="phone",column="phone"),
            @Result(property="validCode",column="valid_code"),
            @Result(property="sendTime",column="send_time"),
            @Result(property="expiredTime",column="expired_time"),
    })
    public Message selectByPhone(@Param("phone") String telephone);

    /**
     * 更新短信
     *
     * @param message
     * @return
     */
    @Insert("update message (#{message}) WHERE phone = #{phone} ")
    @Lang(SimpleUpdateLangDriver.class)
    public int updateMessage(Message message);
}
