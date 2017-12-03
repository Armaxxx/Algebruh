package com.algebruh.beans.administrator;
import com.algebruh.common.utils.UserType;
import entity.HibernateUtil;
import entity.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import static java.lang.Boolean.TRUE;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Query;
import org.hibernate.Session;
@Named(value = "adminManager")
@SessionScoped
public class AdminManager implements Serializable {
    private FacesContext fc;
    private HttpServletRequest request;
    private FacesMessage fm;
    private Session hibernateSession;
    private AdminTable  adminSet;
    private List<AdminTable> adminTable;
    
    public AdminManager() {
        adminTable = new ArrayList<>();
        List<User> userList;
        UserType type;
        
        hibernateSession = HibernateUtil.getSessionFactory().openSession();
        hibernateSession.beginTransaction();
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
    public void deleteUser(int iduser){
        System.out.println("entramos delete");
        User user = (User) hibernateSession.get(User.class, iduser);
        System.out.println("conseguimos al usuario "+user.getFirstnames());
        hibernateSession.delete(user);
    }
    public void cancelEdit(int id){
        adminTable.get(id);
        
    }
    
    public List<AdminTable> getAdminTable() {
        return adminTable;
    }

    public void setAdminTable(List<AdminTable> adminTable) {
        this.adminTable = adminTable;
    }
}
