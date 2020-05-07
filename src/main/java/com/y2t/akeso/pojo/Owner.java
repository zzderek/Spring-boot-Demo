package com.y2t.akeso.pojo;

import java.util.List;

/**
 * @author ZiTung
 * @description: 用户本人
 * @date 2020/4/29 15:56
 */
public class Owner extends User {
    private List<Following> followingList; //我关注的人
    //private List<Follower> followerList; //关注我的人


    public List<Following> getFollowingList() {
        return followingList;
    }

    public void setFollowingList(List<Following> followingList) {
        this.followingList = followingList;
    }
}
