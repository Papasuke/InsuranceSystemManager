package org.system.Model;

public abstract class Account {
    private String username;
    private String password;
    private String email;
    private String phone;
    public enum AccountType {
        POLICYHOLDER,
        DEPENDENT,
        SURVEYOR,
        MANAGER,
        ADMIN
    }
    private AccountType accType;

    public Account() {};

    public Account(String username, String password, String email, String phone, AccountType accType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.accType = accType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AccountType getAccType() {
        return accType;
    }

    public void setAccType(AccountType accType) {
        this.accType = accType;
    }

    public void setPolicyHolder() {
        this.accType = AccountType.POLICYHOLDER;
    }

    public void setDependent() {
        this.accType = AccountType.DEPENDENT;
    }

    public void setSurveyor() {
        this.accType = AccountType.SURVEYOR;
    }

    public void setManger() {
        this.accType = AccountType.MANAGER;
    }

    public void setAdmin() {
        this.accType = AccountType.ADMIN;
    }

}
