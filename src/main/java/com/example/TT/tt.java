package com.example.TT;

import ValidContent_Visitor.Valid_Visitable;
import ValidContent_Visitor.Visitor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class tt extends Valid_Visitable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int tid ;
    public String Total ;
    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }


    public boolean accept(Visitor visitor) { return visitor.visit(this); }}

