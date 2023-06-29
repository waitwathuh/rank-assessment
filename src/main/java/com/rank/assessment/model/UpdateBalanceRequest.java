package com.rank.assessment.model;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
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
public class UpdateBalanceRequest {

	@DecimalMin(value = "0.01", message = "Minimum value allowed: 0.01")
	@Digits(integer = 3, fraction = 2, message = "Max integral is 3 and max fraction is 2")
	private BigDecimal amount;

	private TransactionType transactionType;

}
