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
public class ExerciseTable {
    private int idexercise;
    private String equation;
    private String solution;
    private boolean solved;
    private String status;

    public ExerciseTable() {
    }

    public ExerciseTable(int idexercise, String equation, String solution, boolean solved, String status) {
        this.idexercise = idexercise;
        this.equation = equation;
        this.solution = solution;
        this.solved = solved;
        this.status = status;
    }

    public int getIdexercise() {
        return idexercise;
    }

    public void setIdexercise(int idexercise) {
        this.idexercise = idexercise;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
    
    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
