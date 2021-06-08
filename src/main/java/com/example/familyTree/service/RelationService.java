package com.example.familyTree.service;

import com.example.familyTree.model.Relation;
import com.example.familyTree.persistance.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationService {

    private final RelationRepository relationRepository;

    @Autowired
    public RelationService(RelationRepository relationRepository) {
        this.relationRepository = relationRepository;
    }

    public Relation saveRelation(Relation relation) {
        return relationRepository.save(relation);
    }

}
