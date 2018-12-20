package com.findai.xkk.ai_interviewer.domain;

import java.io.Serializable;

public class Job implements Serializable {
    String companyId;
    String companyShort;
    String companyType;
    String degree;
    String endDate;
    int id_job;
    String industry;
    String jobDescript;
    String jobName;
    String companyLogo;
    String jobRequest;
    byte[] bitmap;
    int payMax;
    int payMin;
    String site;
    int wantNum;
    String workPlace;
    int workType;

    String application_status;

    public String getApplication_status() {
        return application_status;
    }

    public void setApplication_status(String application_status) {
        this.application_status = application_status;
    }

    public byte[] getBitmap() {
        return bitmap;
    }

    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyShort() {
        return companyShort;
    }

    public void setCompanyShort(String companyShort) {
        this.companyShort = companyShort;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getId_job() {
        return id_job;
    }

    public void setId_job(int id_job) {
        this.id_job = id_job;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getJobDescript() {
        return jobDescript;
    }

    public void setJobDescript(String jobDescript) {
        this.jobDescript = jobDescript;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobRequest() {
        return jobRequest;
    }

    public void setJobRequest(String jobRequest) {
        this.jobRequest = jobRequest;
    }

    public int getPayMax() {
        return payMax;
    }

    public void setPayMax(int payMax) {
        this.payMax = payMax;
    }

    public int getPayMin() {
        return payMin;
    }

    public void setPayMin(int payMin) {
        this.payMin = payMin;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getWantNum() {
        return wantNum;
    }

    public void setWantNum(int wantNum) {
        this.wantNum = wantNum;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }
}
