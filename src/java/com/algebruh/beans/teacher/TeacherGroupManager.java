package com.algebruh.beans.teacher;

import entity.Exercise;
import entity.HibernateUtil;
import entity.Schoolgroup;
import entity.Student;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named(value = "teacherGroupManager")
@Dependent
public class TeacherGroupManager {
    private final FacesContext fc;
    private final HttpServletRequest request;
    private FacesMessage fm;
    private final Session hibernateSession;
    private Transaction t;
    private final HttpSession httpSession;
    
    private final int idGroup;
    private final Schoolgroup group;
    private Exercise newExercise;
    private Student newStudent;
    private List<Student> students = new ArrayList<>();
    private List<Student> allStudents = new ArrayList<>();
    private List<Exercise> exercises = new ArrayList<>();
    private boolean hasStudents;
    private boolean hasExercises;
    private boolean addingExercise;
    private boolean addingStudent;

    public TeacherGroupManager() {
        hibernateSession = HibernateUtil.getSessionFactory().openSession();
        fc= FacesContext.getCurrentInstance();
        request = (HttpServletRequest)fc.getExternalContext().getRequest();
        httpSession = request.getSession();
        
        newExercise = new Exercise();
        newStudent = new Student();
        idGroup = (int)httpSession.getAttribute("id");
        group = (Schoolgroup)hibernateSession.get(Schoolgroup.class, idGroup);
        hasStudents = students.addAll(group.getStudents());
        hasExercises = exercises.addAll(group.getExercises());
        addingExercise = false;
        Query query = hibernateSession.createQuery("From Student");
        allStudents = query.list();
        allStudents.removeAll(students);
    }
    
    public void saveNewExercise(){
        Exercise exercise = new Exercise(newExercise.getEquation(), newExercise.getEqtype(), newExercise.getSolution());
        t = hibernateSession.beginTransaction();
        hibernateSession.save(exercise);
        group.getExercises().add(exercise);
        hibernateSession.saveOrUpdate(group);
        t.commit();
    }
    public void saveNewStudent(){
        t = hibernateSession.beginTransaction();
        Student student = (Student)hibernateSession.get(Student.class, newStudent.getIduser());
        group.getStudents().add(student);
        hibernateSession.save(group);
        student.setSchoolgroup(group);
        hibernateSession.saveOrUpdate(group);
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

    public boolean isAddingExercise() {
        return addingExercise;
    }

    public void setAddingExercise(boolean addingExercise) {
        this.addingExercise = addingExercise;
    }

    public Exercise getNewExercise() {
        return newExercise;
    }

    public boolean isAddingStudent() {
        return addingStudent;
    }

    public void setAddingStudent(boolean addingStudent) {
        this.addingStudent = addingStudent;
    }

    public Student getNewStudent() {
        return newStudent;
    }

    public List<Student> getAllStudents() {
        return allStudents;
    }
    
}
