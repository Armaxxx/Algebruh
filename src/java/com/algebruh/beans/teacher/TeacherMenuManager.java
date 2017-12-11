package com.algebruh.beans.teacher;

import entity.HibernateUtil;
import entity.Schoolgroup;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named(value = "teacherMenuManager")
@SessionScoped
public class TeacherMenuManager implements Serializable {
    private final FacesContext fc;
    private final HttpServletRequest request;
    private FacesMessage fm;
    private final Session hibernateSession;
    private Transaction t;
    private HttpSession httpSession;
    
    private int iduser;
    private List<Schoolgroup> groupList;
    
    public TeacherMenuManager() {
        hibernateSession = HibernateUtil.getSessionFactory().openSession();
        fc= FacesContext.getCurrentInstance();
        request = (HttpServletRequest)fc.getExternalContext().getRequest();
        httpSession = request.getSession();
    }
    
    public void buildGroupList(){
        iduser = (int) httpSession.getAttribute("iduser");
        
        Query groups = hibernateSession.createQuery("From Schoolgroup where idteacher="+iduser);
        groupList = groups.list();
    }
    public String editGroup(int id){
        httpSession.setAttribute("id", id);
        return "/teacher/teachergroup.xhtml";
    }
    
    public List<Schoolgroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Schoolgroup> groupList) {
        this.groupList = groupList;
    }
}
