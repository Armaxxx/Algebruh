package com.algebruh.beans.administrator;

import com.algebruh.common.utils.GroupTable;
import entity.HibernateUtil;
import entity.Schoolgroup;
import entity.Student;
import entity.Teacher;
import entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named(value = "adminManagerGroup")
@SessionScoped
public class AdminManagerGroup implements Serializable{
    private final FacesContext fc;
    private final HttpServletRequest request;
    private FacesMessage fm;
    private final Session hibernateSession;
    private Transaction t;
    
    private Boolean showGroupMenu;
    private Boolean IsAddingtoGroup;
    private List<Schoolgroup> groupList;
    private List<GroupTable> groupTable;
    private GroupTable groupRow;
    private List<Teacher> teacherList;
    public AdminManagerGroup() {
        hibernateSession = HibernateUtil.getSessionFactory().openSession();
        fc= FacesContext.getCurrentInstance();
        request = (HttpServletRequest)fc.getExternalContext().getRequest();
        Query groups = hibernateSession.createQuery("from Schoolgroup");
        groupList = groups.list();
        showGroupMenu = false;
        IsAddingtoGroup = false;
        groupTable = new ArrayList<>();
        Query teachers = hibernateSession.createQuery("from Teacher");
        teacherList = teachers.list();
    }
    
    public void openGroupMenu(){
        System.out.println("AbrirMenu");
        setShowGroupMenu(true);
        buildGroupTable();
    }
    public void closeGroupMenu(){
        setShowGroupMenu(false);
    }
    public void buildGroupTable(){
        groupTable.clear();
        System.out.println("Entra construccion de tabla grupo");
        System.out.println("Query grupos");
        Query groups = hibernateSession.createQuery("from Schoolgroup");
        System.out.println("Query completada");
        groupList = groups.list();
        System.out.println("Grupos "+groupList.size());
        Teacher teach;
        List<Student> students = new ArrayList<>();
        List<User> users = new ArrayList<>();
        System.out.println("preFor");
        for(Schoolgroup group :groupList){
            users.clear();
            students.clear();
            System.out.println("variables limpias");
            System.out.println("Entra For grupos");
            if(!group.getStudents().isEmpty()){
                System.out.println("Tiene "+group.getStudents().size()+" usuarios");
                System.out.println("Tiene estudiantes");
                students.addAll(group.getStudents());
                System.out.println("Estudiantes "+students.size()+" añadidos a lista");
                for(Student student:students){
                    System.out.println("Entra FOR lista de estudiantes");
                    if(student.getUser() != null){
                        System.out.println("Estudiante tiene usuario");
                        users.add(student.getUser());
                        System.out.println("Estudiante "+student.getUser().getFirstnames()+" añadido a usuarios");
                    }
                }
                System.out.println("Sale for estudiantes");
            }
            teach = group.getTeacher();
            System.out.println("Se crea la ROW con:");
            System.out.println("Grupo: "+group.getName());
            System.out.println("Profesor: "+group.getTeacher().getUser().getFirstnames());
            if(users.isEmpty())
                groupRow = new GroupTable(group, teach);
            else {
                System.out.println("Tiene usuarios");
                groupRow = new GroupTable(group, teach, users);
            }
            System.out.println("ROW creado");
            groupTable.add(groupRow);
            System.out.println("ROW añadido");
        }
    }
    public void editGroup(GroupTable group){
        System.out.println("Entra edit");
        int index;
        index = groupTable.indexOf(group);
        System.out.println("Conseguimos el grupo "+group.getGroup().getName()+" de index "+index);
        System.out.println("preconstruccion edit");
        buildGroupTable();
        System.out.println("postconstruccion edit");
        group.setShow(false);
        groupTable.set(index, group);
        System.out.println("Lo cambiamos en la tabla");
    }
    public void saveEditGroup(GroupTable group){
        Transaction trans = hibernateSession.beginTransaction();
        Schoolgroup editedGroup = (Schoolgroup)hibernateSession.get(Schoolgroup.class, group.getGroup().getIdgroup());
        editedGroup = group.getGroup();
        hibernateSession.saveOrUpdate(editedGroup);
        trans.commit();
        buildGroupTable();
    }
    public void deleteGroup(GroupTable groupPack){
        Schoolgroup group;
        Transaction trans;
        buildGroupTable();
        group = groupPack.getGroup();
        trans = hibernateSession.beginTransaction();
        hibernateSession.delete(group);
        trans.commit();
        buildGroupTable();
    }
    public Boolean getShowGroupMenu() {
        return showGroupMenu;
    }

    public void setShowGroupMenu(Boolean showGroupMenu) {
        this.showGroupMenu = showGroupMenu;
    }

    public Boolean getIsAddingtoGroup() {
        return IsAddingtoGroup;
    }

    public void setIsAddingtoGroup(Boolean IsAddingtoGroup) {
        this.IsAddingtoGroup = IsAddingtoGroup;
    }

    public List<GroupTable> getGroupTable() {
        return groupTable;
    }

    public void setGroupTable(List<GroupTable> groupTable) {
        this.groupTable = groupTable;
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }
    
}
