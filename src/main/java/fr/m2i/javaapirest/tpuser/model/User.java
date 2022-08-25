
package fr.m2i.javaapirest.tpuser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="utilisateurs")
public class User {
    
    @Id 
   @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name="lastname",length=100, nullable=false)
    private String lastname;
    @Column(name="firstname",length=100, nullable=false)
    private String firstname;
    @Column(name="role",columnDefinition="enum('normal','admin')", nullable=false)
    private String role;
    @Column(name="email", length=100,unique=true, nullable=false)
    private String email;
    
    @Column(name="password",length=40, nullable=false)
    @JsonIgnore
    private String password;
    
    public User() {
        
    }

    public User(String lastname, String firstname, String role, String email, String password) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void copy(User userData){
        
        if(userData.getFirstname() != null && !"".equals(userData.getFirstname())){
            this.firstname = userData.getFirstname();
        }
        
        if(userData.getLastname()!= null && !"".equals(userData.getLastname())){
            this.lastname = userData.getLastname();
        }
        
        if(userData.getEmail()!= null && !"".equals(userData.getEmail())){
            this.email = userData.getEmail();
        }
        
        if(userData.getRole()!= null && !"".equals(userData.getRole())){
            this.role = userData.getRole();
        }
        
        if(userData.getPassword()!= null && !"".equals(userData.getPassword())){
            this.password = userData.getPassword();
        }
    }
}
