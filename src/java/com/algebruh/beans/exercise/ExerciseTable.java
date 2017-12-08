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
    private int idExercise;
    private String equation;
    private String status;

    public ExerciseTable() {
    }

    public ExerciseTable(int idExercise, String equation, String status) {
        this.idExercise = idExercise;
        this.equation = equation;
        this.status = status;
    }

    public int getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(int idExercise) {
        this.idExercise = idExercise;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
