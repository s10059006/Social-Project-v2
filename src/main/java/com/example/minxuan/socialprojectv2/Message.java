package com.example.minxuan.socialprojectv2;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MinXuan on 2017/5/24.
 */

public class Message {

    @SerializedName("TAG")
    private String TAG;

    @SerializedName("account")
    private String account;

    @SerializedName("password")
    private String password;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("phonenumber")
    private String phonenumber;

    @SerializedName("email")
    private String email;

    @SerializedName("confirmpassword")
    private String confirmpassword;

    public void setTAG(String tag){
        this.TAG = tag;
    }
    public void setAccount(String account){
        this.account = account;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setPhonenumber(String phonenumber){
        this.phonenumber = phonenumber;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setConfirmpassword(String confirmpassword){
        this.confirmpassword = confirmpassword;
    }


    public String getTAG(){
        return this.TAG;
    }
    public String getAccount(){
        return this.account;
    }
    public String getPassword(){
        return this.password;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getPhonenumber(){
        return this.phonenumber;
    }
    public String getEmail(){
        return this.email;
    }
    public String getConfirmpassword(){
        return this.confirmpassword;
    }

}
