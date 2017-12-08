package com.algebruh.beans.administrator;
import com.algebruh.common.utils.UserType;
import entity.Administrator;
import entity.HibernateUtil;
import entity.Student;
import entity.Teacher;
import entity.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import static java.lang.Boolean.TRUE;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
@Named(value = "adminManager")
@SessionScoped
public class AdminManager implements Serializable {
    private FacesContext fc;
    private HttpServletRequest request;
    private FacesMessage fm;
    private Session hibernateSession;
    private Transaction t;
    
    private AdminTable  adminSet,newUser;
    private List<AdminTable> adminTable;
    private List<User> userList;
    private UserType type;
    private Boolean isAdding;
    private Boolean showUser;
    
    public AdminManager() {
        showUser = false;
        newUser = new AdminTable();
        adminTable = new ArrayList<>();
        isAdding = false;
        hibernateSession = HibernateUtil.getSessionFactory().openSession();
        fc= FacesContext.getCurrentInstance();
        request = (HttpServletRequest)fc.getExternalContext().getRequest();
        Query users = hibernateSession.createQuery("from User");
        userList = users.list();
        for (User user : userList) {
            if(user != null){
                type = UserType.UNKNOWN;
                if(user.getStudent() != null){
                    type = UserType.STUDENT;
                }else if(user.getTeacher() != null){
                    type = UserType.TEACHER;
                }
                else if(user.getAdministrator() != null){
                    type = UserType.ADMINISTRATOR;
                }
                adminSet = new AdminTable(user.getIduser(),user.getFirstnames(), user.getSurnames(), user.getUsername(), user.getPassword(), type.name().toLowerCase(), TRUE);
                adminTable.add(adminSet);
            }
        }
    }
    
    public void resetAdminTable(){
        adminTable.clear();
        Query users = hibernateSession.createQuery("from User");
        userList = users.list();
        for (User user : userList) {
            if(user != null){
                type = UserType.UNKNOWN;
                if(user.getStudent() != null){
                    type = UserType.STUDENT;
                }else if(user.getTeacher() != null){
                    type = UserType.TEACHER;
                }
                else if(user.getAdministrator() != null){
                    type = UserType.ADMINISTRATOR;
                }
                adminSet = new AdminTable(user.getIduser(),user.getFirstnames(), user.getSurnames(), user.getUsername(), user.getPassword(), type.name().toLowerCase(), TRUE);
                adminTable.add(adminSet);
            }
        }
    }
    public void deleteUser(int iduser){
        t= hibernateSession.beginTransaction();
        User user = (User) hibernateSession.get(User.class, iduser);
        hibernateSession.delete(user);
        t.commit();
        resetAdminTable();
    }
    public void editUser(AdminTable at){
        int index;
        index = adminTable.indexOf(at);
        resetAdminTable();
        System.out.println(index);
        at.setEnable(false);
        adminTable.set(index, at);
    }
    public void cancelEdit(){
        resetAdminTable(); 
    }
    public void saveNewUser(AdminTable at){
        t= hibernateSession.beginTransaction();
        User user = new User();
        user.setFirstnames(at.getFirstnames());
        user.setSurnames(at.getSurnames());
        user.setPassword(at.getPassword());
        user.setUsername(at.getUsername());
        switch(at.getType()){
            case "1":
                Administrator admin = new Administrator(user);
                user.setAdministrator(admin);
                hibernateSession.save(admin);
                break;
            case "2":
                type = UserType.TEACHER;
                Teacher teach = new Teacher(user);
                user.setTeacher(teach);
                hibernateSession.save(teach);
                break;
            case "3":
                type = UserType.STUDENT;
                Student student = new Student(user);
                user.setStudent(student);
                hibernateSession.save(student);
                break;
            case "4":
                type = UserType.UNKNOWN;
                break;
            default:
                type = UserType.UNKNOWN;
                break;
        }
        hibernateSession.save(user);
        t.commit();
        resetAdminTable();
        isAdding = false;
    }
    public void saveUser(AdminTable at){
        t= hibernateSession.beginTransaction();
        User user = (User) hibernateSession.get(User.class, at.getIduser());
        deleteUserType(user);
        user.setFirstnames(at.getFirstnames());
        user.setSurnames(at.getSurnames());
        user.setPassword(at.getPassword());
        user.setUsername(at.getUsername());
        switch(at.getType()){
            case "1":
                Administrator admin = new Administrator(user);
                user.setAdministrator(admin);
                hibernateSession.save(admin);
                break;
            case "2":
                type = UserType.TEACHER;
                Teacher teach = new Teacher(user);
                user.setTeacher(teach);
                hibernateSession.save(teach);
                break;
            case "3":
                type = UserType.STUDENT;
                Student student = new Student(user);
                user.setStudent(student);
                hibernateSession.save(student);
                break;
            case "4":
                type = UserType.UNKNOWN;
                break;
            default:
                type = UserType.UNKNOWN;
                break;
        }
        hibernateSession.save(user);
        t.commit();
        resetAdminTable();
    }
    
    public void deleteUserType(User user){
        Boolean hasType = true;
        while(hasType){
            if(user != null){
                type = UserType.UNKNOWN;
                if(user.getStudent() != null){
                    hibernateSession.delete(user.getStudent());
                    user.setStudent(null);
                    type = UserType.STUDENT;
                }else if(user.getTeacher() != null){
                    type = UserType.TEACHER;
                    hibernateSession.delete(user.getTeacher());
                    user.setTeacher(null);
                }
                else if(user.getAdministrator() != null){
                    hibernateSession.delete(user.getAdministrator());
                    user.setAdministrator(null);
                    type = UserType.ADMINISTRATOR;
                }
                else{
                hasType = false;
                }
            }
        }
    }
        
    public List<AdminTable> getAdminTable() {
        return adminTable;
    }

    public void setAdminTable(List<AdminTable> adminTable) {
        this.adminTable = adminTable;
    }

    public AdminTable getNewUser() {
        return newUser;
    }

    public void setNewUser(AdminTable newUser) {
        this.newUser = newUser;
    }
    
    public Boolean getIsAdding() {
        return isAdding;
    }

    public void setIsAdding(Boolean isAdding) {
        this.isAdding = isAdding;
    }

    public Boolean getShowUser() {
        return showUser;
    }

    public void setShowUser(Boolean showUser) {
        this.showUser = showUser;
    }
}
