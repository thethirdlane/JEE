package com.developintelligence.tutorials.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class PlayWithJPA {
	public static void main(String[] args) {
		int courseId = 0;
		int duration = 5;

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("TrainingCatalog");
		EntityManager em = emf.createEntityManager();

		// create a course
		Course course = new Course();
		courseId = (int) System.currentTimeMillis();
		course.setDescription("Test Course");
		course.setDuration(3);
		course.setName("JPA 301");

		Instructor instructor = new Instructor();
		instructor.setEmail("kelby@developintelligence.com");
		instructor.setName("Kelby Zorgdrager");

		course.setInstructor(instructor);
		instructor.setCourse(course);

		// store the course
		em.getTransaction().begin();
		em.persist(course);
		em.persist(instructor);
		em.getTransaction().commit();

		courseId = course.getId();
		// find the course
		em.getTransaction().begin();
		Course tmpCourse = em.find(Course.class, courseId);
		System.out.println("Found the course: " + tmpCourse);

		System.out.println(tmpCourse == course);

		em.close();
	}

}
