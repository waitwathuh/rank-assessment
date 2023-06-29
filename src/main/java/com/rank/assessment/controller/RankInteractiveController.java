package com.rank.assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rank.assessment.model.ExceptionResponse;
import com.rank.assessment.model.GetBalanceResponse;
import com.rank.assessment.model.LastTenTransactionsRequest;
import com.rank.assessment.model.LastTenTransactionsResponse;
import com.rank.assessment.model.UpdateBalanceRequest;
import com.rank.assessment.model.UpdateBalanceResponse;

import com.rank.assessment.service.RankInteractiveService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.Valid;

@RestController
public class RankInteractiveController {

	private RankInteractiveService rankService;

	@Autowired
	public RankInteractiveController(RankInteractiveService rankService) {
		this.rankService = rankService;
	}

	@GetMapping(path = "/player/{playerId}/balance")
	@Operation(tags = { "auth-controller" }, summary = "Get Balance", responses = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = GetBalanceResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")) })
	public @ResponseBody ResponseEntity<GetBalanceResponse> getBalance(@PathVariable("playerId") int playerId) {
		return new ResponseEntity<>(rankService.getBalance(playerId), HttpStatus.OK);
	}

	@PostMapping(path = "/player/{playerId}/balance/update")
	@Operation(tags = { "auth-controller" }, summary = "Update Balance", responses = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = UpdateBalanceResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")) })
	public @ResponseBody ResponseEntity<UpdateBalanceResponse> updateBalance(@PathVariable("playerId") int playerId,
			@Valid @RequestBody UpdateBalanceRequest request) {
		return new ResponseEntity<>(rankService.updateBalance(playerId, request), HttpStatus.OK);
	}

	@PostMapping(path = "/admin/player/transactions")
	@Operation(tags = { "auth-controller" }, summary = "Last 10 Transactions", responses = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = LastTenTransactionsResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")) })
	public @ResponseBody ResponseEntity<List<LastTenTransactionsResponse>> lastTenTransactions(
			@Valid @RequestBody LastTenTransactionsRequest request) {
		return new ResponseEntity<>(rankService.lastTenTransactions(request), HttpStatus.OK);
	}

}
