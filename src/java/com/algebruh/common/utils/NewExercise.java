/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algebruh.common.utils;

/**
 *
 * @author Armax
 */
public class NewExercise {
    private String equation,solution;
    private int eqtype;

    public NewExercise(String equation, int eqtype, String solution) {
        this.equation = equation;
        this.eqtype = eqtype;
        this.solution = solution;
    }

    public NewExercise() {
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public int getEqtype() {
        return eqtype;
    }

    public void setEqtype(int eqtype) {
        this.eqtype = eqtype;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
    
}
