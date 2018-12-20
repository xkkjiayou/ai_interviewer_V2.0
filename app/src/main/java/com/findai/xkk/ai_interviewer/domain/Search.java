package com.findai.xkk.ai_interviewer.domain;

import java.io.Serializable;
import java.util.List;

public class Search implements Serializable {
    String[] jobidlist;

    public String[] getJobidlist() {
        return jobidlist;
    }

    public void setJobidlist(String[] jobidlist) {
        this.jobidlist = jobidlist;
    }
}
