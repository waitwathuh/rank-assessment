package com.rank.assessment.service;

import java.util.List;

import com.rank.assessment.model.GetBalanceResponse;
import com.rank.assessment.model.LastTenTransactionsRequest;
import com.rank.assessment.model.LastTenTransactionsResponse;
import com.rank.assessment.model.UpdateBalanceRequest;
import com.rank.assessment.model.UpdateBalanceResponse;

public interface RankInteractiveService {

	public GetBalanceResponse getBalance(int playerId);
	
	public UpdateBalanceResponse updateBalance(int playerId, UpdateBalanceRequest request);
	
	public List<LastTenTransactionsResponse> lastTenTransactions(LastTenTransactionsRequest request);

}
