/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algebruh.beans.exercise;

import entity.Exercise;
import entity.HibernateUtil;
import entity.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;

/**
 *
 * @author Demis
 */
@Named(value = "exerciseManager")
@RequestScoped
public class ExerciseManager{

    private FacesContext fc;
    private HttpServletRequest request;
    private Session hibernateSession;
    
    private int iduser;
    private List<ExerciseTable> exerciseTable;
    
    public ExerciseManager() {
    }
    
    public String showGroupExercises() {
        exerciseTable = new ArrayList<>();
        hibernateSession = HibernateUtil.getSessionFactory().openSession();
        System.out.println("showGroupExercises1: iduser = " + iduser);
        Student alumno = (Student) hibernateSession.load(Student.class, iduser);
        System.out.println("Obtieniendo ejercicios para el alumno : [" + alumno.getIduser() + "] " + alumno.getUser().getFirstnames());
        Set<Exercise> GroupEx = alumno.getSchoolgroup().getExercises();
        
        for(Exercise ex : GroupEx){
            ExerciseTable et = new ExerciseTable();
            
            et.setIdExercise(ex.getIdexercise());
            et.setEquation(ex.getEquation());
            et.setStatus("Que me ves culebra?");
            
            exerciseTable.add(et);
        }
        
        return "exercises";
    }   

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }
    
    public List<ExerciseTable> getExerciseTable() {
        return exerciseTable;
    }

    public void setExerciseTable(List<ExerciseTable> exerciseTable) {
        this.exerciseTable = exerciseTable;
    }
}
