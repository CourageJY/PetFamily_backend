package com.pet.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "logistics")
public class Logistic {
    @EmbeddedId
    private LogisticId id;

    public LogisticId getId() {
        return id;
    }

    public void setId(LogisticId id) {
        this.id = id;
    }
}