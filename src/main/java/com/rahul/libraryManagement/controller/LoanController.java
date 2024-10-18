package com.rahul.libraryManagement.controller;

import com.rahul.libraryManagement.constants.LoanStatus;
import com.rahul.libraryManagement.model.Book;
import com.rahul.libraryManagement.model.Loan;
import com.rahul.libraryManagement.model.Member;
import com.rahul.libraryManagement.service.BookService;
import com.rahul.libraryManagement.service.LoanService;
import com.rahul.libraryManagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private BookService bookService;

    @Autowired
    private MemberService memberService;

    // Endpoint to create a loan (borrow a book)
    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestParam Long bookId, @RequestParam Long memberId) {
        Optional<Book> bookOpt = bookService.findBookById(bookId);
        Optional<Member> memberOpt = memberService.findMemberById(memberId);

        if (bookOpt.isPresent() && memberOpt.isPresent()) {
            Book book = bookOpt.get();
            Member member = memberOpt.get();

            // Check if the book is available
            if (book.getCopiesAvailable() > 0) {
                Loan loan = new Loan();
                loan.setBook(book);
                loan.setMember(member);
                loan.setBorrowDate(LocalDate.now());
                loan.setDueDate(LocalDate.now().plusWeeks(1)); // Set due date as 2 weeks from borrow date
                loan.setStatus(LoanStatus.BORROWED);

                loanService.createLoan(loan);

                // Decrease the available copies of the book
                book.setCopiesAvailable(book.getCopiesAvailable() - 1);
                bookService.saveBook(book);

                return ResponseEntity.ok("Book borrowed successfully!");
            } else {
                return ResponseEntity.badRequest().body("No copies available for this book.");
            }
        } else {
            return ResponseEntity.badRequest().body("Book or member not found.");
        }
    }

    // Endpoint to return a book
    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestParam Long loanId) {
        Optional<Loan> loanOpt = loanService.findLoanById(loanId);

        if (loanOpt.isPresent()) {
            Loan loan = loanOpt.get();

            // Check if the book is already returned
            if (loan.getStatus() == LoanStatus.RETURNED) {
                return ResponseEntity.badRequest().body("This book has already been returned.");
            }

            // Mark the loan as returned
            loan.setReturnDate(LocalDate.now());
            loan.setStatus(LoanStatus.RETURNED);
            loanService.createLoan(loan);

            // Increase the available copies of the book
            Book book = loan.getBook();
            book.setCopiesAvailable(book.getCopiesAvailable() + 1);
            bookService.saveBook(book);

            return ResponseEntity.ok("Book returned successfully!");
        } else {
            return ResponseEntity.badRequest().body("Loan not found.");
        }
    }

    // Endpoint to get all loans by a member
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Loan>> getLoansByMember(@PathVariable Long memberId) {
        Optional<Member> memberOpt = memberService.findMemberById(memberId);

        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            List<Loan> loans = loanService.getLoansByMember(member);
            return ResponseEntity.ok(loans);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint to get all active loans (borrowed and not returned yet)
    @GetMapping("/active")
    public ResponseEntity<List<Loan>> getActiveLoans() {
        List<Loan> activeLoans = loanService.getLoansByStatus(LoanStatus.BORROWED);
        return ResponseEntity.ok(activeLoans);
    }
}

