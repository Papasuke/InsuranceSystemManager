package org.system.Model;

import java.util.ArrayList;

public class PolicyHolder extends Account{
    private String fullName;
    private int insuranceFee;
    private ArrayList<String> claimId;
    private ArrayList<String> dependentId;
    public PolicyHolder() {
        super();
        this.claimId = new ArrayList<>();
        this.dependentId = new ArrayList<>();
    }

    public PolicyHolder(String id, String username, String password, String email, String phone, String accType, String fullName, int insuranceFee) {
        super(id, username, password, email, phone, accType);
        this.fullName = fullName;
        this.insuranceFee = insuranceFee;
    }

    public PolicyHolder(String id, String username, String password, String email, String phone, String accType, String fullName, int insuranceFee, ArrayList<String> claimId, ArrayList<String> dependentId) {
        super(id, username, password, email, phone, accType);
        this.fullName = fullName;
        this.insuranceFee = insuranceFee;
        this.claimId = claimId;
        this.dependentId = dependentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public ArrayList<String> getClaimId() {
        return claimId;
    }

    public void setClaimId(ArrayList<String> claimId) {
        this.claimId = claimId;
    }

    public ArrayList<String> getDependentId() {
        return dependentId;
    }

    public void setDependentId(ArrayList<String> dependentId) {
        this.dependentId = dependentId;
    }
    public void addClaimId(String claimId) {
        this.claimId.add(claimId);
    }

    public void addDependentId(String dependentId) {
        this.dependentId.add(dependentId);
    }

    public int getInsuranceFee() {
        return insuranceFee;
    }

    public void setInsuranceFee(int insuranceFee) {
        this.insuranceFee = insuranceFee;
    }
}
