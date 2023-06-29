package com.rank.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.rank.assessment.entity")
public class RankAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(RankAssessmentApplication.class, args);
	}

}
