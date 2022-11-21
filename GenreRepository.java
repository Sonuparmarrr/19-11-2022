package com.junit.demo;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javax.persistence.Persistence;

import org.hibernate.HibernateException;

public class Main {
	private static EntityManagerFactory factory = null;
	private static EntityManager entityManager = null;

	
	public static void main(String[] args) {
		try {
			factory = Persistence.createEntityManagerFactory("HibernateDemo");
			
			entityManager = factory.createEntityManager();
			
			Actor a1 = new Actor("Jr N.T.R", "Tharak", 1985, null);
			Actor a2 = new Actor("Charan", "Ram Charak", 1983, null);
			
			Actor a3 = new Actor("Rajini", "Rajinikanth", 1960, null);
			Actor a4 = new Actor("Lalitha", "Jayalalitha", 1965, null);
			
			// 7.	Saving objects of type Actor to the database
			ActorRepository actorRepositoty = new ActorRepository(entityManager);
			
			actorRepositoty.save(a1);
			actorRepositoty.save(a2);
			
			actorRepositoty.save(a3);
			actorRepositoty.save(a4);
			
			
			Genre g1 = new Genre("Adventure",null);
			
			Genre g2 = new Genre("Humour Movies",null);
			
			//Genre g3 = new Genre("Humour Series",null);
			
			GenreRepository genreRepository = new GenreRepository(entityManager);
			
			genreRepository.save(g1);
			
			genreRepository.save(g2);
			
			//genRep.save(g3);
		
			List<Actor> actors1 = new ArrayList<>();
			actors1.add(a1);
			actors1.add(a2);
			
			List<Actor> actors2 = new ArrayList<>();
			actors2.add(a3);
			actors2.add(a4);
			
			Movie m1 = new Movie("R.R.R", 2022, actors1, g1);
			
			Movie m2 = new Movie("XYZ", 1985, actors2, g2);
			
			// 11.	adding Movie records to the database
			MovieRepository movieRepository = new MovieRepository(entityManager);
			
			movieRepository.save(m1);
			movieRepository.save(m2);
			
		
			// 8. look for objects in the database of type Actor by id
			
			Optional<Actor> a = actorRepositoty.findById(1L);
			
			System.out.println("Actor: " + a);
			
			
			// 9. search for objects in the Actor type database that 
			// were born after a certain year (i.e. the year is a method parameter)
			
			List<Actor> actors = actorRepositoty.findAllBornAfter(1982);
			
			actors.forEach((ac) -> System.out.println(ac));
			
			
			// 10.	look for objects in the database of the Actor type, the names 
			// of which end with the specified value of the String type object
			
			//select * from actors where last_name like '%ak';
			
			actors = actorRepositoty.findAllWithLastNameEndsWith("ak");
			actors.forEach(ac -> System.out.println(ac));
			
			//look for objects in the database of the Actor type, the names 
			// of which contain the specified value of the String type object
			actors = actorRepositoty.findAllWithLastNameWith("th");
			actors.forEach(ac -> System.out.println(ac));
			
			
			//12.	searching Movie record by title
			
			Movie movie = movieRepository.findMovieByName("R.R.R");
			System.out.println(movie);
			
			
			//13.	searching for Movie record by id
			Optional<Movie>	m = movieRepository.findById(2L);
			System.out.println(m);
			
			
			//14.	returning all Movie records
			List<Movie> movies = movieRepository.findAll();
			movies.forEach(mv -> System.out.println(mv));
			
			
			//15.	removing one Movie record from the database
			
			/*
			m = movieRepository.findById(2L);
			
			movieRepository.remove(m.get());
			
			movies = movieRepository.findAll();
			movies.forEach(mv -> System.out.println(mv));
			*/
			
			// 16.	removing all Movie records from the database
			
			//movieRepository.removeAll();
			
			
			// 17. Find all the movies with the actors
			
			List<Movie> moviesWithActors = movieRepository.findAllWithActors();
			
			moviesWithActors.forEach(mv -> System.out.println(mv));
			
			
			
		}
		catch(HibernateException e) {
			
			e.printStackTrace();
		}
		catch(Exception e) {
			
			
			e.printStackTrace();
		}
		finally {
			
			
			if(entityManager != null)
				entityManager.close();
			
			if(factory != null)
				factory.close();
		}


	}

