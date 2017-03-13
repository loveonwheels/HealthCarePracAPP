package com.mSIHAT.hcp.classes;

/**
 * Created by ghost on 17/8/16.
 */
public class HCPAuthentication {
    public HCPAuthentication(int detail, String hcp_url) {
        this.detail = detail;
        this.hcp_url = hcp_url;
    }

    public int getDetail() {
        return detail;
    }

    public void setDetail(int detail) {
        this.detail = detail;
    }

    public int detail;
    public String hcp_url;

}
