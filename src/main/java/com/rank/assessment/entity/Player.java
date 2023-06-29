package com.rank.assessment.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "PLAYER")
@Entity
public class Player {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, insertable = false)
    private int id;

    @NotNull(message = "Username cannot be null")
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    
    @Column(name = "balance", nullable = false)
    private BigDecimal balance = new BigDecimal(0.0);

}
