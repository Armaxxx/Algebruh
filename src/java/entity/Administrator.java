package entity;
// Generated Dec 4, 2017 4:46:36 PM by Hibernate Tools 4.3.1



/**
 * Administrator generated by hbm2java
 */
public class Administrator  implements java.io.Serializable {


     private int iduser;
     private User user;

    public Administrator() {
    }

    public Administrator(User user) {
       this.user = user;
    }
   
    public int getIduser() {
        return this.iduser;
    }
    
    public void setIduser(int iduser) {
        this.iduser = iduser;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }




}


