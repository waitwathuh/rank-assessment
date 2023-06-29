package com.rank.assessment;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.rank.assessment.entity.Player;
import com.rank.assessment.exception.PlayerNotFoundException;
import com.rank.assessment.model.GetBalanceResponse;
import com.rank.assessment.repository.PlayerRepository;
import com.rank.assessment.service.impl.RankInteractiveServiceImpl;

@SpringBootTest
public class GetBalanceTests {

	@Mock
	private PlayerRepository playerRepository;

	@InjectMocks
	private RankInteractiveServiceImpl rankInteractiveServiceImlp;

	@Test
	public void testNormalOperation() {
		int playerId = 10;
		String playerUsername = "UserJ";
		BigDecimal playerBalance = new BigDecimal(123.45);

		Player testPlayer = new Player(playerId, playerUsername, playerBalance);
		Mockito.when(playerRepository.findByPlayerId(anyInt())).thenReturn(testPlayer);

		GetBalanceResponse balanceResponse = rankInteractiveServiceImlp.getBalance(10);
		assertEquals(balanceResponse.getPlayerId(), playerId);
		assertEquals(balanceResponse.getBalance(), playerBalance);
	}

	@Test
	public void testUserNotFound() {
		Mockito.when(playerRepository.findByPlayerId(anyInt())).thenReturn(null);

		assertThatThrownBy(() -> {
			rankInteractiveServiceImlp.getBalance(20);
		}).isInstanceOf(PlayerNotFoundException.class)
		  .hasMessageContaining("Player Id not found:");
	}

}
