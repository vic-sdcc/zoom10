/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import model.*;
import service.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.primefaces.event.SelectEvent;
import service.CoopMemberFacadeREST;

/**
 *
 * @author roland
 */
@ManagedBean
@SessionScoped
public class MemberBean implements Serializable {

    @PersistenceUnit
    EntityManagerFactory emf;

    @EJB
    private CoopFamilyFacadeREST coopFamilyFacadeREST;
    @EJB
    private CoopChildFacadeREST coopChildFacadeREST;
    @EJB
    private CoopPersonFacadeREST coopPersonFacadeREST;
    @EJB
    private CoopAddlAddressFacadeREST addlAddressFacadeREST;
    @EJB
    private CoopAddlContactInfoFacadeREST addlContactInfoFacadeREST;
    @EJB
    private CoopApplicantFacadeREST applicantFacadeREST;
    @EJB
    private CoopAwardsFacadeREST awardsFacadeREST;
    @EJB
    private CoopBizInfoFacadeREST bizInfoFacadeREST;
    @EJB
    private CoopEducInfoFacadeREST educInfoFacadeREST;
    @EJB
    private CoopEmplDtlFacadeREST employmentDetailFacadeREST;
    @EJB
    private CoopMemSkillFacadeREST memSkillFacadeREST;
    @EJB
    private CoopBizInfoFacadeREST coopBizInfoFacadeREST;
    @EJB
    private CoopMemberFacadeREST memberFacadeREST;
    @EJB
    private CoopAddlContactInfoFacadeREST coopAddlContactInfoFacadeREST;
    @EJB
    private CoopMemberStatusFacadeREST coopMemberStatusFacadeREST;
    @EJB
    private CoopInfoFacadeREST coopInfoFacadeREST;

    private CoopAddlAddress addlAddress = new CoopAddlAddress();
    private CoopEducInfo addtlEducInfo = new CoopEducInfo();
    private CoopAddlContactInfo addlContactInfo = new CoopAddlContactInfo();
    private CoopApplicant applicant;
    private CoopAwards awards = new CoopAwards();
    private CoopEmplDtl employmentDetail = new CoopEmplDtl();
    private CoopBizInfo businessInfo = new CoopBizInfo();
    private CoopMember member;
    private CoopOrgUnit orgUnit;
    private CoopNonMember coopNonMember;
    private CoopMemberStatus coopMemberStatus;
    private CoopInfo info = new CoopInfo();
    private CoopFamily familyTreefamilyParents = new CoopFamily();
    private CoopChild familyTreeChild = new CoopChild();
    private CoopFamily familyTreefamily = new CoopFamily();

    //selectedvariables
    private CoopApplicant selectedApplicant = new CoopApplicant();
    private CoopMember selectedMember;

    //models
    private DataModel<CoopApplicant> applicantModel;
    private DataModel<CoopMember> memberModel;
    private DataModel<CoopOrgUnit> ouModel;

    //lists
    private List<CoopApplicant> applicantList;
    private List<CoopApplicant> filteredApplicantList;
    private List<CoopMember> memberList;
    private List<CoopMember> filteredMemberList;
    private List<CoopOrgUnit> ouList;
    private List<CoopOrgUnit> filteredOuList;
    private List<CoopMemberStatus> memStatList;
    private List<CoopInfo> infoList;

    //arrayList
    private ArrayList<Contacts> contactInfo = new ArrayList<>();
    private ArrayList<CoopAddlAddress> addrInfo = new ArrayList<>();
    private ArrayList<CoopEducInfo> educInfo = new ArrayList<>();
    private ArrayList<CoopEmplDtl> emplInfo = new ArrayList<>();
    private ArrayList<CoopBizInfo> bizInfo = new ArrayList<>();
    private ArrayList<CoopAwards> awardsInfo = new ArrayList<>();
    private ArrayList<CoopPerson> familyTree = new ArrayList<>();
    private ArrayList<CoopPerson> familyTreeSpouses = new ArrayList<>();
    private ArrayList<CoopPerson> familyTreeSiblings = new ArrayList<>();
    private ArrayList<CoopPerson> familyTreeChildren = new ArrayList<>();
    private ArrayList<CoopPerson> childSpouse = new ArrayList<>();
    private ArrayList<String> childSpouseVal = new ArrayList<>();
    private ArrayList years = new ArrayList();

    private ArrayList<Contacts> memberSampleList = new ArrayList<>();

    private boolean add2;
    private boolean add3;
    private boolean add4;
    private boolean emplyd;
    private boolean ifAnyEmpl;
    private boolean ifAnyBiz;
    private boolean editMode;

    private Integer fatherMemNo;
    private Integer motherMemNo;
    private Integer spouseMemNo;
    private Integer SiblingMemNo;
    private Integer childMemNo;

    //memberSampleList*****************************************************
    public void addMemberSampleList() {
        getMemberSampleList().add(new Contacts());
        //memberSampleList.add(new SampleInput("name", new Date("11/12/1994")));
    }

    public ArrayList<Contacts> getMemberSampleList() {
        return memberSampleList;
    }

    public void setMemberSampleList(ArrayList<Contacts> memberSampleList) {
        this.memberSampleList = memberSampleList;
    }

    //NonmemberListSpouses*****************************************************
    public void addTxtBoxFamilyTreeSpouses() {
        if (getFamilyTreeSpouses().size() <= 7) {
            getFamilyTreeSpouses().add(new CoopPerson());
        }
    }

    public void removeTxtBoxFamilyTreeSpouses() {
        if (getFamilyTreeSpouses().size() > 0) {
            getFamilyTreeSpouses().remove(getFamilyTreeSpouses().size() - 1);
        }
    }

    public ArrayList<CoopPerson> getFamilyTreeSpouses() {
        return familyTreeSpouses;
    }

    public void setFamilyTreeSpouses(ArrayList<CoopPerson> familyTreeSpouses) {
        this.familyTreeSpouses = familyTreeSpouses;
    }

    //NonmemberListChildren*****************************************************
    public void addTxtBoxFamilyTreeChildren() {
        if (getFamilyTreeChildren().size() <= 10) {
            getFamilyTreeChildren().add(new CoopPerson());
            getChildSpouse().add(new CoopPerson());
            getChildSpouseVal().add(new String());
        }
    }

    public void removeTxtBoxFamilyTreeChildren() {
        if (getFamilyTreeChildren().size() > 0) {
            getFamilyTreeChildren().remove(getFamilyTreeChildren().size() - 1);
            getChildSpouse().remove(getChildSpouse().size() - 1);
            getChildSpouseVal().remove(getChildSpouseVal().size() - 1);
        }
    }

    public ArrayList<CoopPerson> getFamilyTreeChildren() {
        return familyTreeChildren;
    }

    public void setFamilyTreeChildren(ArrayList<CoopPerson> familyTreeChildren) {
        this.familyTreeChildren = familyTreeChildren;
    }

    public ArrayList<CoopPerson> getChildSpouse() {
        return childSpouse;
    }

    public void setChildSpouse(ArrayList<CoopPerson> childSpouse) {
        this.childSpouse = childSpouse;
    }

    public ArrayList<String> getChildSpouseVal() {
        return childSpouseVal;
    }

    public void setChildSpouseVal(ArrayList<String> childSpouseVal) {
        this.childSpouseVal = childSpouseVal;
    }

    //NonmemberListSiblings*****************************************************
    public void addTxtBoxFamilyTreeSiblings() {
        if (getFamilyTreeSiblings().size() <= 10) {
            getFamilyTreeSiblings().add(new CoopPerson());
        }
    }

    public void removeTxtBoxFamilyTreeSiblings() {
        if (getFamilyTreeSiblings().size() > 0) {
            getFamilyTreeSiblings().remove(getFamilyTreeSiblings().size() - 1);
        }
    }

    public ArrayList<CoopPerson> getFamilyTreeSiblings() {
        return familyTreeSiblings;
    }

    public void setFamilyTreeSiblings(ArrayList<CoopPerson> familyTreeSiblings) {
        this.familyTreeSiblings = familyTreeSiblings;
    }

    //NonmemberList*************************************************************
    public ArrayList<CoopPerson> getFamilyTree() {
        return familyTree;
    }

    public void setFamilyTree(ArrayList<CoopPerson> familyTree) {
        this.familyTree = familyTree;
    }

    //AwardsInfo****************************************************************
    public void addTxtBoxAwrd() {
        if (getAwardsInfo().size() <= 9) {
            getAwardsInfo().add(new CoopAwards());
        }
    }

    public void removeTxtBoxAwrd() {
        if (getAwardsInfo().size() > 0) {
            getAwardsInfo().remove(getAwardsInfo().size() - 1);
        }
    }

    public ArrayList<CoopAwards> getAwardsInfo() {
        return awardsInfo;
    }

    public void setAwardsInfo(ArrayList<CoopAwards> awardsInfo) {
        this.awardsInfo = awardsInfo;
    }

    //BizInfo******************************************************************
    public ArrayList<CoopBizInfo> getBizInfo() {
        return bizInfo;
    }

    public void setBizInfo(ArrayList<CoopBizInfo> bizInfo) {
        this.bizInfo = bizInfo;
    }

    //EmplInfo******************************************************************
    public ArrayList<CoopEmplDtl> getEmplInfo() {
        return emplInfo;
    }

