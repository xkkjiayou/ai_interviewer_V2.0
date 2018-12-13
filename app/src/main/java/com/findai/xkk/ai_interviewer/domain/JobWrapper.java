package com.findai.xkk.ai_interviewer.domain;

public class JobWrapper {
    Job job_details;
    String status;

    public Job getJob_details() {
        return job_details;
    }

    public void setJob_details(Job job_details) {
        this.job_details = job_details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
