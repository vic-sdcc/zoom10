
package bean;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import model.CoopApplicant;
import org.primefaces.event.SelectEvent;
import service.CoopApplicantFacadeREST;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;


@ManagedBean(name = "applicantBean")
@SessionScoped
public class ApplicantBean implements Serializable {

    @PersistenceUnit
    EntityManagerFactory emf;

    @EJB
    private CoopApplicantFacadeREST coopApplicantFacadeREST;
    private CoopApplicant applicant;
    private CoopApplicant selectedApplicant;
    private List<CoopApplicant> applicantList;
    private List<CoopApplicant> filteredApplicants;
    private DataModel<CoopApplicant> applicantModel;

    public void init() {
        Query queryProspectList = emf.createEntityManager().createQuery("SELECT c FROM CoopApplicant c WHERE c.applicationStat = 'A'");
        applicantList = queryProspectList.getResultList();
        applicantModel = new ListDataModel<>(applicantList);
    }

    public void beanclear() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("applicantBean", null);
    }

    public ApplicantBean() {
    }

    public DataModel<CoopApplicant> getApplicantModel() {
        if (applicantModel == null) {
            Query queryProspectList = emf.createEntityManager().createQuery("SELECT c FROM CoopApplicant c WHERE c.applicationStat = 'A'");
            applicantList = queryProspectList.getResultList();
            applicantModel = new ListDataModel<>(applicantList);
        }
        return applicantModel;
    }

    public CoopApplicant getApplicant() {
        return applicant;
    }

    public void setApplicant(CoopApplicant applicant) {
        this.applicant = selectedApplicant;
    }

    public String save() {
        coopApplicantFacadeREST.create(applicant);
        applicant = new CoopApplicant();
        return "addApplicant";
    }

    public List<CoopApplicant> getApplicantList() {
        return applicantList;
    }

    public void setApplicantList(List<CoopApplicant> applicantList) {
        this.applicantList = applicantList;
    }

    public List<CoopApplicant> getFilteredApplicants() {
        return filteredApplicants;
    }

    public void setFilteredApplicants(List<CoopApplicant> filteredApplicants) {
        this.filteredApplicants = filteredApplicants;
    }

    public void setSelectedApplicant(CoopApplicant selectedApplicant) {
        this.selectedApplicant = selectedApplicant;
    }

    public CoopApplicant getSelectedApplicant() {
        if (selectedApplicant == null) {
            selectedApplicant = new CoopApplicant();
        }
        return selectedApplicant;
    }

    public void onRowSelect(SelectEvent event) {
    }

    public String saveEdit() {
        coopApplicantFacadeREST.edit(applicant);
        applicant = new CoopApplicant();
        beanclear();
        return "viewApplicant";
    }

    public String deleteApplicant() {
        coopApplicantFacadeREST.remove(applicant);
        applicant = new CoopApplicant();
        beanclear();
        return "viewApplicant";
    }
}
