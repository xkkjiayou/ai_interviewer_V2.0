package com.findai.xkk.ai_interviewer.domain;

import java.io.Serializable;
import java.util.List;

public class JobList implements Serializable {
    List<Job> jobList;
    String status;

    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
