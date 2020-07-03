package com.y2t.akeso.dao;

import com.y2t.akeso.common.sql.SimpleInsertLangDriver;
import com.y2t.akeso.pojo.Message;
import com.y2t.akeso.pojo.po.UserLottery;
import org.apache.ibatis.annotations.*;

/**
 * @author ZiTung
 * @description: 红包Dao
 * @date 2020/7/2 16:22
 */
@Mapper
public interface ILotteryDao {

    @Select("SELECT amount_now FROM lottery_pool WHERE id = 1")
    int getAmple();

    @Insert("insert into user_lottery (#{userLottery})")
    @Lang(SimpleInsertLangDriver.class)
    int increaseForUser(UserLottery userLottery);

    @Update("UPDATE lottery_pool SET amount_now = amount_now - #{amount} WHERE id = 1 ")
    int decrease(@Param("amount") int amount);
}
