package com.rahul.libraryManagement.respository;

import com.rahul.libraryManagement.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    // Method to check if a member exists by phone number
    Optional<Member> findByPhoneNumber(String phoneNumber);

    Optional<Member> findByUsername(String username);
}
