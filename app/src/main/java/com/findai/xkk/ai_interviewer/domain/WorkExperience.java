package com.findai.xkk.ai_interviewer.domain;

import java.io.Serializable;

public class WorkExperience implements Serializable {


    String companyname = "";
    String jobname = "";
    String workyear_start = "";
    String jobdescription = "";
    String workyear_end = "";


    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getWorkyear_start() {
        return workyear_start;
    }

    public void setWorkyear_start(String workyear_start) {
        this.workyear_start = workyear_start;
    }

    public String getWorkyear_end() {
        return workyear_end;
    }

    public void setWorkyear_end(String workyear_end) {
        this.workyear_end = workyear_end;
    }

    public String getJobdescription() {
        return jobdescription;
    }

    public void setJobdescription(String jobdescription) {
        this.jobdescription = jobdescription;
    }
}
