/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.CoopPerson;
import service.CoopPersonFacadeREST;

/**
 *
 * @author vic
 */
@FacesConverter(forClass = CoopPerson.class, value = "cnvtr")
@ManagedBean(name="spsCnvtr")
public class myObjCnvtr implements Converter {

    @EJB
    CoopPersonFacadeREST coopPersonFacadeREST;

    private CoopPerson person;
    dateStringParse parse = new dateStringParse();
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        //getPerson().setPersonId(0);
        //getPerson().setFirstName(String.valueOf(((CoopPerson) value).getFirstName()));
        //getPerson().setLastName(String.valueOf(((CoopPerson) value).getLastName()));
        //getPerson().setMiddleName(String.valueOf(((CoopPerson) value).getMiddleName()));
        //getPerson().setGender(((CoopPerson) value).getGender());
        return String.valueOf(((CoopPerson) value).getPersonId()) + ";" + String.valueOf(((CoopPerson) value).getFirstName()) + ";" + String.valueOf(((CoopPerson) value).getLastName()) + ";" + ((CoopPerson) value).getBirthdate();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        String ID = "", fName = "", lName = "", bDate = "";
        Integer cntr = 0;

        for (int i = 0; i != value.length(); i++) {
            if (value.charAt(i) == ';') {
                cntr++;
                i++;
            }

            if (cntr == 0) {
                ID += value.charAt(i);
            } else if (cntr == 1) {
                fName += value.charAt(i);
            } else if (cntr == 2) {
                lName += value.charAt(i);
            } else if (cntr == 3) {
                bDate += value.charAt(i);
            }
        }

        try {
            getPerson().setPersonId(Integer.parseInt(ID));
        } catch (Exception e) {

        }
        
        
        getPerson().setFirstName(fName);
        getPerson().setLastName(lName);
        getPerson().setBirthdate(parse.parse(bDate));
        getPerson().setMiddleName("");
        return getPerson();
    }

    public CoopPerson getPerson() {
        if (person == null) {
            person = new CoopPerson();
        }
        return person;
    }

    public void setPerson(CoopPerson person) {
        this.person = person;
    }

}
