package com.rahul.libraryManagement.service;

import com.rahul.libraryManagement.model.Member;
import com.rahul.libraryManagement.respository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public String saveMember(Member member) {

        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            return "Error: Email is already in use!";
        }

        // Check if the phone number already exists
        if (memberRepository.findByPhoneNumber(member.getPhoneNumber()).isPresent()) {
            return "Error: Phone number is already in use!";
        }
        memberRepository.save(member);
        return "member created successfully";
    }

    public Optional<Member> findMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public void deleteMemberById(Long id){
        memberRepository.deleteById(id);
    }

}

