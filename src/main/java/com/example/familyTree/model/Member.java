package com.example.familyTree.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @JsonIgnore
    @OneToMany(mappedBy = "parent")
    Set<Relation> parentRelations;

    @JsonIgnore
    @OneToMany(mappedBy = "child")
    Set<Relation> childRelations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }


    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<Relation> getParentRelations() {
        return parentRelations;
    }

    public void setParentRelations(Set<Relation> parentRelations) {
        this.parentRelations = parentRelations;
    }

    public Set<Relation> getChildRelations() {
        return childRelations;
    }

    public void setChildRelations(Set<Relation> childRelations) {
        this.childRelations = childRelations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", gender=" + gender +
                '}';
    }

    public void assignParent(Relation tempRelation, Member parent) {
        if(parentRelations==null)
            parentRelations = new HashSet<>();
        parentRelations.add(tempRelation);
        tempRelation.setParent(parent);
        tempRelation.setChild(this);
    }


    public void assignChild(Relation tempRelation, Member child) {
        if(childRelations==null)
            childRelations = new HashSet<>();
        childRelations.add(tempRelation);
        tempRelation.setChild(child);
        tempRelation.setParent(this);
    }

    public String findMotherName()
    {
        for(Relation relation : getChildRelations()) {
            if (relation.getParent().gender == Gender.F)
                return relation.getParent().name;
        }
        return "";
    }


    public String findFatherName()
    {
        for(Relation relation : getChildRelations()) {
            if (relation.getParent().gender == Gender.M)
                return relation.getParent().name;
        }
        return "";
    }

   public List<String> findSonsName()
   {
       List<String> result = new ArrayList<>();
       for(Relation relation : getParentRelations()) {
           if(relation.getChild().gender==Gender.M)
               result.add(relation.getChild().name);
           else result.add("");
       }
       return result;
   }

    public List<String> findDaughtersName()
    {
        List<String> result = new ArrayList<>();
        for(Relation relation : getParentRelations()) {
            if(relation.getChild().gender==Gender.F)
                result.add(relation.getChild().name);
            else result.add("");
        }
        return result;
    }




}
