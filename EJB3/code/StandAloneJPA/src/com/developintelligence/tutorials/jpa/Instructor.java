package com.developintelligence.tutorials.jpa;

import javax.persistence.*;

@Entity
public class Instructor {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private String name, email;

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  @OneToOne
  private Course course;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Instructor that = (Instructor) o;

    if (id != that.id) return false;
    if (email != null ? !email.equals(that.email) : that.email != null) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Instructor{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
  }
}
