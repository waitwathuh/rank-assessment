package com.rank.assessment.config.custom_advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rank.assessment.exception.WagerToLargeException;
import com.rank.assessment.model.ExceptionResponse;

@ControllerAdvice
public class Teapot {

	@ExceptionHandler({ WagerToLargeException.class })
	@ResponseBody
	public ResponseEntity<ExceptionResponse> playerNotFound(WagerToLargeException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setStatus("IM A TEAPOT");
		response.setMessage(ex.getMessage());

		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.I_AM_A_TEAPOT);
	}

}
