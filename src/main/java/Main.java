import model.Person;
import model.Person.PersonBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;


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

    public static void main( String[] args)
    {
        Person person = new PersonBuilder( "Arthur", "Kalule" ).withEmail( "kalulearthur@gmail.com" ).build();
        System.out.println( person );
        System.out.println( "Hello Hibernate" );
    }
}
