package com.findai.xkk.ai_interviewer.domain;

public class ApplicationRecord {
    String delivertDate;
    int iid;
    boolean is_interview;
    boolean need_interview;
    int uid;
    int jobid;


    public String getDelivertDate() {
        return delivertDate;
    }

    public void setDelivertDate(String delivertDate) {
        this.delivertDate = delivertDate;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public boolean isIs_interview() {
        return is_interview;
    }

    public void setIs_interview(boolean is_interview) {
        this.is_interview = is_interview;
    }

    public boolean isNeed_interview() {
        return need_interview;
    }

    public void setNeed_interview(boolean need_interview) {
        this.need_interview = need_interview;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getJobid() {
        return jobid;
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }
}
