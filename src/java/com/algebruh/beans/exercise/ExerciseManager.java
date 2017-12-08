/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algebruh.beans.exercise;

import com.algebruh.common.utils.EquationUtil;
import entity.Diagram;
import entity.Exercise;
import entity.HibernateUtil;
import entity.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
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
    private HttpServletRequest request;
    private Session hibernateSession;

    private int iduser;
    private List<ExerciseTable> exerciseTable;

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
            ExerciseTable et = new ExerciseTable();
            String eqType = ex.getEqtype();
            if (eqType.equals("solve")) {
                //Operaciones para mostrar ecuacion
                et.setIdexercise(ex.getIdexercise());
                String rawEquation = ex.getEquation();
                String[] eqElements = rawEquation.split(";");
                int[] eq = new int[4];
                for(int i=0; i<4; i++){
                    eq[i] = Integer.parseInt(eqElements[i]);
                }
                String finalEquation = EquationUtil.getEquation1ForView(eq);
                et.setEquation(finalEquation);
                //Operaciones para estado
                Set<Diagram> exerciseDiagrams = ex.getDiagrams();
                boolean solved = false;
                
                for(Diagram d : exerciseDiagrams){
                    if(d.getStudent().getIduser() == iduser){
                        solved = true;
                        break;
                    }
                }
                et.setSolution(ex.getSolution());
                et.setSolved(solved);
                String status = solved ? "Entregado" : "No Entregado";
                et.setStatus(status);

                exerciseTable.add(et);
            }
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
