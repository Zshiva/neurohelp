package com.project.neurohelp.platform.exception;

public enum NeuroHelpErrorMessage {
    LOGIN_UNSUCCESSFUL("NH001","User Login Unsuccessful"),
    LOGIN_CREDENTIALS_DOES_NOT_MATCH("NHOO2","Login Credentials doesn't match")

    ;
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
