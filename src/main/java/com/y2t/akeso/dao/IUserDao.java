package com.y2t.akeso.dao;

import com.y2t.akeso.entity.User;
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
    @Insert("INSERT INTO user (user_name,user_id,phone,status) " +
            "values (#{userName},#{userId},#{phone},#{status})")
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


    @Select("select * from user where user_id = #{userId}")
    @Results({
            @Result(property="userId",column="user_id"),
            @Result(property="username",column="username"),
            @Result(property="phone",column="phone"),
            @Result(property="status",column="status"),
            @Result(property="createTime",column="create_time"),
            @Result(property="updateTime",column="update_time"),
    })
    User selectByUserId(String userId);
}
