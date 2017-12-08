/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algebruh.common.utils;

/**
 *
 * @author Demis
 */
public class EquationUtil {

    public static String getEquation1ForView(int[] eq) {
        String strXL = "";
        String strXR = "";
        if (eq[0] == 0) {
            strXL = "";
        } else if (eq[0] == 1) {
            strXL = "X";
        } else if (eq[0] == -1) {
            strXL = "-X";
        } else {
            strXL = eq[0] + "X";
        }
        if (eq[2] == 0) {
            strXR = "";
        } else if (eq[2] == 1) {
            strXR = "X";
        } else if (eq[2] == -1) {
            strXR = "-X";
        } else {
            strXR = eq[2] + "X";
        }
        String opL = "";
        if (eq[0] == 0 && eq[1] < 0) {
            opL = "-";
        } else if (eq[0] != 0 && eq[1] != 0) {
            opL = eq[1] > 0 ? "+" : "-";
        }
        String opR = "";
        if (eq[2] == 0 && eq[3] < 0) {
            opR = "-";
        } else if (eq[2] != 0 && eq[3] != 0) {
            opR = eq[3] > 0 ? "+" : "-";
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

        return strXL + " " + opL + " " + uLeft + " = " + strXR + " " + opR + " " + uRight;
    }
}
