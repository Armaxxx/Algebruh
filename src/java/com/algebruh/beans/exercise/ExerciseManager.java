/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algebruh.beans.exercise;

import com.algebruh.common.utils.EquationUtil;
import entity.Diagram;
import entity.Evaluation;
import entity.Exercise;
import entity.HibernateUtil;
import entity.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;

/**
 *
 * @author Demis
 */
@Named(value = "exerciseManager")
@SessionScoped
public class ExerciseManager implements Serializable {

    private FacesContext fc;
    private FacesMessage fm;
    private HttpServletRequest request;
    private Session hibernateSession;

    private int iduser;
    private List<ExerciseTableElement> exerciseTable;
    
    private final static String[] EXERCISE_TYPE = {"", "Despejar", "Sustituir", "Expander", "Factorizar"};

    public ExerciseManager() {
        fc = FacesContext.getCurrentInstance();
        request = (HttpServletRequest) fc.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession();
        String strId = (String) httpSession.getAttribute("iduser");
        iduser = Integer.parseInt(strId);
    }

    public String showGroupExercises() {
        exerciseTable = new ArrayList<>();
        hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Student alumno = (Student) hibernateSession.load(Student.class, iduser);
        Set<Exercise> GroupEx = alumno.getSchoolgroup().getExercises();

        for (Exercise ex : GroupEx) {
            ExerciseTableElement et = new ExerciseTableElement();
            int eqType = ex.getEqtype();
            et.setType(EXERCISE_TYPE[eqType]);
            //Operaciones para mostrar ecuacion
            et.setIdexercise(ex.getIdexercise());
            String finalEquation = EquationUtil.parseEquation(ex.getEquation(), eqType);
            et.setEquation(finalEquation);
            
            //Operaciones para estado
            Set<Diagram> exerciseDiagrams = ex.getDiagrams();
            boolean solved = false;
            boolean evaluated = false;
            String status = "No entregado";
            for(Diagram d : exerciseDiagrams){
                if(d.getStudent().getIduser() == iduser){
                    solved = true;
                    Evaluation ev = d.getEvaluation();
                    if(ev != null){
                        evaluated = true;
                        status = "Calificado con " + ev.getGrade();
                    }
                    else{
                        status = "No calificado";
                    }
                    break;
                }
            }   
            et.setSolved(solved);
            et.setEvaluated(evaluated);
            et.setStatus(status);
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

    public List<ExerciseTableElement> getExerciseTable() {
        return exerciseTable;
    }

    public void setExerciseTable(List<ExerciseTableElement> exerciseTable) {
        this.exerciseTable = exerciseTable;
    }    
}
