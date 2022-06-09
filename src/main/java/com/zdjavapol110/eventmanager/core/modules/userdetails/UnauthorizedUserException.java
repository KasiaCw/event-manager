package com.zdjavapol110.eventmanager.core.modules.userdetails;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "You must be login")
public class UnauthorizedUserException extends RuntimeException {}
