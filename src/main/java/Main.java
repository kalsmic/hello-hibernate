import model.Person;
import model.Person.PersonBuilder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class Main
{
    // re-usable reference to a session Factory
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory()
    {
        // create a standard registry
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();

    }

    private static int save(Person person){

        // open a session
        Session session = sessionFactory.openSession();

        // begin a transaction.
        session.beginTransaction();

        // use the session to save the person object
        int id = (int) session.save( person );

        // commit the transaction
        session.getTransaction().commit();

        // close the session
        session.close();
        return id;

    }


    public static List<Person> fetchAll(){

        // open a session
        Session session = sessionFactory.openSession();

        // create a hibernate criteria object
        CriteriaQuery<Person> criteriaQuery = session.getCriteriaBuilder().createQuery( Person.class );
        criteriaQuery.from(Person.class);

        // get a list of all People objects according to Criteria object
        List<Person> people = session.createQuery( criteriaQuery ).getResultList();

        // close the session
        session.close();

        return people;
    }

    private static void update(Person person){

        // open a session
        Session session = sessionFactory.openSession();

        // begin a transaction.
        session.beginTransaction();

        // use the session to save the person object
        session.update( person );

        // commit the transaction
        session.getTransaction().commit();

        // close the session
        session.close();
    }


    private static void delete(Person person){

        // open a session
        Session session = sessionFactory.openSession();

        // begin a transaction.
        session.beginTransaction();

        // use the session to save the person object
        session.delete( person );

        // commit the transaction
        session.getTransaction().commit();

        // close the session
        session.close();
    }


    public static Person findPersonById(int id){

        // open a session
        Session session = sessionFactory.openSession();

        // retrieve person with specified id
        Person person = session.get(Person.class,id);

        // close the session
        session.close();

        return person;
    }


    public static void main( String[] args)
    {
        Person person1 = new PersonBuilder( "Arthur", "Kalule" ).withEmail( "kalulearthur@gmail.com" ).build();
        int person1Id = save(person1);
        Person person2 = new PersonBuilder( "John", "doe" ).withEmail( "johndoe@email.com" ).build();
        save(person2);

        // Display a list of People
        fetchAll().forEach( System.out::println );

        Person foundPerson = findPersonById( person1Id);
        assert person1.equals( foundPerson );

        if ( !person1.getFirstName().equals( "Arthur" ) ||  !person1.getLastName().equals( "Kalule" ) )
        {
            throw new AssertionError();
        }

        // update the person
        person1.setFirstName( "Benna" );
        person1.setLastName( "Nakamaanya" );
        update( person1 );

        if ( !person1.getFirstName().equals( "Benna" ) ||  !person1.getLastName().equals( "Nakamaanya" ) )
        {
            throw new AssertionError("Update failed");
        }

        delete( findPersonById( person1Id ) );

        if ( !( fetchAll().size() == 1 ) )
        {
            throw new AssertionError("person1 Object was not deleted");
        }











    }
}
