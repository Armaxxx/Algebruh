/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algebruh.beans.student;

import com.algebruh.beans.exercise.ExerciseTableElement;
import entity.Exercise;
import entity.Student;
import entity.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
