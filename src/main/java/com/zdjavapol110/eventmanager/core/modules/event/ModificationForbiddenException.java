package com.zdjavapol110.eventmanager.core.modules.event;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Only Author can modify or delete object")
public class ModificationForbiddenException extends RuntimeException {}
