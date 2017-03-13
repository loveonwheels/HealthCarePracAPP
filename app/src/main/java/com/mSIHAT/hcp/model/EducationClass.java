package com.mSIHAT.hcp.model;

/**
 * Created by ghost on 28/1/16.
 */
public class EducationClass {

    private String universityName;
    private String startDate;
    private String endDate;
    private String award;


    public EducationClass() {
    }

    public EducationClass(String startDate, String universityName, String endDate, String award) {
        this.startDate = startDate;
        this.universityName = universityName;
        this.endDate = endDate;
        this.award = award;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }
}
