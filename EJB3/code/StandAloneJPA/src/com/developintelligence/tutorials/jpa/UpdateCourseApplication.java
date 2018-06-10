package com.developintelligence.tutorials.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class UpdateCourseApplication {
  public static void main(String[] args) {
    int courseId = 0;
    int duration = 5;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TrainingCatalog");
    EntityManager em = emf.createEntityManager();

    //create a course
    Course course = new Course();
    courseId = (int) System.currentTimeMillis();
    course.setDescription("Test Course");
    course.setDuration(3);
    course.setName("JPA 101");

    Instructor instructor = new Instructor();
    instructor.setEmail("kelby@developintelligence.com");
    instructor.setName("Kelby Zorgdrager");

    course.setInstructor(instructor);
    instructor.setCourse(course);

    //store the course
    em.getTransaction().begin();
    em.persist(course);
    em.persist(instructor);
    em.getTransaction().commit();

    courseId = course.getId();
    //find the course
    em.getTransaction().begin();
    Course tmpCourse = em.find(Course.class, courseId);
    System.out.println("Found the course: " + tmpCourse);

    System.out.println(tmpCourse == course);

    //update the course
    tmpCourse.setDuration(duration);
    //commit it to db
    em.getTransaction().commit();


    //select all courses
    Query query = em.createQuery("select c from Course c");
    List<Course> courses = query.getResultList();
    for (Course c : courses) {
      System.out.println(c);
    }
    //select all courses whose duration > 1
    query = em.createQuery("select c.name, c.description from Course c where c.duration > 1");
    List l = query.getResultList();
    System.out.println("Name and description: ");

    Object [] o = l.toArray();
    for (Object c : o) {
      System.out.print(((Object []) c)[0] + " " + ((Object []) c)[0].getClass());
      System.out.print(((Object []) c)[1] + " " + ((Object []) c)[1].getClass());

    }


    //select all courses whose duration > 1
    query = em.createQuery("select count(c.name) from Course c where c.duration > 1");
    System.out.println(query.getSingleResult());

    //select courses whose name is like Java
    query = em.createQuery("select c from Course c where c.name like '%Java%'");
    courses = query.getResultList();
    for (Course c : courses) {
      System.out.println(c);
    }

    System.out.println("Native queries...");

    /** Start NATIVE Queries **/
    //select all courses
    query = em.createNativeQuery("select * from Course");
    List oCourses = query.getResultList();
    for (Object c : oCourses) {
      System.out.println(c);
      System.out.println(c.getClass());
    }

    //select all courses whose duration > 1
    query = em.createNativeQuery("select * from Course where duration > 1");
    oCourses = query.getResultList();
    for (Object c : oCourses) {
      System.out.println(c);
      System.out.println(c.getClass());
    }

    //select number of courses whose duration > 1
    query = em.createNativeQuery("select count(name) from course where duration > 1");
    System.out.println(query.getSingleResult());


    //select courses whose name is like Java
    query = em.createNativeQuery("select * from Course where name like '%Java%'");
    oCourses = query.getResultList();
    for (Object c : oCourses) {
      System.out.println(c);
    }

    /** NAMED queries **/
    System.out.println("Named queries...");
    query = em.createNamedQuery("getAll");
    courses = query.getResultList();
    for (Course c : courses) {
      System.out.println(c);
    }

    query = em.createNamedQuery("durationGreaterThan1");
    courses = query.getResultList();
    for (Course c : courses) {
      System.out.println(c);
    }

    em.close();
  }


}




