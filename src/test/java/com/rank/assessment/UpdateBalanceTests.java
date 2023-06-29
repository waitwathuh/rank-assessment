package com.rank.assessment;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.rank.assessment.entity.Player;
import com.rank.assessment.entity.Transaction;
import com.rank.assessment.exception.PlayerNotFoundException;
import com.rank.assessment.exception.WagerToLargeException;
import com.rank.assessment.model.TransactionType;
import com.rank.assessment.model.UpdateBalanceRequest;
import com.rank.assessment.model.UpdateBalanceResponse;
import com.rank.assessment.repository.PlayerRepository;
import com.rank.assessment.repository.TransactionRepository;
import com.rank.assessment.service.impl.RankInteractiveServiceImpl;

@SpringBootTest
public class UpdateBalanceTests {

	@Mock
	private PlayerRepository playerRepository;

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private RankInteractiveServiceImpl rankInteractiveServiceImlp;

	@Test
	public void testNormalWagerOperation() {
		int playerId = 10;
		String playerUsername = "UserJ";
		BigDecimal playerBalance = new BigDecimal(123.45);
		BigDecimal playerNewBalance = new BigDecimal(111.11);
		BigDecimal amount = new BigDecimal(12.34);
		TransactionType transactionType = TransactionType.WAGER;

		UpdateBalanceRequest request = new UpdateBalanceRequest(amount, transactionType);

		Player testPlayer = new Player(playerId, playerUsername, playerBalance);
		Mockito.when(playerRepository.findByPlayerId(anyInt())).thenReturn(testPlayer);

		Transaction transaction = new Transaction();
		transaction.setId(new BigInteger("1"));
		transaction.setPlayer(testPlayer);
		transaction.setTransactionType(request.getTransactionType());
		transaction.setAmount(request.getAmount());
		Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		UpdateBalanceResponse response = rankInteractiveServiceImlp.updateBalance(playerId, request);
		assertTrue(response.getTransactionId().compareTo(new BigInteger("1")) == 0); 
		assertEquals(response.getBalance().setScale(2, RoundingMode.HALF_UP), playerNewBalance.setScale(2, RoundingMode.HALF_UP));
	}

	@Test
	public void testNormalWinOperation() {
		int playerId = 10;
		String playerUsername = "UserJ";
		BigDecimal playerBalance = new BigDecimal(123.45);
		BigDecimal playerNewBalance = new BigDecimal(135.79);
		BigDecimal amount = new BigDecimal(12.34);
		TransactionType transactionType = TransactionType.WIN;

		UpdateBalanceRequest request = new UpdateBalanceRequest(amount, transactionType);

		Player testPlayer = new Player(playerId, playerUsername, playerBalance);
		Mockito.when(playerRepository.findByPlayerId(anyInt())).thenReturn(testPlayer);

		Transaction transaction = new Transaction();
		transaction.setId(new BigInteger("1"));
		transaction.setPlayer(testPlayer);
		transaction.setTransactionType(request.getTransactionType());
		transaction.setAmount(request.getAmount());
		Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		UpdateBalanceResponse response = rankInteractiveServiceImlp.updateBalance(playerId, request);
		assertTrue(response.getTransactionId().compareTo(new BigInteger("1")) == 0); 
		assertEquals(response.getBalance().setScale(2, RoundingMode.HALF_UP), playerNewBalance.setScale(2, RoundingMode.HALF_UP));
	}

	@Test
	public void testUserNotFound() {
		Mockito.when(playerRepository.findByPlayerId(anyInt())).thenReturn(null);
		
		int playerId = 10;
		BigDecimal amount = new BigDecimal(12.34);
		TransactionType transactionType = TransactionType.WIN;

		UpdateBalanceRequest request = new UpdateBalanceRequest(amount, transactionType);

		assertThatThrownBy(() -> {
			rankInteractiveServiceImlp.updateBalance(playerId, request);
		}).isInstanceOf(PlayerNotFoundException.class)
		  .hasMessageContaining("Player Id not found:");
	}

	@Test
	public void testWagerGraterThanBalance() {
		int playerId = 10;
		String playerUsername = "UserJ";
		BigDecimal playerBalance = new BigDecimal(12.34);
		BigDecimal amount = new BigDecimal(12.35);
		TransactionType transactionType = TransactionType.WAGER;

		UpdateBalanceRequest request = new UpdateBalanceRequest(amount, transactionType);

		Player testPlayer = new Player(playerId, playerUsername, playerBalance);
		Mockito.when(playerRepository.findByPlayerId(anyInt())).thenReturn(testPlayer);
		
		Transaction transaction = new Transaction();
		transaction.setId(new BigInteger("1"));
		transaction.setPlayer(testPlayer);
		transaction.setTransactionType(request.getTransactionType());
		transaction.setAmount(request.getAmount());
		Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		assertThatThrownBy(() -> {
			rankInteractiveServiceImlp.updateBalance(playerId, request);
		}).isInstanceOf(WagerToLargeException.class)
		  .hasMessageContaining("Available balance");
	}

	@Test
	public void testWagerSameAsBalance() {
		int playerId = 10;
		String playerUsername = "UserJ";
		BigDecimal playerBalance = new BigDecimal(12.34);
		BigDecimal playerNewBalance = new BigDecimal(0.00);
		BigDecimal amount = new BigDecimal(12.34);
		TransactionType transactionType = TransactionType.WAGER;

		UpdateBalanceRequest request = new UpdateBalanceRequest(amount, transactionType);

		Player testPlayer = new Player(playerId, playerUsername, playerBalance);
		Mockito.when(playerRepository.findByPlayerId(anyInt())).thenReturn(testPlayer);
		
		Transaction transaction = new Transaction();
		transaction.setId(new BigInteger("1"));
		transaction.setPlayer(testPlayer);
		transaction.setTransactionType(request.getTransactionType());
		transaction.setAmount(request.getAmount());
		Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		UpdateBalanceResponse response = rankInteractiveServiceImlp.updateBalance(playerId, request);
		assertTrue(response.getTransactionId().compareTo(new BigInteger("1")) == 0); 
		assertEquals(response.getBalance().setScale(2, RoundingMode.HALF_UP), playerNewBalance.setScale(2, RoundingMode.HALF_UP));
	}

}
