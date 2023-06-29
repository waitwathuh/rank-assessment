package com.rank.assessment;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.rank.assessment.entity.Player;
import com.rank.assessment.entity.Transaction;
import com.rank.assessment.exception.PlayerNotFoundException;
import com.rank.assessment.model.LastTenTransactionsRequest;
import com.rank.assessment.model.LastTenTransactionsResponse;
import com.rank.assessment.model.TransactionType;
import com.rank.assessment.repository.PlayerRepository;
import com.rank.assessment.repository.TransactionRepository;
import com.rank.assessment.service.impl.RankInteractiveServiceImpl;

@SpringBootTest
public class LastTenTransactionsTests {

	@Mock
	private PlayerRepository playerRepository;

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private RankInteractiveServiceImpl rankInteractiveServiceImlp;

	@Test
	public void testNormalOperation() {
		int playerId = 10;
		String playerUsername = "UserJ";
		BigDecimal playerBalance = new BigDecimal(123.45);

		Player testPlayer = new Player(playerId, playerUsername, playerBalance);
		Mockito.when(playerRepository.findByPlayerUsername(anyString())).thenReturn(testPlayer);

		Transaction transactionA = new Transaction(new BigInteger("1"), new BigDecimal("1.23"), TransactionType.WAGER, testPlayer);
		Transaction transactionB = new Transaction(new BigInteger("2"), new BigDecimal("2.46"), TransactionType.WIN, testPlayer);

		List<Transaction> transactionList = new ArrayList<Transaction>();
		transactionList.add(transactionA);
		transactionList.add(transactionB);

		Mockito.when(transactionRepository.findLastTenTransactions(anyInt())).thenReturn(transactionList);

		List<LastTenTransactionsResponse> response = rankInteractiveServiceImlp.lastTenTransactions(new LastTenTransactionsRequest(playerUsername));
		assertEquals(response.get(0).getTransactionId(), transactionA.getId());
		assertEquals(response.get(0).getTransactionType(), transactionA.getTransactionType());
		assertEquals(response.get(0).getAmount(), transactionA.getAmount());
		assertEquals(response.get(1).getTransactionId(), transactionB.getId());
		assertEquals(response.get(1).getTransactionType(), transactionB.getTransactionType());
		assertEquals(response.get(1).getAmount(), transactionB.getAmount());
	}

	@Test
	public void testUsernameNotFound() {
		LastTenTransactionsRequest request = new LastTenTransactionsRequest("userJ");

		Mockito.when(playerRepository.findByPlayerId(anyInt())).thenReturn(null);
		
		assertThatThrownBy(() -> {
			rankInteractiveServiceImlp.lastTenTransactions(request);
		}).isInstanceOf(PlayerNotFoundException.class)
		  .hasMessageContaining("Player with username not found:");
	}

}
