package com.v.vapp;

import android.util.Base64;

import java.io.Serializable;

/**
 * @author V
 * @since 2018/5/27
 */
public class PwdModel implements Serializable {
    private String platform;
    private String accountName;
    private String pwdBase64;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPwdBase64() {
        return pwdBase64;
    }

    public void setPwdBase64(String pwdBase64) {
        this.pwdBase64 = pwdBase64;
    }

    public String getPwd() {
        return new String(Base64.decode(pwdBase64, Base64.NO_WRAP));
    }
}
