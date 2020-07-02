package com.y2t.akeso.dao;

import com.y2t.akeso.common.sql.SimpleInsertLangDriver;
import com.y2t.akeso.pojo.User;
import org.apache.ibatis.annotations.*;

/**
 * @author ZiTung
 * @description: UserDao
 * @date 2020/5/11 11:11
 */
@Mapper
public interface IUserDao {

    /**
     * 新增用户
     *
     * @param userPO
     * @return
     */
    @Insert("INSERT INTO user (#{userPO})")
    @Lang(SimpleInsertLangDriver.class)
    int addUser(User userPO);

    /**
     * 根据手机号码查询短信
     * @param telephone 手机号码
     * @return
     */
    @Select("select * from user where phone = #{phone}")
    @Results({
            @Result(property="userId",column="user_id"),
            @Result(property="username",column="username"),
            @Result(property="phone",column="phone"),
            @Result(property="status",column="status"),
            @Result(property="createTime",column="create_time"),
            @Result(property="updateTime",column="update_time"),
    })
    User selectByPhone(@Param("phone") String telephone);

}
