/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algebruh.beans.student;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Demis
 */
@Named(value = "studentManager")
@RequestScoped
public class StudentManager {
    /**
     * Creates a new instance of StudentManager
     */
    public StudentManager() {
    }   
    
}
