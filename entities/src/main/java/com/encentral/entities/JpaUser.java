package com.encentral.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "JpaUser.findAll", query = "SELECT j FROM JpaUser j")
})
public class JpaUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private Long userId;

    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;

    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;

    @Basic(optional = false)
    @Column(name = "email", unique = true)
    private String email;

    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    @Basic(optional = false)
    @Column(name = "user_token", unique = true)
    private String userToken;

    @Column(name = "user_role", nullable = false)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<JpaAttendance> jpaAttendanceList;

    @Column(name = "date_added",nullable = false)
    private Date dateAdded;

    public JpaUser() {
    }


    public JpaUser(String firstName, String lastName, String email, String password, String userToken, Role role, List<JpaAttendance> jpaAttendanceList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userToken = userToken;
        this.role = role;
        this.jpaAttendanceList = jpaAttendanceList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<JpaAttendance> getJpaAttendanceList() {
        return jpaAttendanceList;
    }

    public void setJpaAttendanceList(List<JpaAttendance> jpaAttendanceList) {
        this.jpaAttendanceList = jpaAttendanceList;
    }

    public void markAttendance(JpaAttendance attendance){
        this.getJpaAttendanceList().add(attendance);
        attendance.setUser(this);
    }

    public void deleteAttendance(JpaAttendance attendance){
        attendance.setUser(null);
        this.getJpaAttendanceList().remove(attendance);
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaUser)) return false;
        JpaUser jpaUser = (JpaUser) o;
        return Objects.equals(getUserId(), jpaUser.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }

    @Override
    public String toString() {
        return "JpaUser{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                '}';
    }
}
