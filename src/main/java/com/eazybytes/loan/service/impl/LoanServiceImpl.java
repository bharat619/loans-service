package com.eazybytes.loan.service.impl;

import com.eazybytes.loan.constants.LoanConstants;
import com.eazybytes.loan.dto.LoanDto;
import com.eazybytes.loan.entity.Loan;
import com.eazybytes.loan.exception.LoanAlreadyExists;
import com.eazybytes.loan.exception.ResourceNotFound;
import com.eazybytes.loan.mapper.LoansMapper;
import com.eazybytes.loan.repository.LoanRepository;
import com.eazybytes.loan.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
@Service
public class LoanServiceImpl implements LoanService {
    private LoanRepository loansRepository;
    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loan> optionalLoans= loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExists("Loan already registered with given mobileNumber "+mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    @Override
    public LoanDto fetchLoan(String mobileNumber) {
        Loan loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFound("Loan", "mobileNumber", mobileNumber)
        );
        return LoansMapper.mapToLoansDto(loans, new LoanDto());
    }

    @Override
    public boolean updateLoan(LoanDto loansDto) {
        Loan loan = loansRepository.findByLoanNumber(loansDto.getLoanNumber())
                .orElseThrow(() -> new ResourceNotFound("Loan", "loanNumber", loansDto.getLoanNumber()));

        LoansMapper.mapToLoans(loansDto, loan);
        loansRepository.save(loan);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loan loan = loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFound("Loan", "mobileNumber", mobileNumber));
        loansRepository.delete(loan);
        return true;
    }

    private Loan createNewLoan(String mobileNumber) {
        Loan newLoan = new Loan();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoanConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }
}
