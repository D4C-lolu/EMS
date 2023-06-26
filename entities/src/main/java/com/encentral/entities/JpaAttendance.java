
package com.encentral.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.sql.Time;
import java.sql.Date;


@Entity
@Table(name = "attendance")
@NamedQueries({
        @NamedQuery(name = "JpaAttendance.findAll", query = "SELECT j FROM JpaAttendance j"),
        @NamedQuery(name="JpaAttendance.checkAlreadySignedIn", query="SELECT j FROM JpaAttendance j WHERE j.date =: currentDate AND j.user.userToken = currentUserToken "),
        @NamedQuery(name="JpaAttendance.getDaily",query="SELECT j FROM JpaAttendance j WHERE j.date =:currentDate"),
        @NamedQuery(name="JpaAttendance.getUser",query ="SELECT j FROM JpaAttendance j WHERE j.user.userId =:userId")
})
public class JpaAttendance implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "attendance_id",unique = true)
    private String attendanceId;

    @Basic(optional = false)
    @Column(name = "attendance_date")
    private Date date;

    @Column(name = "signin_time")
    private Time signInTime;

    @Column(name = "signout_time")
    private Time signOutTime;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = JpaUser.class)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private JpaUser user;

    public JpaAttendance() {
    }

    public JpaAttendance(String attendanceId, JpaUser user) {
        this.attendanceId = attendanceId;
        this.user = user;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Time signInTime) {
        this.signInTime = signInTime;
    }

    public Time getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(Time signOutTime) {
        this.signOutTime = signOutTime;
    }

    public JpaUser getUser() {
        return user;
    }

    public void setUser(JpaUser user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaAttendance)) return false;
        JpaAttendance that = (JpaAttendance) o;
        return Objects.equals(getAttendanceId(), that.getAttendanceId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttendanceId());
    }

    @Override
    public String toString() {
        return "JpaAttendance{" +
                "attendanceId=" + attendanceId +
                ", date=" + date +
                ", signInTime=" + signInTime +
                ", signOutTime=" + signOutTime +
                ", user=" + user +
                '}';
    }
}
