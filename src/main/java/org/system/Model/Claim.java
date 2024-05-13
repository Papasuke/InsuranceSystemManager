package org.system.Model;

public class Claim {
    private String insuredPerson;

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
