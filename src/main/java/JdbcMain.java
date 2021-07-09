import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcMain
{


    public static void main( String[] args ) throws ClassNotFoundException
    {
        // Register database driver
        Class.forName( "org.sqlite.JDBC" );

        // create a database connection
        try ( Connection connection = DriverManager.getConnection( "jdbc:sqlite:test.db"))      {

            Statement statement = connection.createStatement();
            statement.setQueryTimeout( 30 );  // set timeout to 30 sec.

            // drop table if exists
            statement.executeUpdate(
                    "DROP TABLE IF EXISTS person" );

            // create table
            statement.executeUpdate(
                    "CREATE TABLE person (id INTEGER PRIMARY KEY, firstname STRING, lastname STRING)" );

//            insert data into db

            statement.executeUpdate( "INSERT INTO person (firstname, lastname) VALUES('arthur', 'Kalule')" );
            statement.executeUpdate( "INSERT INTO person (firstname, lastname)  VALUES('Benna', 'Nakamaanya')" );
            statement.executeUpdate( "INSERT INTO person (firstname, lastname)  VALUES('John', 'Doe')" );

            // update data
            statement.executeUpdate( "UPDATE person SET firstname='Arthur' WHERE id=1" );

            // delete data
            statement.executeUpdate( "DELETE FROM person WHERE id=3" );

            // fetch all records from the person table
            ResultSet rs = statement.executeQuery( "select * from person" );

            // iterate over the ResultSet and display them
            while ( rs.next() )
            {
                // read the result set
                System.out.println( "id = " + rs.getInt( "id" )
                        + " FistName = " + rs.getString( "firstname" )
                        + " lastName = " + rs.getString( "lastname" ) );
            }

        }
        catch ( SQLException e )
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println( e.getMessage() );
        }

    }

}
