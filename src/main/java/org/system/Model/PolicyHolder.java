package org.system.Model;

import java.util.ArrayList;

public class PolicyHolder extends Account{
    private ArrayList<String> claimId;
    private ArrayList<String> dependentId;
    public PolicyHolder() {
        super();
        this.claimId = new ArrayList<>();
        this.dependentId = new ArrayList<>();
        this.setAccType(AccountType.POLICYHOLDER); // Set account type to POLICYHOLDER
    }

    public PolicyHolder(String username, String password, String email, String phone) {
        super(username, password, email, phone, AccountType.POLICYHOLDER);
        this.claimId = new ArrayList<>();
        this.dependentId = new ArrayList<>();
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

}
