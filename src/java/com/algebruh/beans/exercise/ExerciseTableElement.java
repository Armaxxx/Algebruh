/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algebruh.beans.exercise;

/**
 *
 * @author Demis
 */
public class ExerciseTableElement {
    private int idexercise;
    private String type;
    private String equation;
    private boolean solved;
    private boolean evaluated;
    private String status;

    public ExerciseTableElement() {
    }

    public ExerciseTableElement(int idexercise, String type, String equation, boolean solved, boolean evaluated, String status) {
        this.idexercise = idexercise;
        this.type = type;
        this.equation = equation;
        this.solved = solved;
        this.evaluated = evaluated;
        this.status = status;
    }

    public int getIdexercise() {
        return idexercise;
    }

    public void setIdexercise(int idexercise) {
        this.idexercise = idexercise;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }
    
    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
