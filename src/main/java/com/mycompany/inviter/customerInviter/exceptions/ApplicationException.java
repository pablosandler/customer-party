package com.mycompany.inviter.customerInviter.exceptions;

public class ApplicationException extends Exception {

    public ApplicationException(String message, Throwable e){
        super(message,e);
    }
}
