package com.example.application.data.entity;

import javax.persistence.Entity;

import com.example.application.data.AbstractEntity;
import java.time.LocalDate;

@Entity
public class Person extends AbstractEntity {

private String first_name;
private String last_name;
private String email;
private String phone;
private String street;
private String postal_code;
private String city;
private double social_security_no;
private int active_borrowed_books;
private int total_borrowed_books;
private String password;
private Boolean loancard;

  public String getFirst_name() { return first_name; }
  public void setFirst_name(String first_name) { this.first_name = first_name; }
  public String getLast_name() { return last_name; }
  public void setLast_name(String last_name) { this.last_name = last_name; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }
  public String getStreet() { return street; }
  public void setStreet(String street) { this.street = street; }
  public String getPostal_code() { return postal_code; }
  public void setPostal_code(String postal_code) { this.postal_code = postal_code; }
  public String getCity() { return city; }
  public void setCity(String city) { this.city = city; }
  public double getSocial_security_no() { return social_security_no; }
  public void setSocial_security_no(double social_security_no) { this.social_security_no = social_security_no; }
  public int getActive_borrowed_books() { return active_borrowed_books; }
  public void setActive_borrowed_books(int active_borrowed_books) { this.active_borrowed_books = active_borrowed_books; }
  public int getTotal_borrowed_books() { return total_borrowed_books; }
  public void setTotal_borrowed_books(int total_borrowed_books) { this.total_borrowed_books = total_borrowed_books; }
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
  public Boolean getLoancard() { return loancard; }
  public void setLoancard(Boolean loancard) { this.loancard = loancard; }


}
