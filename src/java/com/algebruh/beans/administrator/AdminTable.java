/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algebruh.beans.administrator;

/**
 *
 * @author Armax
 */
public class AdminTable {
    private String firstnames;
    private String surnames;
    private String username;
    private String password;
    private String type;
    private Boolean enable;
    public AdminTable() {
    }

    public AdminTable(String firstnames, String surnames, String username, String password, String type, Boolean enable) {
        this.firstnames = firstnames;
        this.surnames = surnames;
        this.username = username;
        this.password = password;
        this.type = type;
        this.enable = enable;
    }
    
    public String getFirstnames() {
        return firstnames;
    }

    public void setFirstnames(String firstnames) {
        this.firstnames = firstnames;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
    
}
