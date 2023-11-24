package com.programming.man.mdchat.exceptions;

public class SpringMDChatException extends RuntimeException {
    public SpringMDChatException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringMDChatException(String exMessage) {
        super(exMessage);
    }
}
