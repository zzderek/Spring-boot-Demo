package com.y2t.akeso.pojo;

/**
 * @author ZiTung
 * @description: 基类：用户
 * @date 2020/4/29 14:59
 */
class User {
    /**
     * 用户名
     */
    private String username;
    /**
     * 账号
     */
    private Account account;
    /**
     * 提醒
     */
    private Remind remind;
    /**
     * 反馈
     */
    private Feedback feedback;
    //private List<Follower> followerList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Remind getRemind() {
        return remind;
    }

    public void setRemind(Remind remind) {
        this.remind = remind;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}
