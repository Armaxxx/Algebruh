package com.algebruh.common.utils;

/**
 * Clase para manejar las ecuaciones almacenadas en la base de datos.
 * @author Demis
 */
public class EquationUtil {
    private final static int[] NUMBER_ELEMENTS_EQUATION = {0, 4, 3, 4, 3};
    private final static int[] NUMBER_ELEMENTS_SOLUTION = {0, 1, 1, 3, 4};
    
    public static String parseEquation(String rawEquation, int type){
        String[] eqElements = rawEquation.split(";");
        int n = NUMBER_ELEMENTS_EQUATION[type];
        int[] elements = new int[n];
        for(int i=0; i<n; i++){
            elements[i] = Integer.parseInt(eqElements[i]);
        }
        
        switch(type){
            case 1:
                return parseSolveEquation(elements);
            case 2:
                return parseSubstituteEquation(elements);
            case 3:
                return parseExpandEquation(elements);
            case 4:
                return parseFactorEquation(elements);
            default:
                return "N/A";
        }
    }

    public static String parseSolveEquation(int[] eq) {
        String strXL = "";
        String strXR = "";
        switch (eq[0]) {
            case 0:
                strXL = "";
                break;
            case 1:
                strXL = "X";
                break;
            case -1:
                strXL = "-X";
                break;
            default:
                strXL = eq[0] + "X";
                break;
        }
        switch (eq[2]) {
            case 0:
                strXR = "";
                break;
            case 1:
                strXR = "X";
                break;
            case -1:
                strXR = "-X";
                break;
            default:
                strXR = eq[2] + "X";
                break;
        }
        String opL = "";
        if (eq[0] == 0 && eq[1] < 0) {
            opL = " - ";
        } else if (eq[0] != 0 && eq[1] != 0) {
            opL = eq[1] > 0 ? " + " : " - ";
        }
        String opR = "";
        if (eq[2] == 0 && eq[3] < 0) {
            opR = " - ";
        } else if (eq[2] != 0 && eq[3] != 0) {
            opR = eq[3] > 0 ? " + " : " - ";
        }
        String uLeft = "";
        if (eq[1] == 0 && eq[0] == 0) {
            uLeft = "0";
        } else if (eq[1] != 0) {
            int tmp = eq[1] * (-1);
            uLeft = eq[1] < 0 ? "" + tmp : "" + eq[1];
        }
        String uRight = "";
        if (eq[3] == 0 && eq[2] == 0) {
            uRight = "0";
        } else if (eq[3] != 0) {
            int tmp = eq[3] * (-1);
            uRight = eq[3] < 0 ? "" + tmp : "" + eq[3];
        }

        return strXL + opL + uLeft + " = " + strXR + opR + uRight;
    }
    
    public static String parseSubstituteEquation(int[] eq){
        String equality = "X = " + eq[0];
        String X;
        switch(eq[1]){
            case 0:
                X = "";
                break;
            case 1:
                X = "X";
                break;
            case -1:
                X = "-X";
                break;
            default:
                X = eq[1] + "X";
                break;
        }
        String C = "";
        String op = "";
        if(eq[2] > 0){
            op = " + ";
            C = "" + eq[2];
        }
        else if(eq[2] < 0){
            op = " - ";
            eq[2] *= -1;
            C = "" + eq[2];
        }
        return X + op + C + ", " + equality;
    }
    
    public static String parseExpandEquation(int[] eq){
        String strXL = "";
        String strXR = "";
        switch (eq[0]) {
            case 0:
                strXL = "";
                break;
            case 1:
                strXL = "X";
                break;
            case -1:
                strXL = "-X";
                break;
            default:
                strXL = eq[0] + "X";
                break;
        }
        switch (eq[2]) {
            case 0:
                strXR = "";
                break;
            case 1:
                strXR = "X";
                break;
            case -1:
                strXR = "-X";
                break;
            default:
                strXR = eq[2] + "X";
                break;
        }
        String opL = "";
        if (eq[0] == 0 && eq[1] < 0) {
            opL = " - ";
        } else if (eq[0] != 0 && eq[1] != 0) {
            opL = eq[1] > 0 ? " + " : " - ";
        }
        String opR = "";
        if (eq[2] == 0 && eq[3] < 0) {
            opR = " - ";
        } else if (eq[2] != 0 && eq[3] != 0) {
            opR = eq[3] > 0 ? " + " : " - ";
        }
        String uLeft = "";
        if (eq[1] == 0 && eq[0] == 0) {
            uLeft = "0";
        } else if (eq[1] != 0) {
            int tmp = eq[1] * (-1);
            uLeft = eq[1] < 0 ? "" + tmp : "" + eq[1];
        }
        String uRight = "";
        if (eq[3] == 0 && eq[2] == 0) {
            uRight = "0";
        } else if (eq[3] != 0) {
            int tmp = eq[3] * (-1);
            uRight = eq[3] < 0 ? "" + tmp : "" + eq[3];
        }

        return "(" + strXL + opL + uLeft + ")(" + strXR + opR + uRight + ")";
    }
    
    public static String parseFactorEquation(int[] eq){
        String squared, op1, lineal, op2, constant;
        switch(eq[0]){
            case 0:
                squared = "";
                break;
            case 1:
                squared = "X\u00B2";
                break;
            case -1:
                squared = "-X\u00B2";
                break;
            default:
                squared = "-X\u00B2";
                break;
        }
        
        if(eq[1] > 0){
            op1 = " + ";
        }
        else if(eq[1] < 0){
            eq[1] *= -1;
            op1 = " - ";
        }
        else{
            op1 = "";
        }
        
        switch(eq[1]){
            case 0:
                lineal = "";
                break;
            case 1:
                lineal = "X";
                break;
            default:
                lineal = eq[1]+"X";
                break;
        }
        
        if(eq[2] > 0){
            op2 = " + ";
        }
        else if(eq[2] < 0){
            eq[2] *= -1;
            op2 = " - ";
        }
        else{
            op2 = "";
        }
        
        constant = eq[2] == 0 ? "" : ""+eq[2];
        
        return squared + op1 + lineal + op2 + constant;
    }
}
