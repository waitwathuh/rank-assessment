package com.rank.assessment.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rank.assessment.entity.Player;
import com.rank.assessment.entity.Transaction;
import com.rank.assessment.exception.PlayerNotFoundException;
import com.rank.assessment.exception.WagerToLargeException;
import com.rank.assessment.model.GetBalanceResponse;
import com.rank.assessment.model.LastTenTransactionsRequest;
import com.rank.assessment.model.LastTenTransactionsResponse;
import com.rank.assessment.model.TransactionType;
import com.rank.assessment.model.UpdateBalanceRequest;
import com.rank.assessment.model.UpdateBalanceResponse;
import com.rank.assessment.repository.PlayerRepository;
import com.rank.assessment.repository.TransactionRepository;
import com.rank.assessment.service.RankInteractiveService;

@Service
public class RankInteractiveServiceImpl implements RankInteractiveService {

	private PlayerRepository playerRepository;

	private TransactionRepository transactionRepository;

	@Autowired
	public RankInteractiveServiceImpl(PlayerRepository playerRepository, TransactionRepository transactionRepository) {
		this.playerRepository = playerRepository;
		this.transactionRepository = transactionRepository;
	}

	@Override
	public GetBalanceResponse getBalance(int playerId) {
		Player player = playerRepository.findByPlayerId(playerId);

		if (player == null) {
			throw new PlayerNotFoundException("Player Id not found: " + playerId);
		}

		GetBalanceResponse response = new GetBalanceResponse();
		response.setPlayerId(player.getId());
		response.setBalance(player.getBalance());

		return response;
	}

	@Override
	public UpdateBalanceResponse updateBalance(int playerId, UpdateBalanceRequest request) {
		Player player = playerRepository.findByPlayerId(playerId);

		if (player == null) {
			throw new PlayerNotFoundException("Player Id not found: " + playerId);
		}

		BigDecimal newBalance;

		if (request.getTransactionType().equals(TransactionType.WAGER)) {
			newBalance = player.getBalance().subtract(request.getAmount());
		} else {
			newBalance = player.getBalance().add(request.getAmount());
		}

		if (request.getTransactionType() == TransactionType.WAGER && newBalance.signum() < 0) {
			throw new WagerToLargeException(String.format("Available balance {%s} is less than wager amount {%s}",
					player.getBalance(), request.getAmount()));
		}

		Transaction transaction = new Transaction();
		transaction.setPlayer(player);
		transaction.setTransactionType(request.getTransactionType());
		transaction.setAmount(request.getAmount());
		transaction = transactionRepository.save(transaction);

		player.setBalance(newBalance);
		playerRepository.save(player);

		return new UpdateBalanceResponse(transaction.getId(), player.getBalance());
	}

	@Override
	public List<LastTenTransactionsResponse> lastTenTransactions(LastTenTransactionsRequest request) {

		Player player = playerRepository.findByPlayerUsername(request.getUsername());

		if (player == null) {
			throw new PlayerNotFoundException("Player with username not found: " + request.getUsername());
		}

		List<Transaction> transactionList = transactionRepository.findLastTenTransactions(player.getId());
		List<LastTenTransactionsResponse> response = new ArrayList<LastTenTransactionsResponse>();

		for (Transaction transaction : transactionList) {
			response.add(new LastTenTransactionsResponse(transaction.getId(), transaction.getTransactionType(),
					transaction.getAmount()));
		}

		return response;
	}

}
