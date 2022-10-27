package org.lanqiao.dto;

public class Score {
    // 技能点 ID
    private Integer skill_point_id;
    // 是否通过
    private boolean passed;
    // 用户得分
    private Integer user_score;
    // 技能点总分
    private Integer skill_score;
    // 通过时间，时间戳，单位：s
    private Long push_at;

    public Integer getSkill_point_id() {
        return skill_point_id;
    }

    public void setSkill_point_id(Integer skill_point_id) {
        this.skill_point_id = skill_point_id;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public Integer getUser_score() {
        return user_score;
    }

    public void setUser_score(Integer user_score) {
        this.user_score = user_score;
    }

    public Integer getSkill_score() {
        return skill_score;
    }

    public void setSkill_score(Integer skill_score) {
        this.skill_score = skill_score;
    }

    public Long getPush_at() {
        return push_at;
    }

    public void setPush_at(Long push_at) {
        this.push_at = push_at;
    }

    @Override
    public String toString() {
        return "ScoreDto{" +
                "skill_point_id=" + skill_point_id +
                ", passed=" + passed +
                ", user_score=" + user_score +
                ", skill_score=" + skill_score +
                ", push_at=" + push_at +
                '}';
    }
}
