package edu.badpals;

import edu.badpals.model.Card;
import edu.badpals.model.Course;
import edu.badpals.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Card card1 = new Card("A123", "eso", null);
            Card card2 = new Card("B456", "bac", null);

            Course course = new Course("Mathematics 101", null, null);

            Student student1 = new Student("Juan Pérez", "12345678A", "666666666", card1);
            Student student2 = new Student("Ana Gómez", "87654321B", "777777777", card2);

            student1.getCourses().add(course);
            student2.getCourses().add(course);
            course.getStudents().add(student1);
            course.getStudents().add(student2);

            em.persist(card1);
            em.persist(card2);
            em.persist(course);
            em.persist(student1);
            em.persist(student2);

            em.getTransaction().commit();

            System.out.println("Estudiantes creados:");
            System.out.println(student1);
            System.out.println(student2);

            em.getTransaction().begin();
            em.remove(student1);
            em.getTransaction().commit();

            System.out.println("Estudiante 1 eliminado. ¿Qué pasa con su tarjeta?");
            System.out.println("Card 1: " + card1);

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}