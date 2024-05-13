package org.system.Model;

import java.time.LocalDate;

public class Claim {
    private String claimId;
    private String insuredPerson;
    private LocalDate createDate;
    private LocalDate examDate;
    private String bankName;
    private String bankAccount;
    private String getBankAccountName;



    public Claim(String insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public void setInsuredPerson(String insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public String getInsuredPerson() {
        return insuredPerson;
    }
}
