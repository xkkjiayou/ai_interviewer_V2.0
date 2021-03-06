package com.findai.xkk.ai_interviewer.domain;

import java.io.Serializable;
import java.util.List;

public class QuestionList implements Serializable {
    List<Question> questionList;
    String imgdata;
    int uid;
    String reportid;

    public int getUserid() {
        return uid;
    }

    public void setUserid(int userid) {
        this.uid = userid;
    }

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public String getImgdata() {
        return imgdata;
    }

    public void setImgdata(String imgdata) {
        this.imgdata = imgdata;
    }
}
