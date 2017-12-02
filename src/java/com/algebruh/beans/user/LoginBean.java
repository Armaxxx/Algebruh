/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algebruh.beans.user;

import com.algebruh.common.utils.UserType;
import entity.HibernateUtil;
import entity.User;
import java.io.Serializable;
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
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    
    private String username;
    private String password;
    private FacesContext fc;
    private HttpServletRequest request;
    private FacesMessage fm;
    
    public LoginBean() {
    }
    
    public String validate(){
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        hibernateSession.beginTransaction();
        fc= FacesContext.getCurrentInstance();
        
        request = (HttpServletRequest)fc.getExternalContext().getRequest();
        if(username != null && password != null && !username.equals("") && !password.equals("")){
            User user = (User) hibernateSession.createQuery("from User where username = '" + username + "' and password = '" + password + "'").uniqueResult();
            if(user != null){
                UserType type = UserType.UNKNOWN;
                
                if(user.getStudent() != null){
                    type = UserType.STUDENT;
                }else if(user.getTeacher() != null){
                    type = UserType.TEACHER;
                }
                else if(user.getAdministrator() != null){
                    type = UserType.ADMINISTRATOR;
                }
                
                if(type == UserType.UNKNOWN){
                    fm = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Hay un error en su cuenta. Contacte al administrador", null);
                    fc.addMessage(null, fm);
                    return "/user/login.xhtml";
                }else{
                    HttpSession httpSession = request.getSession();
                    httpSession.setAttribute("username", username);
                    httpSession.setAttribute("type", type.name());
                    fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Inicio de sesión correcto", null);
                    fc.addMessage(null, fm);
                    
                    return "/" + type.name().toLowerCase() + "/home";
                }
            }
        }
        
        fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre de usuario o contraseña incorrectos", null);
        fc.addMessage(null, fm);
        return "/user/login";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
