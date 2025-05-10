package com.eazybytes.loan;

import com.eazybytes.loan.dto.LoansContactInformation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {LoansContactInformation.class})
public class LoanApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoanApplication.class, args);
	}
}
