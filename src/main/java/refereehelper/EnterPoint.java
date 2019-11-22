package refereehelper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hibernate.Session;
import refereehelper.models.User;
import refereehelper.utils.HibernateUtil;

public class EnterPoint {

    public static void main(String[] args) {
        System.out.println("Maven + Hibernate + MySQL");
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        User user = new User();

        user.setFirstName("Alexander");
        user.setLastName("Barchuk");

        ObjectMapper ow = new ObjectMapper();

        User user2 = (User) session.load(User.class, 1);
        try {
            String json = ow.writeValueAsString(user2);
            System.out.println("JSON = " + json);
        } catch (JsonMappingException e) {
            System.out.println("User not found");
        }
        catch (JsonProcessingException e) {
            // catch various errors
            e.printStackTrace();
        }
        session.save(user);
        session.getTransaction().commit();
    }

}

