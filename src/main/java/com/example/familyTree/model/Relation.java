package com.example.familyTree.model;

import javax.persistence.*;

@Entity
@Table(name = "relation")
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relation_id")
    private Integer relationId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Member parent;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "child_id", referencedColumnName = "id")
    private Member child;

    public Member getParent() {
        return parent;
    }

    public void setParent(Member parent) {
        this.parent = parent;
    }

    public Member getChild() {
        return child;
    }

    public void setChild(Member child) {
        this.child = child;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "relationId=" + relationId +
                ", parentId=" + parent +
                ", childId=" + child +
                '}';
    }


}
