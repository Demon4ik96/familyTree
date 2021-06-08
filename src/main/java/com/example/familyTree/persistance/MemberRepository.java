package com.example.familyTree.persistance;

import com.example.familyTree.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    @Query("SELECT u FROM Member u WHERE FLOOR(DATEDIFF(CURRENT_DATE(),birthdate)/365) BETWEEN :startAge AND :finishAge")
    public List<Member> getMembersBetweenAge(@Param("startAge") int startAge, @Param("finishAge") int finishAge);

}
