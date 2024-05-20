package org.system.Model;

import java.util.ArrayList;

public class Dependent extends Account {
    private String policyHolderId;
    private String fullName;
    private int insuranceFee;
    private ArrayList<String> claimId;


    public Dependent(String id,String username, String password, String email, String phone, String accType, String policyHolderId) {
        super(id,username, password, email, phone,accType);
        this.policyHolderId = policyHolderId;
        this.claimId = new ArrayList<>();
    }

    public Dependent(String id, String username, String password, String email, String phone, String accType, String policyHolderId, String fullName, int insuranceFee) {
        super(id, username, password, email, phone, accType);
        this.policyHolderId = policyHolderId;
        this.fullName = fullName;
        this.insuranceFee = insuranceFee;
    }

    public Dependent(String id, String username, String password, String email, String phone, String accType, String policyHolderId, String fullName, int insuranceFee, ArrayList<String> claimId) {
        super(id, username, password, email, phone, accType);
        this.policyHolderId = policyHolderId;
        this.fullName = fullName;
        this.insuranceFee = insuranceFee;
        this.claimId = claimId;
    }

    // Getters and setters for policyHolderId and claimId
    public String getPolicyHolderId() {
        return policyHolderId;
    }

    public void setPolicyHolderId(String policyHolderId) {
        this.policyHolderId = policyHolderId;
    }

    public ArrayList<String> getClaimId() {
        return claimId;
    }

    public void setClaimId(ArrayList<String> claimId) {
        this.claimId = claimId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getInsuranceFee() {
        return insuranceFee;
    }

    public void setInsuranceFee(int insuranceFee) {
        this.insuranceFee = insuranceFee;
    }
}
