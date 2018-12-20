package com.findai.xkk.ai_interviewer.domain;

import java.io.Serializable;

public class EducationExperience implements Serializable {

    String university = "";
    String major = "";
    String degree = "";
    String edudescription = "";
    String edu_year_start = "";
    String edu_year_end = "";

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getEdudescription() {
        return edudescription;
    }

    public void setEdudescription(String edudescription) {
        this.edudescription = edudescription;
    }

    public String getEdu_year_start() {
        return edu_year_start;
    }

    public void setEdu_year_start(String edu_year_start) {
        this.edu_year_start = edu_year_start;
    }

    public String getEdu_year_end() {
        return edu_year_end;
    }

    public void setEdu_year_end(String edu_year_end) {
        this.edu_year_end = edu_year_end;
    }
}
