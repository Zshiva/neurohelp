package com.project.neurohelp.platform.exception;

public enum NeuroHelpErrorMessage {
    LOGIN_UNSUCCESSFUL("NH003","User Login Unsuccessful"),
    LOGIN_CREDENTIALS_DOES_NOT_MATCH("NHOO4","Login Credentials doesn't match"),
    REGISTER_UNSUCCESSFUL("NH001", "Register Unsuccessful" ),
    EMAIL_ID_ALREADY_EXISTS("","Email ID Already Existed" ),
    EMAIL_ID_REQUIRED("NH005", "Email ID is required" ),
    CITIZENSHIP_RECORD_IS_REQUIRED("NH006","Citizenship is required" ),
    CITIZENSHIP_RECORD_ALREADY_EXISTS("NH007","Citizenship Number already exists" ),
    PASSWORD_IS_REQUIRED("NH006","Password is required" ),

    USER_NOT_FOUND("NH008", "User not found"),
    OTP_IS_REQUIRED("NH009", "OTP is required"),
    OTP_INVALID_OR_EXPIRED("NH010", "OTP is invalid or expired"),
    NEW_PASSWORD_REQUIRED("NH011", "New password is required"),
    NEW_PASSWORD_MUST_BE_DIFFERENT("NH012", "New password must be different from old password");
    private String code;
    private String message;
    NeuroHelpErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode(){
        return code;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
}
