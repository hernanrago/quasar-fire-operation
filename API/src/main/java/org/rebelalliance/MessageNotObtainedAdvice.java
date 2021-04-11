package org.rebelalliance;

import org.rebelalliance.exceptions.MessageNotObtainedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MessageNotObtainedAdvice {
	  @ResponseBody
	  @ExceptionHandler(MessageNotObtainedException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  String messageNotObtainedHandler(MessageNotObtainedException ex) {
	    return ex.getMessage();
	  }
}
