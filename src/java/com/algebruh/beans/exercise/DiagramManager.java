/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algebruh.beans.exercise;

import entity.Diagram;
import entity.Exercise;
import entity.HibernateUtil;
import entity.Student;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Demis
 */
@Named(value = "diagramManager")
@SessionScoped
public class DiagramManager implements Serializable {
    FacesContext fc;
    HttpServletRequest request;
    HttpSession httpSession;
    Session hibernateSession;
    private FacesMessage fm;
    
    private int iduser;
    private int idexercise;
    private Exercise exercise;
    private String[] eqElements;
    private String[] solution;
    private int exerciseType;
    private String rawEquation;
    private String rawSolution;
    private String jsondata;
    /**
     * Creates a new instance of DiagramManager
     */
    public DiagramManager() {
        fc = FacesContext.getCurrentInstance();
        request = (HttpServletRequest) fc.getExternalContext().getRequest();
        httpSession = request.getSession();
        iduser = (int) httpSession.getAttribute("iduser");
    }
    
    public String solve(int idexercise){
        this.idexercise = idexercise;
        hibernateSession = HibernateUtil.getSessionFactory().openSession();
        exercise = (Exercise) hibernateSession.load(Exercise.class, idexercise);
        eqElements = exercise.getEquation().split(";");
        solution = exercise.getSolution().split(";");
        hibernateSession.close();
        switch(exercise.getEqtype()){
            case 1:
                return "diagram/solve";
            case 2:
                return "diagram/substitute";
            case 3:
                return "diagram/expand";
            case 4:
                return "diagram/factor";
        }
        fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Función aún no disponible", null);
        fc.addMessage(null, fm);
        return "exercises";
    }
    
    public String show(int iddiagram){
        hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Diagram d = (Diagram) hibernateSession.load(Diagram.class, iddiagram);
        rawEquation = d.getExercise().getEquation();
        rawSolution = d.getExercise().getSolution();
        jsondata = d.getSerial();
        exerciseType = d.getExercise().getEqtype();
        return "diagram/show";
    }
    
    public String save(){        
        hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Student student = (Student) hibernateSession.load(Student.class, iduser);
        Transaction t = hibernateSession.beginTransaction();
        Diagram d = new Diagram(exercise, student, jsondata);
        hibernateSession.save(d);
        t.commit();
        hibernateSession.close();
        
        fc = FacesContext.getCurrentInstance();
        fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Diagrama guardado correctamente", null);
        fc.addMessage(null, fm);
        return "/student/home";
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }
    
    public int getIdexercise() {
        return idexercise;
    }

    public void setIdexercise(int idexercise) {
        this.idexercise = idexercise;
    }

    public String[] getEqElements() {
        return eqElements;
    }

    public void setEqElements(String[] eqElements) {
        this.eqElements = eqElements;
    }

    public String[] getSolution() {
        return solution;
    }

    public void setSolution(String[] solution) {
        this.solution = solution;
    }

    public String getRawEquation() {
        return rawEquation;
    }

    public void setRawEquation(String rawEquation) {
        this.rawEquation = rawEquation;
    }

    public String getRawSolution() {
        return rawSolution;
    }

    public void setRawSolution(String rawSolution) {
        this.rawSolution = rawSolution;
    }

    public int getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(int exerciseType) {
        this.exerciseType = exerciseType;
    }
    
    public String getJsondata() {
        return jsondata;
    }

    public void setJsondata(String jsondata) {
        this.jsondata = jsondata;
    }
}
