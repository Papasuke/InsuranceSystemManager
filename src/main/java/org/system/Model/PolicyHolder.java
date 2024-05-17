package org.system.Model;

import java.util.ArrayList;

public class PolicyHolder extends Account{
    private ArrayList<String> claimId;
    private ArrayList<String> dependentId;
    public PolicyHolder() {
        super();
        this.claimId = new ArrayList<>();
        this.dependentId = new ArrayList<>();
    }

    public PolicyHolder(String id, String username, String password, String email, String phone, String accType, ArrayList<String> claimIdList, ArrayList<String> dependentIdList) {
        super(id, username, password, email, phone, "POLICYHOLDER");
        this.claimId = claimIdList;
        this.dependentId = dependentIdList;
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
