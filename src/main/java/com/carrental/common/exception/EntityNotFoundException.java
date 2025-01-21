package com.carrental.common.exception;

import com.carrental.common.support.error.ErrorCode;
import org.slf4j.event.Level;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException(Level level, String message) {
        super(ErrorCode.COMMON_ENTITY_NOT_FOUND, level, message);
    }

    public EntityNotFoundException(Level level) {
        super(ErrorCode.COMMON_ENTITY_NOT_FOUND, level);
    }

    public EntityNotFoundException(String message) {
        super(ErrorCode.COMMON_ENTITY_NOT_FOUND, message);
    }

    public EntityNotFoundException() {
        super(ErrorCode.COMMON_ENTITY_NOT_FOUND);
    }
}
