package com.developintelligence.tutorials.jpa;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "durationGreaterThan1",
                query = "SELECT c FROM Course c WHERE c.duration > 1"),
        @NamedQuery(name = "getAll",
                query = "SELECT c FROM Course c")})
public class Course {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private int id;
  private String name, description;
  private int duration;

  @OneToOne
  private Instructor instructor;


  public Instructor getInstructor() {
    return instructor;
  }

  public void setInstructor(Instructor instructor) {
    this.instructor = instructor;
  }



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

  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  public int getDuration() {
    return duration;
  }
  public void setDuration(int duration) {
    this.duration = duration;
  }

  @Override
  public String toString() {
    return "Course{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", duration=" + duration +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Course course = (Course) o;

    if (duration != course.duration) return false;
    if (id != course.id) return false;
    if (description != null ? !description.equals(course.description) : course.description != null) return false;
    if (name != null ? !name.equals(course.name) : course.name != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + duration;
    return result;
  }
}
