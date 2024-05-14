package org.system.Model;

import java.util.Date;

public class Claim {
    private String claimId;
    private String insuredPerson;
    private Date createDate;
    private Date examDate;
    private String bankName;
    private String bankAccount;
    private String getBankAccountName;
    private String description;
    private String status;

    public Claim(String claimId, String insuredPerson, Date createDate, Date examDate, String bankName, String bankAccount, String getBankAccountName, String description, String status) {
        this.claimId = claimId;
        this.insuredPerson = insuredPerson;
        this.createDate = createDate;
        this.examDate = examDate;
        this.bankName = bankName;
        this.bankAccount = bankAccount;
        this.getBankAccountName = getBankAccountName;
        this.description = description;
        this.status = status;
    }

    public String getClaimId() {
        return claimId;
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

    public String getBankAccount() {
        return bankAccount;
    }

    public String getGetBankAccountName() {
        return getBankAccountName;
    }

    public String getDescription() {
        return description;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
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

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setGetBankAccountName(String getBankAccountName) {
        this.getBankAccountName = getBankAccountName;
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

    @Override
    public String toString() {
        return STR."Claim{claimId='\{claimId}\{'\''}, insuredPerson='\{insuredPerson}\{'\''}, createDate=\{createDate}, examDate=\{examDate}, bankName='\{bankName}\{'\''}, bankAccount='\{bankAccount}\{'\''}, getBankAccountName='\{getBankAccountName}\{'\''}, description='\{description}\{'\''}, status=\{status}\{'}'}";
    }
}
