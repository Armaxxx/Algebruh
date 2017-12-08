package com.algebruh.common.utils;

import entity.Schoolgroup;
import entity.Teacher;
import entity.User;
import java.util.List;

public class GroupTable {
    private Schoolgroup group;
    private Teacher teacher;
    private List<User> users;
    private Boolean show;

    public GroupTable(Schoolgroup group, Teacher teacher, List<User> users) {
        this.group = group;
        this.teacher = teacher;
        this.users = users;
        this.show = true;
    }
    
    public GroupTable(Schoolgroup group, Teacher teacher) {
        this.group = group;
        this.teacher = teacher;
        this.show = true;
    }

    public GroupTable() {
        this.show = true;
    }

    public Schoolgroup getGroup() {
        return group;
    }

    public void setGroup(Schoolgroup group) {
        this.group = group;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }
    
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
