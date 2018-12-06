package com.findai.xkk.ai_interviewer.resume_fragment;

import com.findai.xkk.ai_interviewer.domain.EducationExperience;
import com.findai.xkk.ai_interviewer.domain.WorkExperience;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface ResumeInterface {
    public String resume_evalution_interface_impl(String str);
    public String resume_basicinfo_interface_impl(String[] str);
    public String resume_workexperience_interface_impl(List<WorkExperience> list);
    public String resume_eduexperience_interface_impl(List<EducationExperience> list);
}
