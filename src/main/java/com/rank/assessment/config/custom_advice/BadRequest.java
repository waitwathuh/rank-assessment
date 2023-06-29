package com.rank.assessment.config.custom_advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

import com.rank.assessment.exception.PlayerNotFoundException;
import com.rank.assessment.model.ExceptionResponse;

@ControllerAdvice
public class BadRequest {

	@ExceptionHandler({ PlayerNotFoundException.class })
	@ResponseBody
	public ResponseEntity<ExceptionResponse> playerNotFound(PlayerNotFoundException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setStatus("Bad Request");
		response.setMessage(ex.getMessage());

		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseBody
	protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		ExceptionResponse response = new ExceptionResponse();
		response.setStatus("Bad Request");
		response.setMessage("Validation errors on request body: " + errors);

		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	}

}
