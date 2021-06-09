package com.example.application.data.entity;


import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;
import javax.annotation.Nullable;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Table(name = "person", schema = "agile_library")
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_persons")
    private Integer idPersons;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @Column(name="street")
    private String street;

    @Column(name="postal_code")
    private String postalCode;

    @Column(name="city")
    private String city;

    @Column(name="social_security_no")
    private String socialSecurityNo;

    @Column(name="active_borrowed_books")
    private String activeBorrowedBooks;

    @Column(name="total_borrowed_books")
    private String totalBorrowedBooks;

    @Column(name="password")
    private String password;

    @Column(name="loancard")
    private Boolean loancard;

    @Column(name="role_id")
    private int roleId;


    @Transient
    private Role role;

    public Person() {
    }

    public Person(String first_name,
                  String last_name,
                  String email,
                  String phone,
                  String street,
                  String postal_code,
                  String city,
                  String socialSecurityNo,
                  String password,
                  Boolean loancard,
                  Role role)
    {
        this.firstName = first_name;
        this.lastName = last_name;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.postalCode = postal_code;
        this.city = city;
        this.socialSecurityNo = socialSecurityNo;
        this.password = password;
        this.loancard = loancard;
        this.roleId = role.getRole_id();
    }

    public Integer getIdPersons() {
        return idPersons;
    }

    public void setIdPersons(Integer idPersons) {
        this.idPersons = idPersons;
    }

    public Role getRole() throws NoSuchElementException {
        if ( role == null ) {
            determineRole();
        }
        return role;
    }

    //TODO -> kolla om de skall vara NotNUll??
    public void setRole(@Nullable Role role) {
        if ( role == null ) {
            determineRole();
        } else {
            this.role = role;
        }
    }

    private void determineRole() throws NoSuchElementException {
        this.role = Stream.of(Role.values()).filter(r -> r.getRole_id() == this.roleId).findFirst().get();
    }

    public int getRoleId() { return roleId; }
    public void setRoleId(int id_user_role) throws NoSuchElementException {
        this.roleId = id_user_role;
        determineRole();
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getSocialSecurityNo() { return socialSecurityNo; }
    public void setSocialSecurityNo(String socialSecurityNo) { this.socialSecurityNo = socialSecurityNo; }

    public String getActiveBorrowedBooks() { return activeBorrowedBooks; }
    public void setActiveBorrowedBooks(String activeBorrowedBooks) { this.activeBorrowedBooks = activeBorrowedBooks; }

    public String getTotalBorrowedBooks() { return totalBorrowedBooks; }
    public void setTotalBorrowedBooks(String totalBorrowedBooks) { this.totalBorrowedBooks = totalBorrowedBooks; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Boolean getLoancard() { return loancard; }
    public void setLoancard(Boolean loancard) { this.loancard = loancard; }

    public boolean checkPassword(String password) {
        return true;
        //return DigestUtils.sha1Hex(password).equals(this.password);

        //FIXME
        //TODO: Implementera lösenordskontroll.
    }


}
