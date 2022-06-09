package com.zdjavapol110.eventmanager.core.modules.event;

public class UserEventIllegalStateException extends IllegalStateException {
    private static final long serialVersionUID = 1L;

    public UserEventIllegalStateException(String message) {
        super(message);
    }

    public UserEventIllegalStateException(Throwable e) {
        super(e);
    }

}
