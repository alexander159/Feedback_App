package app.survey.android.feedbackapp.model.ServerJSON;

import java.io.Serializable;

import app.survey.android.feedbackapp.util.ServerApi;

public class Patient implements Serializable {
    private long id;
    private long hospitalId;
    private String name;
    private int age;
    private String ipno;
    private String email;
    private String phone;
    private CameAs cameAs;
    private Sex sex;
    private String wardNo;
    private String beNo;

    public Patient(long id, long hospitalId, String name, int age, String ipno, String email, String phone, CameAs cameAs, Sex sex, String wardNo, String beNo) {
        this.id = id;
        this.hospitalId = hospitalId;
        this.name = name;
        this.age = age;
        this.ipno = ipno;
        this.email = email;
        this.phone = phone;
        this.cameAs = cameAs;
        this.sex = sex;
        this.wardNo = wardNo;
        this.beNo = beNo;
    }

    public static CameAs getCameAsFromString(String cameAsStr) {
        switch (cameAsStr) {
            case ServerApi.PatientJSON.CAME_AS_VALUE.PATIENT:
                return Patient.CameAs.Patient;
            case ServerApi.PatientJSON.CAME_AS_VALUE.RELATIVE:
                return Patient.CameAs.Relative;
            case ServerApi.PatientJSON.CAME_AS_VALUE.VISITOR:
                return Patient.CameAs.Visitor;
            default:
                return null;
        }
    }

    public static Sex getSexFromString(String sexStr) {
        switch (sexStr) {
            case ServerApi.PatientJSON.SEX_VALUE.MALE:
                return Patient.Sex.Male;
            case ServerApi.PatientJSON.SEX_VALUE.FEMALE:
                return Patient.Sex.Female;
            case ServerApi.PatientJSON.SEX_VALUE.OTHERS:
                return Patient.Sex.Others;
            default:
                return null;
        }
    }

    public long getId() {
        return id;
    }

    public long getHospitalId() {
        return hospitalId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getIpno() {
        return ipno;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public CameAs getCameAs() {
        return cameAs;
    }

    public Sex getSex() {
        return sex;
    }

    public String getWardNo() {
        return wardNo;
    }

    public String getBeNo() {
        return beNo;
    }

    public enum CameAs implements Serializable {Patient, Relative, Visitor}

    public enum Sex implements Serializable {Male, Female, Others}
}
