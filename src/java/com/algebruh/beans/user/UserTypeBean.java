/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algebruh.beans.user;

import com.algebruh.common.utils.UserType;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Demis
 */
@Named(value = "userTypeBean")
@ApplicationScoped
public class UserTypeBean {

    private final String STUDENT = UserType.STUDENT.name();
    private final String TEACHER = UserType.TEACHER.name();
    private final String ADMINISTRATOR = UserType.ADMINISTRATOR.name();
    private final String UNKNOWN = UserType.UNKNOWN.name();
    public UserTypeBean() {
    }

    public String getSTUDENT() {
        return STUDENT;
    }
    
    public String getTEACHER() {
        return TEACHER;
    }
    
    public String getADMINISTRATOR() {
        return ADMINISTRATOR;
    }
    
    public String getUNKNOWN() {
        return UNKNOWN;
    }
}