	/*
	public static List<Genre> findGenreAll() {
	    return entityManager.createQuery("from genres", Genre.class).getResultList();
	}
	
	public static Optional<Genre> findGenreById(final long id) {
	    return Optional.ofNullable(entityManager.find(Genre.class, id));
	}

	public static List<Genre> findGenreAllByName(final String name) {
	    return entityManager.createQuery("SELECT g FROM genres WHERE name = :name", Genre.class)
	        .setParameter("name", name)
	        .getResultList();
	  }

	public static Genre saveGenre(final Genre genre) {
	    EntityTransaction transaction = null;
	    try {
	      transaction = entityManager.getTransaction();
	      if (!transaction.isActive()) {
	        transaction.begin();
	      }

	      entityManager.persist(genre);
	      transaction.commit();
	      return genre;
	    } catch (final Exception e) {
	      if (transaction != null) {
	        transaction.rollback();
	      }
	      return null;
	    }
	  }
	
	public static void removeGenre(final Genre genre) {
	    EntityTransaction transaction = null;
	    try {
	      transaction = entityManager.getTransaction();
	      if (!transaction.isActive()) {
	        transaction.begin();
	      }

	      entityManager.remove(genre);
	      transaction.commit();
	    } catch (final Exception e) {
	      if (transaction != null) {
	        transaction.rollback();
	      }
	    }
	  }

	public static void removeGenreAll() {
		
	}

	public static List<Movie> findMovieAll() {
	    return entityManager.createQuery("FROM movies ", Movie.class).getResultList();
	  }

	public static Optional<Movie> findMovieById(final long id) {
	    return Optional.ofNullable(entityManager.find(Movie.class, id));
	  }

	public static List<Movie> findMovieAllByName(String title) {
	    return entityManager.createQuery("SELECT m FROM movies m WHERE m.title = :title", Movie.class)
	        .setParameter("title", title)
	        .getResultList();
	  }

	public static Movie saveMovie(final Movie movie) {
	    EntityTransaction transaction = null;
	    try {
	      transaction = entityManager.getTransaction();
	      if (!transaction.isActive()) {
	        transaction.begin();
	      }

	      entityManager.persist(movie);
	      transaction.commit();
	      return movie;
	    } catch (final Exception e) {
	      if (transaction != null) {
	        transaction.rollback();
	      }
	      return null;
	    }
	  }

	public static void removeMovie(final Movie movie) {
	    EntityTransaction transaction = null;
	    try {
	      transaction = entityManager.getTransaction();
	      if (!transaction.isActive()) {
	        transaction.begin();
	      }

	      entityManager.remove(movie);
	      transaction.commit();
	    } catch (final Exception e) {
	      if (transaction != null) {
	        transaction.rollback();
	      }
	    }
	  }

	public static List<Movie> findMovieAllWithActors() {
	    return entityManager.createQuery("SELECT m FROM movies m LEFT JOIN fetch m.actors", Movie.class)
	        .getResultList();
	  }

	public static void removeMovieAll() {
	
	}
	
	public static List<Actor> findActorAll() {
		return null;
	}

	public static Optional<Actor> findActorById(final long id) {
		
		return null;
	}
	
	public static List<Actor> findActorAllByName(String name) {
		return null;
	}

	public static Actor saveActor(final Actor actor) {
		return null;
	}

	public static void removeActor(final Actor actor) {
		
	}

	public static void removeActorAll() {
		
	}*/
	
}
