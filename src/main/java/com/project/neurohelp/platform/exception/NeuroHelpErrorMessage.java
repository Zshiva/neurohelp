package com.project.neurohelp.platform.exception;

public enum NeuroHelpErrorMessage {
    LOGIN_UNSUCCESSFUL("NH003","User Login Unsuccessful"),
    LOGIN_CREDENTIALS_DOES_NOT_MATCH("NH004","Login Credentials doesn't match"),
    REGISTER_UNSUCCESSFUL("NH001", "Register Unsuccessful" ),
    EMAIL_ID_ALREADY_EXISTS("NH002","Email ID Already Existed" ),
    EMAIL_ID_REQUIRED("NH005", "Email ID is required" ),
    CITIZENSHIP_RECORD_IS_REQUIRED("NH006","Citizenship is required" ),
    CITIZENSHIP_RECORD_ALREADY_EXISTS("NH007","Citizenship Number already exists" ),
    PASSWORD_IS_REQUIRED("NH008","Password is required" ),

    USER_NOT_FOUND("NH009", "User not found"),
    OTP_IS_REQUIRED("NH010", "OTP is required"),
    OTP_INVALID_OR_EXPIRED("NH011", "OTP is invalid or expired"),
    NEW_PASSWORD_REQUIRED("NH012", "New password is required"),
    NEW_PASSWORD_MUST_BE_DIFFERENT("NH013", "New password must be different from old password"),

    CHAT_SESSION_ID_REQUIRED("NH014", "Session ID is required"),
    CHAT_USER_MESSAGE_REQUIRED("NH015", "User message is required"),
    CHAT_USER_MESSAGE_TOO_LONG("NH016", "User message is too long"),
    AI_SERVICE_UNAVAILABLE("NH017", "AI service is unavailable"),
    AI_SERVICE_BAD_RESPONSE("NH018", "AI service returned an invalid response"),

    WELLNESS_SESSION_ID_REQUIRED("NH019", "Wellness session ID is required"),
    WELLNESS_CHECK_IN_INVALID("NH020", "Wellness check-in data is invalid");

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
