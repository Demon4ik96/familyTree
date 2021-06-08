package com.example.familyTree.controller;

import com.example.familyTree.model.Member;
import com.example.familyTree.model.Relation;
import com.example.familyTree.service.MemberService;
import com.example.familyTree.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;
    private final RelationService relationService;

    @Autowired
    public MemberController(MemberService memberService, RelationService relationService) {
        this.memberService = memberService;
        this.relationService = relationService;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listMembers", memberService.findAll());
        return "index";
    }

    @GetMapping("/showNewMemberFrom")
    public String showNewMemberForm(Model model) {
        Member member = new Member();
        model.addAttribute("member", member);
        return "new_member";
    }

    @PostMapping("/saveMember")
    public String saveMember(@ModelAttribute("member") Member member) {
        memberService.saveMember(member);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        Member member = memberService.getMemberById(id);
        model.addAttribute("member", member);
        return "update_member";
    }

    @GetMapping("/showFormForAddRelatives/{id}")
    public String showFormForAddRelatives(@PathVariable(value = "id") int id, Model model) {
        Member parent = new Member();
        model.addAttribute("member", memberService.getMemberById(id));
        model.addAttribute("parent", parent);
        model.addAttribute("listMembers", memberService.findAll());
        return "add_relatives";
    }

    @GetMapping("/showFormForViewRelatives/{id}")
    public String showFormForViewRelatives(@PathVariable(value = "id") int id, Model model) {
        Member member = memberService.getMemberById(id);
        model.addAttribute("member", member);
        String motherName = member.findMotherName();
        String fatherName = member.findFatherName();
        List<String> sonNames = member.findSonsName();
        List<String> daughterNames = member.findDaughtersName();
        model.addAttribute("motherName", motherName);
        model.addAttribute("fatherName", fatherName);
        model.addAttribute("sonNames", sonNames);
        model.addAttribute("daughterNames", daughterNames);
        return "view_relatives";
    }

    @PostMapping("/saveRelatives")
    public String saveRelatives(@RequestParam(value = "parentId") Integer parentId,
                                @RequestParam(value = "memberId") Integer memberId,
                                @RequestParam(value = "childId") Integer childId) {
        Member member = memberService.getMemberById(memberId);
        if (parentId == 0 && childId > 0) {
            Member child = memberService.getMemberById(childId);
            Relation childRelation = new Relation();
            member.assignChild(childRelation, child);
            relationService.saveRelation(childRelation);
            return "redirect:/";
        } else if (parentId > 0 && childId == 0) {
            Member parent = memberService.getMemberById(parentId);
            Relation parentRelation = new Relation();
            member.assignParent(parentRelation, parent);
            relationService.saveRelation(parentRelation);
            return "redirect:/";
        } else if (parentId > 0 && childId > 0) {
            Member child = memberService.getMemberById(childId);
            Relation childRelation = new Relation();
            member.assignChild(childRelation, child);
            relationService.saveRelation(childRelation);
            Member parent = memberService.getMemberById(parentId);
            Relation parentRelation = new Relation();
            member.assignParent(parentRelation, parent);
            relationService.saveRelation(parentRelation);
            return "redirect:/";
        } else return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable(value = "id") Integer id) {
        memberService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/filterMembers")
    public String filterByAge(@RequestParam(value = "startAge") int startAge,
                              @RequestParam(value = "finishAge") int finishAge,
                              Model model) {
        List<Member> filterList = memberService.getMembersBetweenAge(startAge, finishAge);
        model.addAttribute("filterList", filterList);
        model.addAttribute("startAge", startAge);
        model.addAttribute("finishAge", finishAge);
        return "filterByAge";
    }


}
