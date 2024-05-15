package org.system.Model;

import java.util.Date;

public class Claim {
    private int id;
    private String insuredPerson;
    private Date createDate;
    private Date examDate;
    private String bankName;
    private String bankAccountName;
    private String bankAccountNum;
    private int claimAmount;
    private String description;
    private String status;

    public Claim(int id, String insuredPerson, Date createDate, Date examDate, String bankName, String bankAccountName, String bankAccountNum, int claimAmount, String description, String status) {
        this.id = id;
        this.insuredPerson = insuredPerson;
        this.createDate = createDate;
        this.examDate = examDate;
        this.bankName = bankName;
        this.bankAccountName = bankAccountName;
        this.bankAccountNum = bankAccountNum;
        this.claimAmount = claimAmount;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(int claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getInsuredPerson() {
        return insuredPerson;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getExamDate() {
        return examDate;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public String getBankAccountNum() {
        return bankAccountNum;
    }

    public String getDescription() {
        return description;
    }


    public void setInsuredPerson(String insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public void setBankAccountNum(String bankAccountNum) {
        this.bankAccountNum = bankAccountNum;
    }

    public void setDescription(String description) {
        description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
