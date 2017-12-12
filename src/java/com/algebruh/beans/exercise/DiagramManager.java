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
    private String jsondata;
    /**
     * Creates a new instance of DiagramManager
     */
    public DiagramManager() {
        fc = FacesContext.getCurrentInstance();
        request = (HttpServletRequest) fc.getExternalContext().getRequest();
        httpSession = request.getSession();
        String strId = (String) httpSession.getAttribute("iduser");
        iduser = Integer.parseInt(strId);
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
        }
        fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Función aún no disponible", null);
        fc.addMessage(null, fm);
        return "exercises";
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
        return "../home";
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

    public String getJsondata() {
        return jsondata;
    }

    public void setJsondata(String jsondata) {
        this.jsondata = jsondata;
    }
}