    public void setEmplInfo(ArrayList<CoopEmplDtl> emplInfo) {
        this.emplInfo = emplInfo;
    }

    //EduInfo*******************************************************************
    public void addTxtBoxEdu() {
        if (getEducInfo().size() <= 5) {
            getEducInfo().add(new CoopEducInfo());
        }
    }

    public void removeTxtBoxEdu() {
        if (getEducInfo().size() > 0) {
            getEducInfo().remove(getEducInfo().size() - 1);
        }
    }

    public ArrayList<CoopEducInfo> getEducInfo() {
        return educInfo;
    }

    public void setEducInfo(ArrayList<CoopEducInfo> educInfo) {
        this.educInfo = educInfo;
    }

    //**************************************************************************
    public ArrayList<CoopAddlAddress> getAddrInfo() {
        return addrInfo;
    }

    public void setAddrInfo(ArrayList<CoopAddlAddress> addrInfo) {
        this.addrInfo = addrInfo;
    }

    //ContactInfo***************************************************************
    public void addTxtBoxCont() {
        if (getContactInfo().size() <= 3) {
            getContactInfo().add(new Contacts());
        }
    }

    public void removeTxtBoxCont() {
        if (getContactInfo().size() > 0) {
            getContactInfo().remove(getContactInfo().size() - 1);
        }
    }

    public ArrayList<Contacts> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ArrayList<Contacts> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public MemberBean() {
        for (int i = (new Date().getYear() + 1900); i > 1949; i--) {
            years.add(i);
        }
        newSetArrayListMember();
    }

    private List<CoopMember> memberCountList;
    private DataModel<CoopMember> memberCountModel;

    private String memID;

    public String getMemNo() {
        Query memberCount = emf.createEntityManager().createQuery("SELECT c FROM CoopMember c");
        memberCountList = memberCount.getResultList();
        memberCountModel = new ListDataModel(memberCountList);
        memID = String.format("%05d", memberCountModel.getRowCount() + 1);
        memID = (new Date().getYear() + 1900) + "-" + memID;
        return memID;
    }

    @PostConstruct
    public void init() {
        applicant = new CoopApplicant();
        Query queryProspectList = emf.createEntityManager().createQuery("SELECT c FROM CoopApplicant c WHERE c.applicationStat = 'A'");
        applicantList = queryProspectList.getResultList();
        applicantModel = new ListDataModel<>(applicantList);
        memberList = memberFacadeREST.findAll();
        memberModel = new ListDataModel<>(memberList);
        memStatList = coopMemberStatusFacadeREST.findAll();
        infoList = coopInfoFacadeREST.findAll();
        if (!editMode) {
            member.setMemNo(getMemNo());
            member.setMemIdNo(getMemNo());
        }
    }

