package dev.zheng.entities;

import java.util.Objects;

public class User {
    private int id;
    private String fname;
    private String lname;

    private String userName;
    private String password;
    private UserTitle title;
    private boolean approved ;

    public User(int id, String fname, String lname, String userName, String password, UserTitle title, boolean approved) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.userName = userName;
        this.password = password;
        this.title = title;
        this.approved = approved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserTitle getTitle() {
        return title;
    }

    public void setTitle(UserTitle title) {
        this.title = title;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", userName='" + userName + '\'' +
                ", title=" + title +
                ", approved=" + approved +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && approved == user.approved && fname.equals(user.fname) && lname.equals(user.lname) && userName.equals(user.userName) && password.equals(user.password) && title == user.title;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fname, lname, userName, password, title, approved);
    }
}
