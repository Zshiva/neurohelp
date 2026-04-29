package com.project.neurohelp.platform.exception;

public class NeuroHelpException extends RuntimeException {
    private final NeuroHelpErrorMessage errorMessage;

    public NeuroHelpException(NeuroHelpErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
    public NeuroHelpErrorMessage getErrorMessage(){
        return errorMessage;
    }
}

