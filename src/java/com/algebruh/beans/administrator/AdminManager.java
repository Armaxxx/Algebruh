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
    private AdminTable  adminSet;
    private List<AdminTable> adminTable;
    private Transaction t;
    private List<User> userList;
    private UserType type;
    
    public AdminManager() {
        adminTable = new ArrayList<>();
        
        hibernateSession = HibernateUtil.getSessionFactory().openSession();
        t= hibernateSession.beginTransaction();
        fc= FacesContext.getCurrentInstance();
        request = (HttpServletRequest)fc.getExternalContext().getRequest();
        Query users = hibernateSession.createQuery("from User");
        userList = users.list();
        System.out.println("query completa " +userList.size());
        for (User user : userList) {
            System.out.println("entramos a for "+userList.indexOf(user));
            if(user != null){
                type = UserType.UNKNOWN;
                System.out.println("Usuario Nulo");
                if(user.getStudent() != null){
                    System.out.println("Usuario Estudiante");
                    type = UserType.STUDENT;
                }else if(user.getTeacher() != null){
                    System.out.println("Usuario Maestro");
                    type = UserType.TEACHER;
                }
                else if(user.getAdministrator() != null){
                    System.out.println("Usuario Admin");
                    type = UserType.ADMINISTRATOR;
                }
                System.out.println(user.getFirstnames());
                System.out.println(user.getSurnames());
                System.out.println(user.getUsername());
                System.out.println(user.getPassword());
                System.out.println(type.name().toLowerCase());
                System.out.println("Empezamos el adminset");
                adminSet = new AdminTable(user.getIduser(),user.getFirstnames(), user.getSurnames(), user.getUsername(), user.getPassword(), type.name().toLowerCase(), TRUE);
                System.out.println("Terminamos el adminset");
                adminTable.add(adminSet);
                System.out.println("agregamos el adminset");
            }
        }
    }
    
    public void resetAdminTable(){
        System.out.println("RESET");
        adminTable.clear();
        Query users = hibernateSession.createQuery("from User");
        userList = users.list();
        System.out.println("query completa " +userList.size());
        for (User user : userList) {
            System.out.println("entramos a for "+userList.indexOf(user));
            if(user != null){
                type = UserType.UNKNOWN;
                System.out.println("Usuario Nulo");
                if(user.getStudent() != null){
                    System.out.println("Usuario Estudiante");
                    type = UserType.STUDENT;
                }else if(user.getTeacher() != null){
                    System.out.println("Usuario Maestro");
                    type = UserType.TEACHER;
                }
                else if(user.getAdministrator() != null){
                    System.out.println("Usuario Admin");
                    type = UserType.ADMINISTRATOR;
                }
                System.out.println(user.getFirstnames());
                System.out.println(user.getSurnames());
                System.out.println(user.getUsername());
                System.out.println(user.getPassword());
                System.out.println(type.name().toLowerCase());
                System.out.println("Empezamos el adminset");
                adminSet = new AdminTable(user.getIduser(),user.getFirstnames(), user.getSurnames(), user.getUsername(), user.getPassword(), type.name().toLowerCase(), TRUE);
                System.out.println("Terminamos el adminset");
                adminTable.add(adminSet);
                System.out.println("agregamos el adminset");
            }
        }
    }
    public void deleteUser(int iduser){
        System.out.println("entramos delete");
        User user = (User) hibernateSession.get(User.class, iduser);
        System.out.println("conseguimos al usuario "+user.getFirstnames());
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
    public void saveUser(AdminTable at){
        System.out.println("entramos al SAVE");
        System.out.println("Nombre editado: "+at.getFirstnames());
        System.out.println("apellido editado: "+at.getSurnames());
        System.out.println("usuario editado: "+at.getUsername());
        System.out.println("contra editado: "+at.getPassword());
        System.out.println("tipo editado: "+at.getType());
        User user = (User) hibernateSession.get(User.class, at.getIduser());
        System.out.println("conseguimos al usuario "+user.getFirstnames());
        user.setFirstnames(at.getFirstnames());
        user.setSurnames(at.getSurnames());
        user.setPassword(at.getPassword());
        user.setUsername(at.getUsername());
        System.out.println("El usuario es ahora "+user.getFirstnames());
        hibernateSession.save(user);
        user.setFirstnames(at.getFirstnames());
        user.setSurnames(at.getSurnames());
        user.setPassword(at.getPassword());
        user.setUsername(at.getUsername());
        System.out.println("Usuario Guardado");
        System.out.println("El usuario es ahora "+user.getFirstnames());
        System.out.println("Es de tipo "+at.getType());
        switch(at.getType()){
            case "1":
                System.out.println("El usuario es Administrador");
                Administrator admin = new Administrator(user);
                hibernateSession.save(admin);
                System.out.println("Tipo de usuario guardado");
                break;
            case "2":
                System.out.println("El usuario es Profesor");
                type = UserType.TEACHER;
                Teacher teach = new Teacher(user);
                hibernateSession.save(teach);
                System.out.println("Tipo de usuario guardado");
                break;
            case "3":
                System.out.println("El usuario es Estudiante");
                type = UserType.STUDENT;
                Student student = new Student(user);
                hibernateSession.save(student);
                System.out.println("Tipo de usuario guardado");
                break;
            case "4":
                type = UserType.UNKNOWN;
                break;
            default:
                type = UserType.UNKNOWN;
                break;
        }
        t.commit();
        resetAdminTable();
    }
    public List<AdminTable> getAdminTable() {
        return adminTable;
    }

    public void setAdminTable(List<AdminTable> adminTable) {
        this.adminTable = adminTable;
    }
}
