package com.rahul.libraryManagement.service;

import com.rahul.libraryManagement.constants.LoanStatus;
import com.rahul.libraryManagement.model.Loan;
import com.rahul.libraryManagement.model.Member;
import com.rahul.libraryManagement.respository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public Optional<Loan> findLoanById(Long id) {
        return loanRepository.findById(id);
    }

    public List<Loan> getLoansByMember(Member member) {
        return loanRepository.findByMember(member);
    }

    public List<Loan> getLoansByStatus(LoanStatus status) {
        return loanRepository.findByStatus(status);
    }
}

