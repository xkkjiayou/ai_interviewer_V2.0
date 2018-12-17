package com.findai.xkk.ai_interviewer.domain;

public class Application {
    boolean deliver_resume;
    boolean have_resume;
    boolean is_interview;
    boolean need_interview;
    int iid;

    public boolean isDeliver_resume() {
        return deliver_resume;
    }

    public void setDeliver_resume(boolean deliver_resume) {
        this.deliver_resume = deliver_resume;
    }

    public boolean isHave_resume() {
        return have_resume;
    }

    public void setHave_resume(boolean have_resume) {
        this.have_resume = have_resume;
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

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }
}
