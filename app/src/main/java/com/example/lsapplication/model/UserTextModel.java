package com.example.lsapplication.model;

public class UserTextModel {
    String FirstNameText, LastNameText, EmailText, PasswordText, CompirmPasswordText, personalDetails, SignUpText, SignInText, haveAccount, TremOfUse;

    public UserTextModel(String firstNameText, String lastNameText, String emailText, String passwordText, String compirmPasswordText, String personalDetails, String  SignUpText , String SignInText, String haveAccount ,String TremOfUse) {
        this.FirstNameText = firstNameText;
        this.LastNameText = lastNameText;
        this.EmailText = emailText;
        this.PasswordText = passwordText;
        this.CompirmPasswordText = compirmPasswordText;
        this.personalDetails= personalDetails;
        this.SignUpText = SignUpText;
        this.SignInText = SignInText;
        this.haveAccount= haveAccount;
        this.TremOfUse= TremOfUse;
    }

    public String getSignInText() {
        return SignInText;
    }

    public void setSignInText(String signInText) {
        SignInText = signInText;
    }

    public String getHaveAccount() {
        return haveAccount;
    }

    public void setHaveAccount(String haveAccount) {
        this.haveAccount = haveAccount;
    }

    public String getTremOfUse() {
        return TremOfUse;
    }

    public void setTremOfUse(String tremOfUse) {
        TremOfUse = tremOfUse;
    }

    public String getSignUpText() {
        return SignUpText;
    }

    public void setSignUpText(String signUpText) {
        SignUpText = signUpText;
    }

    public String getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(String personalDetails) {
        this.personalDetails = personalDetails;
    }

    public String getFirstNameText() {
        return FirstNameText;
    }

    public void setFirstNameText(String firstNameText) {
        FirstNameText = firstNameText;
    }

    public String getLastNameText() {
        return LastNameText;
    }

    public void setLastNameText(String lastNameText) {
        LastNameText = lastNameText;
    }

    public String getEmailText() {
        return EmailText;
    }

    public void setEmailText(String emailText) {
        EmailText = emailText;
    }

    public String getPasswordText() {
        return PasswordText;
    }

    public void setPasswordText(String passwordText) {
        PasswordText = passwordText;
    }

    public String getCompirmPasswordText() {
        return CompirmPasswordText;
    }

    public void setCompirmPasswordText(String compirmPasswordText) {
        CompirmPasswordText = compirmPasswordText;
    }
}
