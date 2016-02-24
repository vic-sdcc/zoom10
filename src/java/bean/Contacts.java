/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Date;

/**
 *
 * @author vic
 */
public class Contacts {
    private char contactType;
    private String contactDetail;
    
    public Contacts() {
    }
    
    public Contacts(char Type, String Detail) {
        this.contactType = Type;
        this.contactDetail = Detail;
    }

    public char getContactType() {
        return contactType;
    }

    public void setContactType(char contactType) {
        this.contactType = contactType;
    }

    public String getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(String contactDetail) {
        this.contactDetail = contactDetail;
    }

    

}
