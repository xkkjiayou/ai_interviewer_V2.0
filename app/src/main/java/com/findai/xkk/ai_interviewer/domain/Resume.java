package com.findai.xkk.ai_interviewer.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Resume implements Serializable {
    int uid=0;
    String name="";
    String birthday="";
    String nativeplace="";
    String gender="";
    String tele="";
    String email="";
    String ideal="";
    List<EducationExperience> eduexperience = new ArrayList<>();
    List<WorkExperience> workexperience = new ArrayList<>();
    String selfdescription;
    boolean hasResume=false;



    public  Resume(){
        this.eduexperience.add(new EducationExperience());
        this.workexperience.add(new WorkExperience());
    }

    public boolean isHasResume() {
        return hasResume;
    }

    public void setHasResume(boolean hasResume) {
        this.hasResume = hasResume;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNativeplace() {
        return nativeplace;
    }

    public void setNativeplace(String nativeplace) {
        this.nativeplace = nativeplace;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdeal() {
        return ideal;
    }

    public void setIdeal(String ideal) {
        this.ideal = ideal;
    }

    public List<EducationExperience> getEduexperience() {
        return eduexperience;
    }

    public void setEduexperience(List<EducationExperience> eduexperience) {
        this.eduexperience = eduexperience;
    }

    public List<WorkExperience> getWorkexperience() {
        return workexperience;
    }

    public void setWorkexperience(List<WorkExperience> workexperience) {
        this.workexperience = workexperience;
    }

    public String getSelfdescription() {
        return selfdescription;
    }

    public void setSelfdescription(String selfdescription) {
        this.selfdescription = selfdescription;
    }
}
