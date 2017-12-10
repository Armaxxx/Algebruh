package com.algebruh.beans.user;

import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named(value = "sessionBean")
@SessionScoped
public class SessionBean implements Serializable {
    private int iduser;
    private String username;
    private String names;
    private String surnames;
    private String type;
    private FacesContext fc;
    private HttpServletRequest request;
    private FacesMessage fm;
    private HttpSession httpSession;
    
    public SessionBean() {
        fc = FacesContext.getCurrentInstance();
        request = (HttpServletRequest)fc.getExternalContext().getRequest();
        httpSession = request.getSession();
    }
    
    public String logout(){
        fc = FacesContext.getCurrentInstance();
        request = (HttpServletRequest)fc.getExternalContext().getRequest();
        httpSession.removeAttribute("iduser");
        httpSession.removeAttribute("username");
        httpSession.removeAttribute("type");
        httpSession.removeAttribute("names");
        httpSession.removeAttribute("surnames");
        httpSession.invalidate();
        return "/user/login";
    }
    
    public void validateSession(ComponentSystemEvent event) throws IOException{
        String typeRequired = (String) event.getComponent().getAttributes().get("typeRequired");
        fc = FacesContext.getCurrentInstance();
        createSession();
        if(username == null){
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor, inicia sesión", null);
            fc.addMessage(null, fm);
            logout();
            fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "/user/login");
        }
        else if(type == null || !type.equals(typeRequired)){
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tienes permiso para acceder a esa página", null);
            fc.addMessage(null, fm);
            logout();
            fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "/" + type.toLowerCase() + "/home");
        }
    }
    
    public void createSession(){
        iduser = Integer.parseInt((String) httpSession.getAttribute("iduser"));
        username = (String) httpSession.getAttribute("username");
        type = (String) httpSession.getAttribute("type");
        names = (String) httpSession.getAttribute("names");
        surnames = (String) httpSession.getAttribute("surnames");
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }
    
}
