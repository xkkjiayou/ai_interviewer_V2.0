package com.findai.xkk.ai_interviewer.domain;

import java.util.ArrayList;
import java.util.List;

public class ApplicationRecordWrapper {
    List<ApplicationRecord> application_record_list;
    String status = "error";
    public ApplicationRecordWrapper(){
        application_record_list = new ArrayList<>();
    }
    public List<ApplicationRecord> getApplication_record_list() {
        return application_record_list;
    }

    public void setApplication_record_list(List<ApplicationRecord> application_record_list) {
        this.application_record_list = application_record_list;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
