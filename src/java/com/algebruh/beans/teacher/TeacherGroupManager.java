package com.algebruh.beans.teacher;

import com.algebruh.common.utils.NewExercise;
import entity.Exercise;
import entity.HibernateUtil;
import entity.Schoolgroup;
import entity.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named(value = "teacherGroupManager")
@SessionScoped
public class TeacherGroupManager implements Serializable{
    private final FacesContext fc;
    private final HttpServletRequest request;
    private FacesMessage fm;
    private final Session hibernateSession;
    private Transaction t;
    private final HttpSession httpSession;
    
    private final int idGroup;
    private final Schoolgroup group;
    private NewExercise newExercise;
    private int newStudent;
    private List<Student> students = new ArrayList<>();
    private List<Student> allStudents = new ArrayList<>();
    private List<Exercise> exercises = new ArrayList<>();
    private boolean hasStudents;
    private boolean hasExercises;

    public TeacherGroupManager() {
        hibernateSession = HibernateUtil.getSessionFactory().openSession();
        fc= FacesContext.getCurrentInstance();
        request = (HttpServletRequest)fc.getExternalContext().getRequest();
        httpSession = request.getSession();
        
        newExercise = new NewExercise();
        idGroup = (int)httpSession.getAttribute("id");
        group = (Schoolgroup)hibernateSession.get(Schoolgroup.class, idGroup);
        hasStudents = students.addAll(group.getStudents());
        hasExercises = exercises.addAll(group.getExercises());
        Query query = hibernateSession.createQuery("From Student");
        allStudents = query.list();
        allStudents.removeAll(students);
    }
    public void saveNewExercise(NewExercise newExercise){
        System.out.println("Entramos save exercise");
        System.out.println("El tipo de ejercicio es "+newExercise.getEqtype());
        Exercise exercise = new Exercise();
        String equation;
        String solution;
        switch(newExercise.getEqtype()){
            case 1:
                System.out.println("Es solve");
                equation = newExercise.getA()+";"+newExercise.getB()+";"+newExercise.getC()+";"+newExercise.getD();
                solution = Integer.toString(newExercise.getE());
                System.out.println("Solucion = "+solution);
                exercise.setEquation(equation);
                exercise.setEqtype(newExercise.getEqtype());
                exercise.setSolution(solution);
                break;
            case 2:
                System.out.println("Es Substitute");
                equation = newExercise.getA()+";"+newExercise.getB()+";"+newExercise.getC();
                solution = Integer.toString(newExercise.getD());
                System.out.println("Solucion = "+solution);
                exercise.setEquation(equation);
                exercise.setEqtype(newExercise.getEqtype());
                exercise.setSolution(solution);
                break;
            case 3:
                System.out.println("Es Expand");
                equation = newExercise.getA()+";"+newExercise.getB()+";"+newExercise.getC()+";"+newExercise.getD();
                solution = newExercise.getE()+";"+newExercise.getF()+";"+newExercise.getG();
                System.out.println("Solucion = "+solution);
                exercise.setEquation(equation);
                exercise.setEqtype(newExercise.getEqtype());
                exercise.setSolution(solution);
                break;
            case 4:
                System.out.println("Es Factor");
                equation = newExercise.getA()+";"+newExercise.getB()+";"+newExercise.getC();
                solution = newExercise.getD()+";"+newExercise.getE()+";"+newExercise.getF()+";"+newExercise.getG();
                System.out.println("Solucion = "+solution);
                exercise.setEquation(equation);
                exercise.setEqtype(newExercise.getEqtype());
                exercise.setSolution(solution);
                break;
            default:
                break;
        }
        t = hibernateSession.beginTransaction();
        System.out.println("se crea exercise nuevo "+exercise.getEqtype()+" de "+exercise.getEquation()+" con solucion "+exercise.getSolution());
        System.out.println("Metemos el grupo a exercise");
        exercise.getSchoolgroups().add(group);
        System.out.println("Lo guardamos");
        hibernateSession.save(exercise);
        group.getExercises().add(exercise);
        System.out.println("se agrega al grupo");
        hibernateSession.saveOrUpdate(group);
        System.out.println("se guarda el grupo");
        exercises.add(exercise);
        t.commit();
    }
    public void eqTypeSelect(int x){
        switch(x){
            case 1:
                newExercise.setIsSolve(true);
                newExercise.setIsSubstitute(false);
                newExercise.setIsExpand(false);
                newExercise.setIsFactor(false);
                break;
            case 2:
                newExercise.setIsSolve(false);
                newExercise.setIsSubstitute(true);
                newExercise.setIsExpand(false);
                newExercise.setIsFactor(false);
                break;
            case 3:
                newExercise.setIsSolve(false);
                newExercise.setIsSubstitute(false);
                newExercise.setIsExpand(true);
                newExercise.setIsFactor(false);
                break;
            case 4:
                newExercise.setIsSolve(false);
                newExercise.setIsSubstitute(false);
                newExercise.setIsExpand(false);
                newExercise.setIsFactor(true);
                break;
            default:
                newExercise.setIsSolve(false);
                newExercise.setIsSubstitute(false);
                newExercise.setIsExpand(false);
                newExercise.setIsFactor(false);
                break;
        }
    }
    public void saveNewStudent(int newStudent){
        System.out.println("Entra Save Student");
        System.out.println("Id de usuario "+newStudent);
        Student student = (Student)hibernateSession.get(Student.class,newStudent);
        t = hibernateSession.beginTransaction();
        System.out.println("Obtenemos a "+student.getUser().getFirstnames());
        group.getStudents().add(student);
        System.out.println("Agregamos al estudiante al grupo");
        hibernateSession.saveOrUpdate(group);
        System.out.println("Guardamos grupo");
        student.setSchoolgroup(group);
        System.out.println("Le asignamos grupo al estudiante");
        hibernateSession.saveOrUpdate(student);
        System.out.println("Guardamos estudiante");
        students.add(student);
        System.out.println("Agregamos al array");
        hasStudents = true;
        allStudents.remove(student);
        t.commit();
    }
    public void deleteUser(int id){
        t = hibernateSession.beginTransaction();
        Student student = (Student)hibernateSession.get(Student.class,id);
        group.getStudents().remove(student);
        student.setSchoolgroup(null);
        hibernateSession.saveOrUpdate(group);
        hibernateSession.saveOrUpdate(student);
        students.remove(student);
        if (students.isEmpty())
            hasStudents = false;
        allStudents.add(student);
        t.commit();
    }
    public void deleteExercise(int id){
        t = hibernateSession.beginTransaction();
        Exercise exercise = (Exercise)hibernateSession.get(Exercise.class,id);
        group.getExercises().remove(exercise);
        exercise.getSchoolgroups().remove(group);
        hibernateSession.saveOrUpdate(group);
        hibernateSession.saveOrUpdate(exercise);
        exercises.remove(exercise);
        if (exercises.isEmpty())
            hasExercises = false;
        t.commit();
    }
    public int getIdGroup() {
        return idGroup;
    }

    public Schoolgroup getGroup() {
        return group;
    }

    public List<Student> getStudents() {
        return students;
    }

    public boolean isHasStudents() {
        return hasStudents;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public boolean isHasExercises() {
        return hasExercises;
    }

    public NewExercise getNewExercise() {
        return newExercise;
    }

    public int getNewStudent() {
        return newStudent;
    }

    public void setNewStudent(int newStudent) {
        this.newStudent = newStudent;
    }
    
    public List<Student> getAllStudents() {
        return allStudents;
    }
    
}
