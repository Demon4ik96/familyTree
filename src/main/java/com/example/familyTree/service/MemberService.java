package com.example.familyTree.service;


import com.example.familyTree.model.Member;
import com.example.familyTree.persistance.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member saveMember(Member member) {

        return memberRepository.save(member);
    }

    public Member getMemberById(int id) {
        Optional<Member> optional = memberRepository.findById(id);
        Member member = null;
        if (optional.isPresent()) {
            member = optional.get();
        } else {
            throw new RuntimeException("Member not found for id: " + id);
        }
        return member;
    }

    public void deleteById(Integer id) {
        memberRepository.deleteById(id);
    }

    public List<Member> getMembersBetweenAge(int startAge, int finishAge) {
        return memberRepository.getMembersBetweenAge(startAge, finishAge);
    }


}
