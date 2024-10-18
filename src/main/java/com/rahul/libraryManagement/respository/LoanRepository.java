package com.rahul.libraryManagement.respository;

import com.rahul.libraryManagement.constants.LoanStatus;
import com.rahul.libraryManagement.model.Loan;
import com.rahul.libraryManagement.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByMember(Member member);
    List<Loan> findByStatus(LoanStatus status);
}

