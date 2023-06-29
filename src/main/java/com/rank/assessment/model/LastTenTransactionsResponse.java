package com.rank.assessment.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LastTenTransactionsResponse {

	private BigInteger transactionId;

	private TransactionType transactionType;

	private BigDecimal amount;

}
