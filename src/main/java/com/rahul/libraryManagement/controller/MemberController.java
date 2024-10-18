package com.rahul.libraryManagement.controller;

import com.rahul.libraryManagement.model.Member;
import com.rahul.libraryManagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @PostMapping
    public String addMember(@RequestBody Member member) {
        return memberService.saveMember(member);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberService.findMemberById(id)
                .map(member -> ResponseEntity.ok().body(member))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id){
          memberService.deleteMemberById(id);
    }
}