    public void beanclear() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("memberBean", null);
    }

    //getters start
    public CoopAddlAddress getAddlAddress() {
        if (addlAddress == null) {
            addlAddress = new CoopAddlAddress();
        }
        return addlAddress;
    }

    public CoopAddlContactInfo getAddlContactInfo() {
        if (addlContactInfo == null) {
            addlContactInfo = new CoopAddlContactInfo();
        }
        return addlContactInfo;
    }

    public CoopApplicant getApplicant() {
        if (applicant == null) {
            applicant = new CoopApplicant();
        }
        return applicant;
    }

    public CoopAwards getAwards() {
        if (awards == null) {
            awards = new CoopAwards();
        }
        return awards;
    }

    public CoopEmplDtl getEmploymentDetail() {
        if (employmentDetail == null) {
            employmentDetail = new CoopEmplDtl();
        }
        return employmentDetail;
    }

    public CoopMember getMember() {
        if (member == null) {
            member = new CoopMember();
        }
        return member;
    }

    public CoopOrgUnit getOrgUnit() {
        if (orgUnit == null) {
            orgUnit = new CoopOrgUnit();
        }
        return orgUnit;
    }

    public CoopApplicant getSelectedApplicant() {
        if (selectedApplicant == null) {
            selectedApplicant = new CoopApplicant();
        }
        return selectedApplicant;
    }

    public CoopMember getSelectedMember() {
        if (selectedMember == null) {
            selectedMember = new CoopMember();
        }
        return selectedMember;
    }
    //getters end

    //setters start
    public void setAddlAddress(CoopAddlAddress addlAddress) {
        this.addlAddress = addlAddress;
    }

    public void setAddlContactInfo(CoopAddlContactInfo addlContactInfo) {
        this.addlContactInfo = addlContactInfo;
    }

    public void setApplicant(CoopApplicant applicant) {
        this.applicant = applicant;
    }

    public void setAwards(CoopAwards awards) {
        this.awards = awards;
    }

    public void setEmploymentDetail(CoopEmplDtl employmentDetail) {
        this.employmentDetail = employmentDetail;
    }

    public void setMember(CoopMember member) {
        this.member = member;
    }

    public void setOrgUnit(CoopOrgUnit orgUnit) {
        this.orgUnit = orgUnit;
    }

    public void setSelectedApplicant(CoopApplicant selectedApplicant) {
        this.selectedApplicant = selectedApplicant;
    }

    public void setSelectedMember(CoopMember selectedMember) {
        this.selectedMember = selectedMember;
    }
    //setters end

    //list
    public List<CoopApplicant> getapplicantList() {
        applicantList = applicantFacadeREST.findAll();
        return applicantList;
    }

    public DataModel<CoopApplicant> getApplicantModel() {
        if (applicantModel == null) {
            Query queryProspectList = emf.createEntityManager().createQuery("SELECT c FROM CoopApplicant c WHERE c.applicationStat = 'A'");
            applicantList = queryProspectList.getResultList();
            applicantModel = new ListDataModel<>(applicantList);
        }
        return applicantModel;
    }

    public List<CoopMember> getMemberList() {
        memberList = memberFacadeREST.findAll();
        return memberList;
    }

    public DataModel<CoopMember> getMemberModel() {
        if (memberModel == null) {
            memberModel = new ListDataModel<>(memberList);
        }
        return memberModel;
    }

    public List<CoopOrgUnit> getOuList() {
        return ouList;
    }

    public DataModel<CoopOrgUnit> getOuModel() {
        if (ouModel == null) {
            ouModel = new ListDataModel<>(ouList);
        }
        return ouModel;
    }

    //filters
    public List<CoopApplicant> getFilteredApplicantList() {
        return filteredApplicantList;
    }

    public void setFilteredApplicantList(List<CoopApplicant> filteredApplicantList) {
        this.filteredApplicantList = filteredApplicantList;
    }

    public List<CoopMember> getFilteredMemberList() {
        return filteredMemberList;
    }

    public void setFilteredMemberList(List<CoopMember> filteredMemberList) {
        this.filteredMemberList = filteredMemberList;
    }

    public List<CoopOrgUnit> getFilteredOuList() {
        return filteredOuList;
    }

    public void setFilteredOuList(List<CoopOrgUnit> filteredOuList) {
        this.filteredOuList = filteredOuList;
    }

    public void onRowSelect(SelectEvent event) {
        setEditMode(false);
        loadData();
        ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
        configurableNavigationHandler.performNavigation("viewMember?faces-redirect=true");
    }

    public String saveEdit() {
        memberFacadeREST.edit(member);
        member = new CoopMember();
        beanclear();
        return "viewMember";
    }

    public static String formatDate(Date currentDate) {
        DateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy");
        return dateFormat.format(currentDate);
    }

    public static String formatBDate(Date currentDate) {
        /*
         String txtdate = "06/06/2012";
         SimpleDateFormat pd = new SimpleDateFormat("dd/MM/yyyy");
         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

         Date date = null;
         try {
         date = (Date) pd.parse(txtdate);
         } catch (ParseException e) {
         e.printStackTrace();
         }

         String DisplayDate = df.format(date);
         */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        return dateFormat.format(currentDate);
    }

    public boolean isAdd2() {
        return add2;
    }

    public void setAdd2(boolean add2) {
        this.add2 = add2;
    }

    public boolean isAdd3() {
        return add3;
    }

    public void setAdd3(boolean add3) {
        this.add3 = add3;
    }

    public boolean isAdd4() {
        return add4;
    }

    public void setAdd4(boolean add4) {
        this.add4 = add4;
    }

    public ArrayList getYears() {
        return years;
    }

    public void setYears(ArrayList years) {
        this.years = years;
    }

    public boolean isIfAnyEmpl() {
        return ifAnyEmpl;
    }

    public void setIfAnyEmpl(boolean ifAnyEmpl) {
        this.ifAnyEmpl = ifAnyEmpl;
    }

    public void bizIfAny() {
        if (!member.getOwnedBusiness()) {
            setIfAnyBiz(member.getOwnedBusiness());
        }

    }

    public boolean isIfAnyBiz() {
        return ifAnyBiz;
    }

    public void setIfAnyBiz(boolean ifAnyBiz) {
        this.ifAnyBiz = ifAnyBiz;
    }

    public boolean isEmplyd() {

        if (!emplyd) {
            setIfAnyEmpl(false);
        }

        return emplyd;
    }

    public void setEmplyd(boolean emplyd) {
        this.emplyd = emplyd;
    }

    public CoopNonMember getCoopNonMember() {
        return coopNonMember;
    }

    public void setCoopNonMember(CoopNonMember coopNonMember) {
        this.coopNonMember = coopNonMember;
    }

    public void contactsValues() {
        if (isEditMode()) {
            contactsDataLoad();
        }
        addlContactInfo.setMemNo(member);
        for (int i = 0; i != contactInfo.size(); i++) {
            addlContactInfo.setContactType(contactInfo.get(i).getContactType());
            addlContactInfo.setContactDetail(contactInfo.get(i).getContactDetail());
            if (addlContactInfo.getContactDetail().length() > 0) {
                coopAddlContactInfoFacadeREST.create(addlContactInfo);
            }
        }
    }

    public void memberGetValues() {
        if (!isEditMode()) {
            member.setMemNo(getMemNo());
            member.setMemIdNo(getMemNo());
        }
        member.setScAcctno(member.getScAcctno());

        member.setLastName(selectedApplicant.getLastName());
        member.setFirstName(selectedApplicant.getFirstName());
        member.setMiddleName(selectedApplicant.getMiddleName());
        member.setNickname(member.getNickname());
        member.setGender(selectedApplicant.getGender());
        member.setBirthdate(selectedApplicant.getBirthdate());
        member.setBirthplace(member.getBirthplace());
        member.setResidenceType(member.getResidenceType());
        member.setStreet(selectedApplicant.getStreet());
        member.setBarangay(selectedApplicant.getBarangay());
        member.setCityMun(selectedApplicant.getCityMun());
        member.setRegion(selectedApplicant.getRegion());
        member.setProvince(selectedApplicant.getProvince());
        member.setCivilStatus(selectedApplicant.getCivilStatus());
        member.setEmail(selectedApplicant.getEmail());
        member.setContactNumber(selectedApplicant.getContactNumber());
        member.setMemDate(new Date());

        member.setMemStatRem(member.getMemStatRem());
        member.setMemStatus(member.getMemStatus());
        member.setStatusDate(new Date());
        member.setOuCode(selectedApplicant.getOuCode());
        member.setTaxIdNo(member.getTaxIdNo());
        member.setNationality(selectedApplicant.getNationality());
        member.setZipCode(member.getZipCode());
        member.setOccupation(selectedApplicant.getOccupation());
        member.setReligion(member.getReligion());
        member.setPPrefix(member.getPPrefix());
        member.setSuffix(member.getSuffix());
        member.setPersonStatus(member.getPersonStatus());
        member.setBlood(member.getBlood());
        member.setNotifyName(member.getNotifyName());
        member.setNotifyAddress(member.getNotifyAddress());
        member.setNotifyPhone(member.getNotifyPhone());
        member.setNotifyRelation(member.getNotifyRelation());
        member.setHeight(member.getHeight());
        member.setWeight(member.getWeight());
        member.setOwnedBusiness(member.getOwnedBusiness());
        //member.setSinglecode(familyTree.get(3));
        if (isEditMode()) {
            memberFacadeREST.edit(member);
        } else {
            memberFacadeREST.create(member);
        }
    }

    public void addressGetValues() {
        if (isEditMode()) {
            addressDataLoad();
        }
        addlAddress.setMemNo(member);
        if (add2) {
            addlAddress.setAddressType("If any");
            addlAddress.setStreet(addrInfo.get(0).getStreet());
            addlAddress.setBarangay(addrInfo.get(0).getBarangay());
            addlAddress.setCityMun(addrInfo.get(0).getCityMun());
            addlAddress.setProvince(addrInfo.get(0).getProvince());
            addlAddress.setRegion(addrInfo.get(0).getRegion());
            addlAddress.setZipcode(addrInfo.get(0).getZipcode());
            addlAddressFacadeREST.create(addlAddress);
        }
        if (add3) {
            addlAddress.setAddressType("Previous");
            addlAddress.setStreet(addrInfo.get(1).getStreet());
            addlAddress.setBarangay(addrInfo.get(1).getBarangay());
            addlAddress.setCityMun(addrInfo.get(1).getCityMun());
            addlAddress.setProvince(addrInfo.get(1).getProvince());
            addlAddress.setRegion(addrInfo.get(1).getRegion());
            addlAddress.setZipcode(addrInfo.get(1).getZipcode());
            addlAddressFacadeREST.create(addlAddress);
        }
        if (add4) {
            addlAddress.setAddressType("Provincial");
            addlAddress.setStreet(addrInfo.get(2).getStreet());
            addlAddress.setBarangay(addrInfo.get(2).getBarangay());
            addlAddress.setCityMun(addrInfo.get(2).getCityMun());
            addlAddress.setProvince(addrInfo.get(2).getProvince());
            addlAddress.setRegion(addrInfo.get(2).getRegion());
            addlAddress.setZipcode(addrInfo.get(2).getZipcode());
            addlAddressFacadeREST.create(addlAddress);
        }
        if (emplyd) {
            addlAddress.setAddressType("Biz 1");
            addlAddress.setStreet(addrInfo.get(3).getStreet());
            addlAddress.setBarangay(addrInfo.get(3).getBarangay());
            addlAddress.setCityMun(addrInfo.get(3).getCityMun());
            addlAddress.setProvince(addrInfo.get(3).getProvince());
            addlAddress.setRegion(addrInfo.get(3).getRegion());
            addlAddress.setZipcode(addrInfo.get(3).getZipcode());
            addlAddressFacadeREST.create(addlAddress);
        }
        if (ifAnyEmpl && emplyd) {
            addlAddress.setAddressType("Biz 2");
            addlAddress.setStreet(addrInfo.get(4).getStreet());
            addlAddress.setBarangay(addrInfo.get(4).getBarangay());
            addlAddress.setCityMun(addrInfo.get(4).getCityMun());
            addlAddress.setProvince(addrInfo.get(4).getProvince());
            addlAddress.setRegion(addrInfo.get(4).getRegion());
            addlAddress.setZipcode(addrInfo.get(4).getZipcode());
            addlAddressFacadeREST.create(addlAddress);
        }
    }

    public void educationGetValues() {
        if (isEditMode()) {
            educationDataLoad();
        }
        addtlEducInfo.setMemNo(member);
        for (int i = 0; i != educInfo.size(); i++) {
            addtlEducInfo.setSchoolName(educInfo.get(i).getSchoolName());
            addtlEducInfo.setCourse(educInfo.get(i).getCourse());
            addtlEducInfo.setSchoolLevel(educInfo.get(i).getSchoolLevel());
            addtlEducInfo.setGraduated(educInfo.get(i).getGraduated());
            addtlEducInfo.setYearLastAttended(educInfo.get(i).getYearLastAttended());
            if (addtlEducInfo.getSchoolName().length() > 0 && !"''".equals(addtlEducInfo.getSchoolName())) {
                educInfoFacadeREST.create(addtlEducInfo);
            }
        }

    }

    public void employmentGetValues() {
        if (isEditMode()) {
            employmentDataLoad();
        }
        employmentDetail.setMemNo(member);
        if (emplyd) {
            for (int i = 0; i != 2; i++) {
                employmentDetail.setEmplrBizName(emplInfo.get(i).getEmplrBizName());
                employmentDetail.setEmploymentStat(emplInfo.get(i).getEmploymentStat());
                employmentDetail.setEmplSector(emplInfo.get(i).getEmplSector());
                employmentDetail.setEmplrContactNo(emplInfo.get(i).getEmplrContactNo());
                employmentDetail.setCompBracket(emplInfo.get(i).getCompBracket());
                employmentDetail.setRankPos(emplInfo.get(i).getRankPos());
                employmentDetail.setEmploymentDate(emplInfo.get(i).getEmploymentDate());
                employmentDetail.setWorkplaceEmailAdd(emplInfo.get(i).getWorkplaceEmailAdd());
                employmentDetailFacadeREST.create(employmentDetail);
                if (!ifAnyEmpl) {
                    break;
                }
            }
        }
    }

    public void businessGetValues() {
        if (isEditMode()) {
            businessDataLoad();
        }
        businessInfo.setMemNo(member);
        if (member.getOwnedBusiness()) {
            for (int i = 0; i != 2; i++) {
                businessInfo.setBizName(bizInfo.get(i).getBizName());
                businessInfo.setDateEstablished(bizInfo.get(i).getDateEstablished());
                businessInfo.setBizType(bizInfo.get(i).getBizType());
                businessInfo.setBizInfoNum(bizInfo.get(i).getBizInfoNum());
                businessInfo.setBizNature(bizInfo.get(i).getBizNature());
                businessInfo.setBizIncRange(bizInfo.get(i).getBizIncRange());
                bizInfoFacadeREST.create(businessInfo);
                if (!ifAnyBiz) {
                    break;
                }
            }
        }
    }

    public void awardsValues() {
        if (isEditMode()) {
            awardsDataLoad();
        }
        awards.setMemNo(member);
        for (int i = 0; i != awardsInfo.size(); i++) {
            awards.setAwardTitle(awardsInfo.get(i).getAwardTitle());
            awards.setAccAwdDate(awardsInfo.get(i).getAccAwdDate());
            awards.setAwardDetails(awardsInfo.get(i).getAwardDetails());

            if (awards.getAwardTitle().length() > 0 && !"''".equals(awards.getAwardTitle())) {
                awardsFacadeREST.create(awards);
            }
        }
    }

    dateStringParse parse = new dateStringParse();
    String fName = "", lName = "", bDate = "";
    Integer cntr = 0;

    public void familyTreeGetValues() {
        //Member
        CoopPerson familyTreeMember = null;
        try {
            //If selected member no.exist in the person table
            familyTreeMember = (CoopPerson) emf.createEntityManager().createQuery("SELECT c FROM CoopPerson c "
                    + "WHERE c.memNo.memNo = : '" + selectedMember.getMemNo() + "' ").getResultList().get(0);
            familyTree.set(2, familyTreeMember);
            coopPersonFacadeREST.edit(familyTree.get(2));
        } catch (Exception e) {
            try {
                //If the member is existing in the person table
                familyTreeMember = (CoopPerson) emf.createEntityManager().createQuery("SELECT c FROM CoopPerson c "
                        + "WHERE LOWER(c.lastName) = LOWER('" + selectedApplicant.getLastName() + "') "
                        + "AND LOWER(c.firstName) = LOWER('" + selectedApplicant.getFirstName() + "') "
                        + "AND c.birthdate = ?1").setParameter(1, selectedApplicant.getBirthdate(), TemporalType.TIMESTAMP).getResultList().get(0);
                familyTreeMember.setMemNo(member);
                familyTree.set(2, familyTreeMember);
                coopPersonFacadeREST.edit(familyTree.get(2));
            } catch (Exception f) {
                //Create new member if not exist
                familyTree.get(2).setLastName(selectedApplicant.getLastName());
                familyTree.get(2).setFirstName(selectedApplicant.getFirstName());
                familyTree.get(2).setMiddleName(selectedApplicant.getMiddleName());
                familyTree.get(2).setBirthdate(selectedApplicant.getBirthdate());
                familyTree.get(2).setGender(selectedApplicant.getGender());
                familyTree.get(2).setMemNo(member);
                coopPersonFacadeREST.create(familyTree.get(2));
            }
        }

        try {//avoid multiple null parent values
            familyTreeFamily = (CoopFamily) emf.createEntityManager().createQuery("SELECT d FROM CoopChild c JOIN c.familyId d "
                    + "WHERE c.personId.personId = : " + familyTree.get(2).getPersonId() + "").getResultList().get(0);
            if (familyTreeFamily.getFamilyId() > 0 && familyTreeFamily.getHusband() == null && familyTreeFamily.getWife() == null) {
                if (familyTree.get(0).getLastName().length() <= 0 && familyTree.get(1).getLastName().length() <= 0) {
                    familyTreefamilyParents.setFamilyId(familyTreeFamily.getFamilyId());
                }
            }
        } catch (Exception e) {
        }

        if (isEditMode()) {
            familyTreeDataLoad();
        }

        //Father
        CoopPerson familyFather = null;
        if (familyTree.get(0).getLastName().length() > 0) {
            familyTree.get(0).setGender('M');
            familyTreefamilyParents.setHusband(familyTree.get(0));
            try {
                //If father already exist in the person table
                familyFather = (CoopPerson) emf.createEntityManager().createQuery("SELECT c FROM CoopPerson c "
                        + "WHERE LOWER(c.lastName) = LOWER('" + familyTree.get(0).getLastName() + "') "
                        + "AND LOWER(c.firstName) = LOWER('" + familyTree.get(0).getFirstName() + "') "
                        + "AND c.birthdate = ?1").setParameter(1, familyTree.get(0).getBirthdate(), TemporalType.TIMESTAMP).getResultList().get(0);
                familyTreefamilyParents.setHusband(familyFather);
            } catch (Exception e) {
                coopPersonFacadeREST.create(familyTree.get(0));
            }
        } else {
            familyTreefamilyParents.setHusband(null);
        }

        //Mother
        CoopPerson familyMother = null;
        if (familyTree.get(1).getLastName().length() > 0) {
            familyTree.get(1).setGender('F');
            familyTreefamilyParents.setWife(familyTree.get(1));
            try {
                //If mother already exist in the person table
                familyMother = (CoopPerson) emf.createEntityManager().createQuery("SELECT c FROM CoopPerson c "
                        + "WHERE LOWER(c.lastName) = LOWER('" + familyTree.get(1).getLastName() + "') "
                        + "AND LOWER(c.firstName) = LOWER('" + familyTree.get(1).getFirstName() + "') "
                        + "AND c.birthdate = ?1").setParameter(1, familyTree.get(1).getBirthdate(), TemporalType.TIMESTAMP).getResultList().get(0);
                familyTreefamilyParents.setWife(familyMother);
            } catch (Exception e) {
                coopPersonFacadeREST.create(familyTree.get(1));
            }
        } else {
            familyTreefamilyParents.setWife(null);
        }

        //create coopFamily for parents and for get parent id for child
        try {
            //Check if husband or wife id already exist
            if (familyFather == null && familyMother != null) {
                familyTreeFamily = (CoopFamily) emf.createEntityManager().createQuery("SELECT c FROM CoopFamily c "
                        + "WHERE c.husband IS NULL "
                        + "AND c.wife.personId = : " + familyMother.getPersonId() + " ").getResultList().get(0);
            } else if (familyMother == null && familyFather != null) {
                familyTreeFamily = (CoopFamily) emf.createEntityManager().createQuery("SELECT c FROM CoopFamily c "
                        + "WHERE c.husband.personId = : " + familyFather.getPersonId() + " "
                        + "AND c.wife IS NULL").getResultList().get(0);
            } else {
                familyTreeFamily = (CoopFamily) emf.createEntityManager().createQuery("SELECT c FROM CoopFamily c "
                        + "WHERE c.husband.personId = : " + familyFather.getPersonId() + " "
                        + "AND c.wife.personId = : " + familyMother.getPersonId() + " ").getResultList().get(0);
            }
            familyTreefamilyParents.setFamilyId(familyTreeFamily.getFamilyId());
            //coopFamilyFacadeREST.edit(familyTreefamilyParents);
        } catch (Exception e) {
            //try {
            //    familyTreeFamily = (CoopFamily) emf.createEntityManager().createQuery("SELECT d FROM CoopChild c JOIN c.familyId d "
            //            + "WHERE c.personId.personId = : " + familyTree.get(2).getPersonId() + "").getResultList().get(0);
            //    if (familyTreeFamily.getHusband() != null && familyTreeFamily.getWife() != null) {
            //        familyTreefamilyParents.setFamilyId(familyTreeFamily.getFamilyId());
            //        //coopFamilyFacadeREST.edit(familyTreefamilyParents);
            //    }
            //} catch (Exception e1) {
            //}
            if (familyTreefamilyParents.getFamilyId() == null) {
                coopFamilyFacadeREST.create(familyTreefamilyParents);
            }
        }

        try {//do nothing if child data exist
            CoopChild childMember = (CoopChild) emf.createEntityManager().createQuery("SELECT c FROM CoopChild c "
                    + "WHERE c.familyId.familyId = : " + familyTreefamilyParents.getFamilyId() + " "
                    + "AND c.personId.personId = : " + familyTreeMember.getPersonId() + "").getResultList().get(0);
        } catch (Exception e) {
            familyTreeChild.setFamilyId(familyTreefamilyParents);
            familyTreeChild.setPersonId(familyTree.get(2));
            coopChildFacadeREST.create(familyTreeChild);
        }

        //Siblings
        CoopPerson familySiblings = null;
        for (int i = 0; i != familyTreeSiblings.size(); i++) {
            if (familyTreeSiblings.get(i).getLastName().length() > 0) {
                //This is where you gonna make changes
                //right now dumodoble p ung data s person pag edit n
                try {
                    familySiblings = (CoopPerson) emf.createEntityManager().createQuery("SELECT c FROM CoopPerson c "
                            + "WHERE LOWER(c.lastName) = LOWER('" + familyTreeSiblings.get(i).getLastName() + "') "
                            + "AND LOWER(c.firstName) = LOWER('" + familyTreeSiblings.get(i).getFirstName() + "') "
                            + "AND c.birthdate = ?1").setParameter(1, familyTreeSiblings.get(i).getBirthdate(), TemporalType.TIMESTAMP).getResultList().get(0);
                    //familyTreeSiblings.set(i, familySiblings);
                    coopPersonFacadeREST.edit(familyTreeSiblings.get(i));
                } catch (Exception e) {
                    coopPersonFacadeREST.create(familyTreeSiblings.get(i));
                }
                try {//do nothing if child data exist
                    CoopChild childSiblings = (CoopChild) emf.createEntityManager().createQuery("SELECT c FROM CoopChild c "
                            + "WHERE c.familyId.familyId = : " + familyTreefamilyParents.getFamilyId() + " "
                            + "AND c.personId.personId = : " + familyTreeSiblings.get(i).getPersonId() + "").getResultList().get(0);
                } catch (Exception e) {
                    familyTreeChild.setFamilyId(familyTreefamilyParents);
                    familyTreeChild.setPersonId(familyTreeSiblings.get(i));
                    coopChildFacadeREST.create(familyTreeChild);
                }
            }
        }

        //Spouse
        CoopPerson familySpouse = null;
        for (int i = 0; i != familyTreeSpouses.size(); i++) {
            if (familyTreeSpouses.get(i).getLastName().length() > 0) {
                if (selectedApplicant.getGender() == 'M') {
                    familyTreeSpouses.get(i).setGender('F');
                    familyTreefamily.setHusband(familyTree.get(2));
                    familyTreefamily.setWife(familyTreeSpouses.get(i));
                } else {
                    familyTreeSpouses.get(i).setGender('M');
                    familyTreefamily.setHusband(familyTreeSpouses.get(i));
                    familyTreefamily.setWife(familyTree.get(2));
                }
            } else {
                if (selectedApplicant.getGender() == 'M') {
                    familyTreefamily.setHusband(familyTree.get(2));
                    familyTreefamily.setWife(null);
                } else {
                    familyTreefamily.setHusband(null);
                    familyTreefamily.setWife(familyTree.get(2));
                }
            }

            //This is where you gonna make changes
            try {
                familySpouse = (CoopPerson) emf.createEntityManager().createQuery("SELECT c FROM CoopPerson c "
                        + "WHERE LOWER(c.lastName) = LOWER('" + familyTreeSpouses.get(i).getLastName() + "') "
                        + "AND LOWER(c.firstName) = LOWER('" + familyTreeSpouses.get(i).getFirstName() + "') "
                        + "AND c.birthdate = ?1").setParameter(1, familyTreeSpouses.get(i).getBirthdate(), TemporalType.TIMESTAMP).getResultList().get(0);
            } catch (Exception e) {
                if (familyTreeSpouses.get(i).getLastName().length() > 0) {
                    coopPersonFacadeREST.create(familyTreeSpouses.get(i));
                }
            }
            if (selectedApplicant.getGender() == 'M') {
                try {
                    familyTreeFamily = (CoopFamily) emf.createEntityManager().createQuery("SELECT c FROM CoopFamily c "
                            + "WHERE COALESCE(c.husband.personId,0) = : " + familyTree.get(2).getPersonId() + " "
                            + "AND COALESCE(c.wife.personId,0) = : " + familySpouse.getPersonId() + " ").getResultList().get(0);
                    coopFamilyFacadeREST.edit(familyTreeFamily);
                } catch (Exception f) {
                    try {
                        familyTreeFamily = (CoopFamily) emf.createEntityManager().createQuery("SELECT c FROM CoopFamily c "
                                + "WHERE COALESCE(c.husband.personId,0) = : " + familyTree.get(2).getPersonId() + " "
                                + "AND c.wife IS NULL ").getResultList().get(0);
                        if (familyTreeSpouses.get(i).getLastName().length() > 0) {
                            if (familySpouse.getPersonId() != null) {
                                familyTreefamily.setWife(familySpouse);
                            }
                            coopFamilyFacadeREST.create(familyTreefamily);
                        } else {
                            coopFamilyFacadeREST.edit(familyTreeFamily);
                        }
                    } catch (Exception f1) {
                        try {
                            if (familySpouse.getPersonId() != null) {
                                familyTreefamily.setWife(familySpouse);
                            }
                        } catch (Exception e) {
                        }
                        coopFamilyFacadeREST.create(familyTreefamily);
                    }
                }
            } else {
                try {
                    familyTreeFamily = (CoopFamily) emf.createEntityManager().createQuery("SELECT c FROM CoopFamily c "
                            + "WHERE COALESCE(c.husband.personId,0) = : " + familySpouse.getPersonId() + " "
                            + "AND COALESCE(c.wife.personId,0) = : " + familyTree.get(2).getPersonId() + " ").getResultList().get(0);
                    coopFamilyFacadeREST.edit(familyTreeFamily);
                } catch (Exception g) {
                    try {
                        familyTreeFamily = (CoopFamily) emf.createEntityManager().createQuery("SELECT c FROM CoopFamily c "
                                + "WHERE c.husband IS NULL "
                                + "AND COALESCE(c.wife.personId,0) = : " + familyTree.get(2).getPersonId() + " ").getResultList().get(0);
                        if (familyTreeSpouses.get(i).getLastName().length() > 0) {
                            if (familySpouse.getPersonId() != null) {
                                familyTreefamily.setHusband(familySpouse);
                            }
                            coopFamilyFacadeREST.create(familyTreefamily);
                        } else {
                            coopFamilyFacadeREST.edit(familyTreeFamily);
                        }
                    } catch (Exception g1) {
                        try {
                            if (familySpouse.getPersonId() != null) {
                                familyTreefamily.setHusband(familySpouse);
                            }
                        } catch (Exception e) {
                        }
                        coopFamilyFacadeREST.create(familyTreefamily);
                    }
                }
            }
        }

        //Children
        CoopPerson familyOffspring;
        for (int i = 0; i != familyTreeChildren.size(); i++) {
            if (familyTreeChildren.get(i).getLastName().length() > 0) {
                //This is where you gonna make changes
                cntr = 0;
                lName = "";
                fName = "";
                bDate = "";
                try {
                    familyOffspring = (CoopPerson) emf.createEntityManager().createQuery("SELECT c FROM CoopPerson c "
                            + "WHERE LOWER(c.lastName) = LOWER('" + familyTreeChildren.get(i).getLastName() + "') "
                            + "AND LOWER(c.firstName) = LOWER('" + familyTreeChildren.get(i).getFirstName() + "') "
                            + "AND c.birthdate = ?1").setParameter(1, familyTreeChildren.get(i).getBirthdate(), TemporalType.TIMESTAMP).getResultList().get(0);
                    familyTreeChildren.set(i, familyOffspring);
                    coopPersonFacadeREST.edit(familyTreeChildren.get(i));
                } catch (Exception e) {
                    coopPersonFacadeREST.create(familyTreeChildren.get(i));
                }
                try {//do nothing if child data exist
                    //getSting firstname, lastname, bdate. delimiter ';'
                    for (int x = 0; x != getChildSpouseVal().get(i).length(); x++) {
                        if (getChildSpouseVal().get(i).charAt(x) == ';') {
                            cntr++;
                            x++;
                        }
                        if (cntr == 0) {
                            fName += getChildSpouseVal().get(i).charAt(x);
                        } else if (cntr == 1) {
                            lName += getChildSpouseVal().get(i).charAt(x);
                        } else if (cntr == 2) {
                            bDate += getChildSpouseVal().get(i).charAt(x);
                        }
                    }
                    if (familyTree.get(2).getGender() == 'M') {
                        familyTreeFamily = (CoopFamily) emf.createEntityManager().createQuery("SELECT c FROM CoopFamily c "
                                + "WHERE c.wife.personId = (SELECT x.personId FROM CoopPerson x "
                                + "WHERE LOWER(x.lastName) = LOWER('" + lName + "') "
                                + "AND LOWER(x.firstName) = LOWER('" + fName + "') "
                                + "AND x.birthdate = ?1) "
                                + "AND c.husband.personId = : " + familyTree.get(2).getPersonId() + "").setParameter(1, parse.parse(bDate), TemporalType.TIMESTAMP).getResultList().get(0);
                        if (childSpouse.get(i).getFirstName() == null) {
                            familyTreeFamily.setWife(null);
                        }
                    } else {
                        familyTreeFamily = (CoopFamily) emf.createEntityManager().createQuery("SELECT c FROM CoopFamily c "
                                + "WHERE c.husband.personId = (SELECT x.personId FROM CoopPerson x "
                                + "WHERE LOWER(x.lastName) = LOWER('" + lName + "') "
                                + "AND LOWER(x.firstName) = LOWER('" + fName + "') "
                                + "AND x.birthdate = ?1) "
                                + "AND c.wife.personId = : " + familyTree.get(2).getPersonId() + "").setParameter(1, parse.parse(bDate), TemporalType.TIMESTAMP).getResultList().get(0);
                    }
                    familyTreeChild.setFamilyId(familyTreeFamily);
                    familyTreeChild.setPersonId(familyTreeChildren.get(i));
                    coopChildFacadeREST.create(familyTreeChild);
                } catch (Exception e) {
                    familyTreeChild.setFamilyId(familyTreefamily);
                    familyTreeChild.setPersonId(familyTreeChildren.get(i));
                    coopChildFacadeREST.create(familyTreeChild);
                }
            }
        }
    }

    public String save() {
        memberGetValues();
        familyTreeGetValues();
        contactsValues();
        addressGetValues();
        educationGetValues();
        employmentGetValues();
        businessGetValues();
        awardsValues();
        beanclear();
        return "/xhtml/mainMember.xhtml?faces-redirect=true";
    }

    public CoopEducInfo getAddtlEducInfo() {
        return addtlEducInfo;
    }

    public void setAddtlEducInfo(CoopEducInfo addtlEducInfo) {
        this.addtlEducInfo = addtlEducInfo;
    }

    public CoopBizInfo getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(CoopBizInfo businessInfo) {
        this.businessInfo = businessInfo;
    }

    public void memberDataLoad() {
        CoopMember cm = (CoopMember) emf.createEntityManager().createQuery("SELECT c FROM CoopMember c "
                + "WHERE c.memNo = : '" + selectedMember.getMemNo() + "'").getResultList().get(0);
        member.setMemNo(cm.getMemNo());
        member.setMemIdNo(cm.getMemIdNo());
        member.setScAcctno(cm.getScAcctno());
        selectedApplicant.setLastName(cm.getLastName());
        selectedApplicant.setFirstName(cm.getFirstName());
        selectedApplicant.setMiddleName(cm.getMiddleName());
        member.setNickname(cm.getNickname());
        selectedApplicant.setGender(cm.getGender());
        selectedApplicant.setBirthdate(cm.getBirthdate());
        member.setBirthplace(cm.getBirthplace());
        member.setResidenceType(cm.getResidenceType());
        selectedApplicant.setStreet(cm.getStreet());
        selectedApplicant.setBarangay(cm.getBarangay());
        selectedApplicant.setCityMun(cm.getCityMun());
        selectedApplicant.setRegion(cm.getRegion());
        selectedApplicant.setProvince(cm.getProvince());
        selectedApplicant.setCivilStatus(cm.getCivilStatus());
        selectedApplicant.setEmail(cm.getEmail());
        selectedApplicant.setContactNumber(cm.getContactNumber());
        member.setMemDate(cm.getMemDate());
        member.setMemStatRem(cm.getMemStatRem());
        member.setMemStatus(cm.getMemStatus());
        member.setStatusDate(new Date());
        selectedApplicant.setOuCode(cm.getOuCode());
        member.setTaxIdNo(cm.getTaxIdNo());
        selectedApplicant.setNationality(cm.getNationality());
        member.setZipCode(cm.getZipCode());
        selectedApplicant.setOccupation(cm.getOccupation());
        member.setReligion(cm.getReligion());
        member.setPPrefix(cm.getPPrefix());
        member.setSuffix(cm.getSuffix());
        member.setPersonStatus(cm.getPersonStatus());
        member.setBlood(cm.getBlood());
        member.setNotifyName(cm.getNotifyName());
        member.setNotifyAddress(cm.getNotifyAddress());
        member.setNotifyPhone(cm.getNotifyPhone());
        member.setNotifyRelation(cm.getNotifyRelation());
        member.setHeight(cm.getHeight());
        member.setWeight(cm.getWeight());
        member.setOwnedBusiness(cm.getOwnedBusiness());
    }

    private List<CoopAddlContactInfo> addlContInfoList;
    private DataModel<CoopAddlContactInfo> addlContInfoModel;

    public void contactsDataLoad() {
        Query SelectInAddlContInfo = emf.createEntityManager().createQuery("SELECT c FROM CoopAddlContactInfo c JOIN c.memNo d "
                + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'");
        addlContInfoList = SelectInAddlContInfo.getResultList();
        addlContInfoModel = new ListDataModel(addlContInfoList);

        if (addlContInfoModel.getRowCount() > 0) {
            for (int x = 0; x != addlContInfoModel.getRowCount(); x++) {
                if (isEditMode()) {
                    CoopAddlContactInfo q = (CoopAddlContactInfo) emf.createEntityManager().createQuery("SELECT c FROM CoopAddlContactInfo c JOIN c.memNo d "
                            + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'").getResultList().get(0);
                    coopAddlContactInfoFacadeREST.remove(q);
                } else {
                    CoopAddlContactInfo q = (CoopAddlContactInfo) emf.createEntityManager().createQuery("SELECT c FROM CoopAddlContactInfo c JOIN c.memNo d "
                            + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'").getResultList().get(x);
                    contactInfo.add(new Contacts(q.getContactType(), q.getContactDetail()));
                }
            }
        }
    }

    private List<CoopAddlAddress> addlAddrList;
    private DataModel<CoopAddlAddress> addlAddrModel;

    public void addressDataLoad() {
        Query CoopAddlAddress = emf.createEntityManager().createQuery("SELECT c FROM CoopAddlAddress c JOIN c.memNo d "
                + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'");
        addlAddrList = CoopAddlAddress.getResultList();
        addlAddrModel = new ListDataModel(addlAddrList);
        if (addlAddrModel.getRowCount() > 0) {
            for (int x = 0; x != addlAddrModel.getRowCount(); x++) {
                if (isEditMode()) {
                    CoopAddlAddress q = (CoopAddlAddress) emf.createEntityManager().createQuery("SELECT c FROM CoopAddlAddress c JOIN c.memNo d "
                            + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'").getResultList().get(0);
                    addlAddressFacadeREST.remove(q);
                } else {
                    CoopAddlAddress q = (CoopAddlAddress) emf.createEntityManager().createQuery("SELECT c FROM CoopAddlAddress c JOIN c.memNo d "
                            + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'").getResultList().get(x);
                    if (null != q.getAddressType()) {
                        switch (q.getAddressType()) {
                            case "If any":
                                setAdd2(true);
                                addrInfo.set(0, q);
                                break;
                            case "Previous":
                                setAdd3(true);
                                addrInfo.set(1, q);
                                break;
                            case "Provincial":
                                setAdd4(true);
                                addrInfo.set(2, q);
                                break;
                            case "Biz 1":
                                setEmplyd(true);
                                addrInfo.set(3, q);
                                break;
                            case "Biz 2":
                                setIfAnyEmpl(true);
                                addrInfo.set(4, q);
                                break;
                        }
                    }
                }
            }
        }
    }

    private List<CoopEducInfo> educationList;
    private DataModel<CoopEducInfo> educationModel;

    public void educationDataLoad() {
        Query CoopEducInfo = emf.createEntityManager().createQuery("SELECT c FROM CoopEducInfo c JOIN c.memNo d "
                + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'");
        educationList = CoopEducInfo.getResultList();
        educationModel = new ListDataModel(educationList);
        if (educationModel.getRowCount() > 0) {
            for (int x = 0; x != educationModel.getRowCount(); x++) {
                if (isEditMode()) {
                    CoopEducInfo q = (CoopEducInfo) emf.createEntityManager().createQuery("SELECT c FROM CoopEducInfo c JOIN c.memNo d "
                            + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'").getResultList().get(0);
                    educInfoFacadeREST.remove(q);
                } else {
                    CoopEducInfo q = (CoopEducInfo) emf.createEntityManager().createQuery("SELECT c FROM CoopEducInfo c JOIN c.memNo d "
                            + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'").getResultList().get(x);
                    if (x == 0) {
                        educInfo.set(x, q);
                    } else {
                        educInfo.add(q);
                    }
                }
            }
        }
    }

    private List<CoopEmplDtl> employmentList;
    private DataModel<CoopEmplDtl> employmentModel;

    public void employmentDataLoad() {
        Query CoopEmplDtl = emf.createEntityManager().createQuery("SELECT c FROM CoopEmplDtl c JOIN c.memNo d "
                + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'");
        employmentList = CoopEmplDtl.getResultList();
        employmentModel = new ListDataModel(employmentList);
        if (employmentModel.getRowCount() > 0) {
            for (int x = 0; x != employmentModel.getRowCount(); x++) {

                if (isEditMode()) {
                    CoopEmplDtl q = (CoopEmplDtl) emf.createEntityManager().createQuery("SELECT c FROM CoopEmplDtl c JOIN c.memNo d "
                            + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'").getResultList().get(0);
                    employmentDetailFacadeREST.remove(q);
                } else {
                    CoopEmplDtl q = (CoopEmplDtl) emf.createEntityManager().createQuery("SELECT c FROM CoopEmplDtl c JOIN c.memNo d "
                            + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'").getResultList().get(x);
                    if (x == 0 && q.getEmplrBizName().length() > 0) {
                        setEmplyd(true);
                        emplInfo.set(x, q);
                    } else if (x == 1 && q.getEmplrBizName().length() > 0) {
                        setIfAnyEmpl(true);
                        emplInfo.set(x, q);
                    }
                }

            }
        }
    }

    private List<CoopBizInfo> businessList;
    private DataModel<CoopBizInfo> businessModel;

    public void businessDataLoad() {
        Query CoopBizInfo = emf.createEntityManager().createQuery("SELECT c FROM CoopBizInfo c JOIN c.memNo d "
                + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'");
        businessList = CoopBizInfo.getResultList();
        businessModel = new ListDataModel(businessList);
        if (businessModel.getRowCount() > 0) {
            for (int x = 0; x != businessModel.getRowCount(); x++) {
                if (isEditMode()) {
                    CoopBizInfo q = (CoopBizInfo) emf.createEntityManager().createQuery("SELECT c FROM CoopBizInfo c JOIN c.memNo d "
                            + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'").getResultList().get(0);
                    bizInfoFacadeREST.remove(q);
                } else {
                    CoopBizInfo q = (CoopBizInfo) emf.createEntityManager().createQuery("SELECT c FROM CoopBizInfo c JOIN c.memNo d "
                            + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'").getResultList().get(x);
                    if (x == 0 && q.getBizName().length() > 0) {
                        member.setOwnedBusiness(true);
                        bizInfo.set(x, q);
                    } else if (x == 1 && q.getBizName().length() > 0) {
                        setIfAnyBiz(true);
                        bizInfo.set(x, q);
                    }
                }
            }
        }
    }

    private List<CoopAwards> awardsList;
    private DataModel<CoopAwards> awardsModel;

    public void awardsDataLoad() {
        Query CoopAwards = emf.createEntityManager().createQuery("SELECT c FROM CoopAwards c JOIN c.memNo d "
                + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'");
        awardsList = CoopAwards.getResultList();
        awardsModel = new ListDataModel(awardsList);
        if (awardsModel.getRowCount() > 0) {
            for (int x = 0; x != awardsModel.getRowCount(); x++) {
                if (isEditMode()) {
                    CoopAwards q = (CoopAwards) emf.createEntityManager().createQuery("SELECT c FROM CoopAwards c JOIN c.memNo d "
                            + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'").getResultList().get(0);
                    awardsFacadeREST.remove(q);
                } else {
                    CoopAwards q = (CoopAwards) emf.createEntityManager().createQuery("SELECT c FROM CoopAwards c JOIN c.memNo d "
                            + "WHERE d.memNo = : '" + selectedMember.getMemNo() + "'").getResultList().get(x);
                    if (x == 0) {
                        awardsInfo.set(x, q);
                    } else {
                        awardsInfo.add(q);
                    }
                }
            }
        }
    }

    private List<CoopPerson> familyTreeList;
    private DataModel<CoopPerson> familyTreeModel;
    CoopFamily familyTreeFamily;

    public void familyTreeDataLoad() {

        if (!isEditMode()) {
            loadFather();
            loadMother();
            loadSpouse();
        } else {
            try {
                familyTreeFamily = (CoopFamily) emf.createEntityManager().createQuery("SELECT d FROM CoopChild c JOIN c.familyId d "
                        + "WHERE c.personId.memNo.memNo = : '" + selectedMember.getMemNo() + "' ").getResultList().get(0);
            } catch (Exception e) {
                loadFather();
                loadMother();
                loadSpouse();
            }
        }

        loadSibling();
        loadChild();
    }

    public void loadFather() {
        //Father
        try {
            CoopPerson father = (CoopPerson) emf.createEntityManager().createQuery("SELECT c FROM CoopPerson c "
                    + "WHERE c.personId IN (SELECT n.husband.personId FROM CoopFamily n "
                    + "WHERE n.familyId IN (SELECT x.familyId.familyId FROM CoopChild x "
                    + "WHERE x.personId.memNo.memNo = : '" + selectedMember.getMemNo() + "'))").getResultList().get(0);
            familyTree.set(0, father);
        } catch (Exception e) {
            familyTree.get(0).setPersonId(0);
        }
    }

    public void loadMother() {
        //Mother
        try {
            CoopPerson mother = (CoopPerson) emf.createEntityManager().createQuery("SELECT c FROM CoopPerson c "
                    + "WHERE c.personId IN (SELECT n.wife.personId FROM CoopFamily n "
                    + "WHERE n.familyId IN (SELECT x.familyId.familyId FROM CoopChild x "
                    + "WHERE x.personId.memNo.memNo = : '" + selectedMember.getMemNo() + "'))").getResultList().get(0);
            familyTree.set(1, mother);
        } catch (Exception e) {
            familyTree.get(1).setPersonId(0);
        }
    }

    public void loadSpouse() {
        //Spouse
        String spouseSQL = "SELECT c FROM CoopPerson c ";
        if (selectedMember.getGender() == 'M') {
            spouseSQL += "WHERE c.personId IN (SELECT n.wife.personId FROM CoopFamily n ";
            spouseSQL += "WHERE n.familyId IN (SELECT x.familyId FROM CoopFamily x ";
            spouseSQL += "WHERE x.husband.memNo.memNo = : '" + selectedMember.getMemNo() + "'))";
        } else {
            spouseSQL += "WHERE c.personId IN (SELECT n.husband.personId FROM CoopFamily n ";
            spouseSQL += "WHERE n.familyId IN (SELECT x.familyId FROM CoopFamily x ";
            spouseSQL += "WHERE x.wife.memNo.memNo = : '" + selectedMember.getMemNo() + "'))";
        }

        Query CoopFamilyTreeSpouse = emf.createEntityManager().createQuery(spouseSQL);
        familyTreeList = CoopFamilyTreeSpouse.getResultList();
        familyTreeModel = new ListDataModel(familyTreeList);
        if (familyTreeModel.getRowCount() > 0) {
            for (int x = 0; x != familyTreeModel.getRowCount(); x++) {
                CoopPerson spouse = (CoopPerson) emf.createEntityManager().createQuery(spouseSQL).getResultList().get(x);
                if (x == 0) {
                    familyTreeSpouses.set(0, spouse);
                } else {
                    familyTreeSpouses.add(spouse);
                }
            }
        }
    }

    public void loadSibling() {

        //Siblings
        String siblingSQL = "SELECT c FROM CoopPerson c ";
        siblingSQL += "WHERE c.personId IN (SELECT n.personId.personId FROM CoopChild n ";
        siblingSQL += "WHERE n.familyId.familyId IN (SELECT x.familyId FROM CoopFamily x ";
        if (familyTree.get(0).getPersonId() > 0 || familyTree.get(1).getPersonId() > 0) {
            siblingSQL += "WHERE ";
        }
        if (familyTree.get(0).getPersonId() > 0) {
            siblingSQL += "x.husband.personId = " + familyTree.get(0).getPersonId() + " ";
        }
        if (familyTree.get(0).getPersonId() > 0 && familyTree.get(1).getPersonId() > 0) {
            siblingSQL += "AND ";
        }
        if (familyTree.get(1).getPersonId() > 0) {
            siblingSQL += "x.wife.personId = " + familyTree.get(1).getPersonId() + " ";
        }
        siblingSQL += ")) ";
        if (!isEditMode()) {
            siblingSQL += "AND c.personId NOT IN (SELECT q.personId FROM CoopPerson q ";
            siblingSQL += "WHERE q.memNo.memNo = : '" + selectedMember.getMemNo() + "')";
        }

        Query CoopFamilyTreeSiblings = emf.createEntityManager().createQuery(siblingSQL);
        CoopChild familyID = null;
        familyTreeList = CoopFamilyTreeSiblings.getResultList();
        familyTreeModel = new ListDataModel(familyTreeList);
        if (familyTreeModel.getRowCount() > 0) {
            try {//get familyID of the member
                familyID = (CoopChild) emf.createEntityManager().createQuery("SELECT c FROM CoopChild c "
                        + "WHERE c.personId.memNo.memNo = : '" + selectedMember.getMemNo() + "'").getResultList().get(0);
            } catch (Exception e) {
            }
            for (int x = 0; x != familyTreeModel.getRowCount(); x++) {
                if (isEditMode()) {
                    try {
                        CoopFamily familyCheck = (CoopFamily) emf.createEntityManager().createQuery("SELECT n FROM CoopFamily n "
                                + "WHERE n.familyId = (SELECT c.familyId.familyId FROM CoopChild c "
                                + "WHERE c.personId.memNo.memNo = : '" + selectedMember.getMemNo() + "')").getResultList().get(0);
                        if (!familyCheck.getHusband().getPersonId().toString().isEmpty() || !familyCheck.getWife().getPersonId().toString().isEmpty()) {
                            String siblingchildSQL = "SELECT n FROM CoopChild n ";
                            siblingchildSQL += "WHERE n.familyId.familyId IN (SELECT x.familyId FROM CoopFamily x ";
                            if (familyTree.get(0).getPersonId() > 0 || familyTree.get(1).getPersonId() > 0) {
                                siblingchildSQL += "WHERE ";
                            }
                            if (familyTree.get(0).getPersonId() > 0) {
                                siblingchildSQL += "x.husband.personId = " + familyTree.get(0).getPersonId() + " ";
                            }
                            if (familyTree.get(0).getPersonId() > 0 && familyTree.get(1).getPersonId() > 0) {
                                siblingchildSQL += "AND ";
                            }
                            if (familyTree.get(1).getPersonId() > 0) {
                                siblingchildSQL += "x.wife.personId = " + familyTree.get(1).getPersonId() + " ";
                            }
                            siblingchildSQL += ")";
                            CoopChild siblings = (CoopChild) emf.createEntityManager().createQuery(siblingchildSQL).getResultList().get(0);
                            coopChildFacadeREST.remove(siblings);
                        }
                    } catch (Exception e) {
                        try {
                            CoopChild siblings = (CoopChild) emf.createEntityManager().createQuery("SELECT n FROM CoopChild n "
                                    + "WHERE n.familyId.familyId = : " + familyID.getFamilyId().getFamilyId() + " ").getResultList().get(0);
                            coopChildFacadeREST.remove(siblings);
                        } catch (Exception e1) {
                            break;
                        }
                    }
                } else {
                    try {
                        //select all siblings
                        CoopFamily familyCheck = (CoopFamily) emf.createEntityManager().createQuery("SELECT n FROM CoopFamily n "
                                + "WHERE n.familyId = (SELECT c.familyId.familyId FROM CoopChild c "
                                + "WHERE c.personId.memNo.memNo = : '" + selectedMember.getMemNo() + "')").getResultList().get(0);
                        if (!familyCheck.getHusband().getPersonId().toString().isEmpty() || !familyCheck.getWife().getPersonId().toString().isEmpty()) {
                            CoopPerson siblings = (CoopPerson) emf.createEntityManager().createQuery(siblingSQL).getResultList().get(x);
                            if (x == 0) {
                                familyTreeSiblings.set(x, siblings);
                            } else {
                                familyTreeSiblings.add(siblings);
                            }
                        }
                    } catch (Exception e) {
                        try {
                            //select sibling if parents are null
                            CoopPerson siblings = (CoopPerson) emf.createEntityManager().createQuery("SELECT n.personId FROM CoopChild n "
                                    + "WHERE n.personId.personId <> (SELECT x.personId FROM CoopPerson x "
                                    + "WHERE x.memNo.memNo = : '" + selectedMember.getMemNo() + "') "
                                    + "AND n.familyId.familyId = (SELECT c.familyId.familyId FROM CoopChild c "
                                    + "WHERE c.personId.memNo.memNo = : '" + selectedMember.getMemNo() + "')").getResultList().get(x);
                            if (x == 0) {
                                familyTreeSiblings.set(x, siblings);
                            } else {
                                familyTreeSiblings.add(siblings);
                            }
                        } catch (Exception e1) {
                            break;
                        }
                    }
                }
            }
        }
    }

    public void loadChild() {
        //Child
        String childSQL = "SELECT c FROM CoopPerson c ";
        childSQL += "WHERE c.personId IN (SELECT n.personId.personId FROM CoopChild n ";
        childSQL += "WHERE n.familyId.familyId IN (SELECT d.familyId FROM CoopFamily d ";
        if (selectedMember.getGender() == 'M') {
            childSQL += "WHERE d.husband.memNo.memNo = : '" + selectedMember.getMemNo() + "'))";
        } else {
            childSQL += "WHERE d.wife.memNo.memNo = : '" + selectedMember.getMemNo() + "'))";
        }

        Query CoopFamilyTreeChildren = emf.createEntityManager().createQuery(childSQL);
        familyTreeList = CoopFamilyTreeChildren.getResultList();
        familyTreeModel = new ListDataModel(familyTreeList);
        if (familyTreeModel.getRowCount() > 0) {
            for (int x = 0; x != familyTreeModel.getRowCount(); x++) {
                if (isEditMode()) {
                    String ChildchildSQL = "SELECT n FROM CoopChild n ";
                    ChildchildSQL += "WHERE n.familyId.familyId IN (SELECT d.familyId FROM CoopFamily d ";
                    if (selectedMember.getGender() == 'M') {
                        ChildchildSQL += "WHERE d.husband.memNo.memNo = : '" + selectedMember.getMemNo() + "')";
                    } else {
                        ChildchildSQL += "WHERE d.wife.memNo.memNo = : '" + selectedMember.getMemNo() + "')";
                    }
                    CoopChild children = (CoopChild) emf.createEntityManager().createQuery(ChildchildSQL).getResultList().get(0);
                    coopChildFacadeREST.remove(children);
                } else {
                    CoopPerson children = (CoopPerson) emf.createEntityManager().createQuery(childSQL).getResultList().get(x);

                    String childrenSpouseSQL = "SELECT ";
                    if (selectedMember.getGender() == 'M') {
                        childrenSpouseSQL += "c.familyId.wife ";
                    } else {
                        childrenSpouseSQL += "c.familyId.husband ";
                    }
                    childrenSpouseSQL += "FROM CoopChild c ";
                    childrenSpouseSQL += "WHERE c.personId.personId = " + children.getPersonId() + " ";
                    if (selectedMember.getGender() == 'M') {
                        childrenSpouseSQL += "AND c.familyId.husband.memNo.memNo = : '" + selectedMember.getMemNo() + "'";
                    } else {
                        childrenSpouseSQL += "AND c.familyId.wife.memNo.memNo = : '" + selectedMember.getMemNo() + "'";
                    }
                    try {
                        CoopPerson childrenSpouse = (CoopPerson) emf.createEntityManager().createQuery(childrenSpouseSQL).getResultList().get(0);
                        if (x == 0) {
                            familyTreeChildren.set(x, children);
                            getChildSpouse().set(x, childrenSpouse);
                            getChildSpouseVal().set(x, childrenSpouse.getFirstName() + ";" + childrenSpouse.getLastName() + ";" + childrenSpouse.getBirthdate());
                        } else {
                            familyTreeChildren.add(children);
                            getChildSpouse().add(childrenSpouse);
                            getChildSpouseVal().add(childrenSpouse.getFirstName() + ";" + childrenSpouse.getLastName() + ";" + childrenSpouse.getBirthdate());
                        }
                    } catch (Exception e) {
                    }

                }
            }
        }
    }

    public void loadData() {
        member = new CoopMember();
        selectedApplicant = new CoopApplicant();
        contactInfo = new ArrayList<>();
        addrInfo = new ArrayList<>();
        emplInfo = new ArrayList<>();
        bizInfo = new ArrayList<>();
        educInfo = new ArrayList<>();
        awardsInfo = new ArrayList<>();
        familyTree = new ArrayList<>();
        familyTreeSpouses = new ArrayList<>();
        familyTreeSiblings = new ArrayList<>();
        familyTreeChildren = new ArrayList<>();
        setChildSpouse((ArrayList<CoopPerson>) new ArrayList());
        setChildSpouseVal((ArrayList<String>) new ArrayList());
        newSetArrayListMember();
        setAdd2(false);
        setAdd3(false);
        setAdd4(false);
        setEmplyd(false);
        setIfAnyEmpl(false);
        member.setOwnedBusiness(false);
        setIfAnyBiz(false);
        memberDataLoad();
        contactsDataLoad();
        addressDataLoad();
        educationDataLoad();
        employmentDataLoad();
        businessDataLoad();
        awardsDataLoad();
        familyTreeDataLoad();
        setEditMode(true);
    }

    public String addloadData() {
        setEditMode(false);
        loadData();
        return "/xhtml/addMember.xhtml";
    }

    public String viewloadData() {
        setEditMode(false);
        loadData();
        return "/xhtml/printMember.xhtml";
    }

    public String viewMember() {
        setEditMode(false);
        beanclear();
        return "/xhtml/viewMember.xhtml";
    }

    public String validateMember() {
        setEditMode(false);
        beanclear();
        return "/xhtml/validateApplicant.xhtml";
    }

    private void newSetArrayListMember() {
        addrInfo.add(new CoopAddlAddress());
        addrInfo.add(new CoopAddlAddress());
        addrInfo.add(new CoopAddlAddress());
        addrInfo.add(new CoopAddlAddress());
        educInfo.add(new CoopEducInfo());
        emplInfo.add(new CoopEmplDtl());
        emplInfo.add(new CoopEmplDtl());
        bizInfo.add(new CoopBizInfo());
        bizInfo.add(new CoopBizInfo());
        addrInfo.add(new CoopAddlAddress());
        awardsInfo.add(new CoopAwards());
        getMember().setPersonStatus(true);
        familyTree.add(new CoopPerson());
        familyTree.add(new CoopPerson());
        familyTree.add(new CoopPerson());
        familyTreeSpouses.add(new CoopPerson());
        familyTreeSiblings.add(new CoopPerson());
        familyTreeChildren.add(new CoopPerson());
        getChildSpouse().add(new CoopPerson());
        getChildSpouseVal().add(new String());
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    private String memId;

    public String getMemId() {
        return memId;
    }

    public void setMemId(String memId) {
        this.memId = memId;
    }

    public List<CoopMemberStatus> getMemStatList() {
        return memStatList;
    }

    public void setMemStatList(List<CoopMemberStatus> memStatList) {
        this.memStatList = memStatList;
    }

    public String civilStatText(char value) {
        String civilStat = "";
        if (value == 'S') {
            civilStat = "Single";
        } else if (value == 'M') {
            civilStat = "Married";
        } else if (value == 'P') {
            civilStat = "Separated";
        } else if (value == 'W') {
            civilStat = "Widow(er)";
        }
        return civilStat;
    }

    public String resiText(char value) {
        String resi = "";
        if (value == 'O') {
            resi = "Owned";
        } else if (value == 'R') {
            resi = "Rented";
        } else if (value == 'L') {
            resi = "Living with relatives";
        } else if (value == 'M') {
            resi = "Mortgage";
        }
        return resi;
    }

    public String conTypeText(char value) {
        String conType = "";
        if (value == 'E') {
            conType = "Email";
        } else if (value == 'M') {
            conType = "Mobile";
        } else if (value == 'T') {
            conType = "Telephone";
        } else if (value == 'F') {
            conType = "Fax";
        }
        return conType;
    }

    public List<CoopInfo> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<CoopInfo> infoList) {
        this.infoList = infoList;
    }

    public Integer getFatherMemNo() {
        return fatherMemNo;
    }

    public void setFatherMemNo(Integer fatherMemNo) {
        this.fatherMemNo = fatherMemNo;
    }

    public Integer getMotherMemNo() {
        return motherMemNo;
    }

    public void setMotherMemNo(Integer motherMemNo) {
        this.motherMemNo = motherMemNo;
    }

    public Integer getSpouseMemNo() {
        return spouseMemNo;
    }

    public void setSpouseMemNo(Integer spouseMemNo) {
        this.spouseMemNo = spouseMemNo;
    }

    public Integer getSiblingMemNo() {
        return SiblingMemNo;
    }

    public void setSiblingMemNo(Integer SiblingMemNo) {
        this.SiblingMemNo = SiblingMemNo;
    }

    public Integer getChildMemNo() {
        return childMemNo;
    }

    public void setChildMemNo(Integer childMemNo) {
        this.childMemNo = childMemNo;
    }
}
