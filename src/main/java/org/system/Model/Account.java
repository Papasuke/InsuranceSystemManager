package org.system.Model;

public class Account {
    private String id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String accType;

    public Account() {};

    public Account(String id, String username, String password, String email, String phone, String accType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.accType = accType;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return STR."Account{id='\{id}\{'\''}, username='\{username}\{'\''}, password='\{password}\{'\''}, email='\{email}\{'\''}, phone='\{phone}\{'\''}, accType='\{accType}\{'\''}\{'}'}";
    }

}
