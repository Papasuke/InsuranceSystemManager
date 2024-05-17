package org.system.Model;

import java.util.ArrayList;

public class Dependent extends Account {
    private String policyHolderId;
    private ArrayList<String> claimId;

    public Dependent(String id,String username, String password, String email, String phone, String accType, String policyHolderId) {
        super(id,username, password, email, phone,accType);
        this.policyHolderId = policyHolderId;
        this.claimId = new ArrayList<>();
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
}
