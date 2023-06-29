package com.rank.assessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rank.assessment.entity.Transaction;

public interface TransactionRepository  extends JpaRepository<Transaction, Integer> {

	@Query("SELECT t FROM Transaction t WHERE t.id = :id")
	Transaction findByTransactionId(@Param("id") int id);

	@Query(nativeQuery = true, value = "SELECT * FROM Transaction t WHERE t.playerId = :playerId ORDER BY id DESC LIMIT 10")
	List<Transaction> findLastTenTransactions(@Param("playerId") int playerId);

}
