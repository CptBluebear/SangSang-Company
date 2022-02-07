package org.corodiak.sangsang.controller;

import org.corodiak.sangsang.type.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(
			HttpRequestMethodNotSupportedException.class
			)
	public ResponseEntity<Message> unexceptedRequestMethod(Exception ex) {
		
		Message message = new Message();
		message.setMessage("UNEXCEPTED METHOD");
		
        return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(
			MethodArgumentTypeMismatchException.class
			)
	public ResponseEntity<Message> badParameter(Exception ex) {
		
		Message message = new Message();
		message.setMessage("REQUEST PARAMETER HAS PROBLEM ");
		
        return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
    }
}
