/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.estate.security;

import com.teag.bean.PlannerBean;
import java.util.ArrayList;

/**
 * @author Paul Stay
 * Change the database passwords to be encoded.
 */
public class PasswordChange {

    public static void main(String args[]){
        PlannerBean planner = new PlannerBean();
        ArrayList<PlannerBean> list = planner.getBeans(PlannerBean.ID + ">0");

        for(PlannerBean p : list){
            try {
                String enc = PasswordService.getInstance().encrypt(p.getPassword());
                System.out.printf("%d\t%10s\t%15s\t%14s\t%s\n", p.getId(), p.getFirstName(), p.getLastName(), p.getEncryptPass(), enc);
                p.setEPass(enc);
                p.update();
            } catch( Exception e) {
                System.out.println("error with password servcice");
            }
        }
    }
}
