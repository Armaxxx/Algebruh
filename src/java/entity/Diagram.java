package entity;
// Generated Dec 11, 2017 3:40:56 PM by Hibernate Tools 4.3.1



/**
 * Diagram generated by hbm2java
 */
public class Diagram  implements java.io.Serializable {


     private Integer iddiagram;
     private Exercise exercise;
     private Student student;
     private String serial;
     private Evaluation evaluation;

    public Diagram() {
    }

	
    public Diagram(Exercise exercise, Student student, String serial) {
        this.exercise = exercise;
        this.student = student;
        this.serial = serial;
    }
    public Diagram(Exercise exercise, Student student, String serial, Evaluation evaluation) {
       this.exercise = exercise;
       this.student = student;
       this.serial = serial;
       this.evaluation = evaluation;
    }
   
    public Integer getIddiagram() {
        return this.iddiagram;
    }
    
    public void setIddiagram(Integer iddiagram) {
        this.iddiagram = iddiagram;
    }
    public Exercise getExercise() {
        return this.exercise;
    }
    
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
    public Student getStudent() {
        return this.student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    public String getSerial() {
        return this.serial;
    }
    
    public void setSerial(String serial) {
        this.serial = serial;
    }
    public Evaluation getEvaluation() {
        return this.evaluation;
    }
    
    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }




}


