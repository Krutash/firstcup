package firstcup.ejb;

import firstcup.entity.FirstcupUser;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author utkro
 */
@Stateless
public class DukesBirthdayBean {

    @PersistenceContext
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private static final Logger logger = Logger.getLogger("firstcup.ejb.DukesBirthdayBean");

    public Double getAverageAgeDifference() {
        Double avgAgeDiff
                = (Double) em.createNamedQuery("findAverageAgeDifferenceOfAllFirstcupUsers")
                        .getSingleResult();
        logger.info("Average age difference is: " + avgAgeDiff);
        return avgAgeDiff;
    }

    public int getAgeDifference(Date date) {
        int ageDifference;
        Calendar theirBirthday = new GregorianCalendar();
        Calendar dukesBirthday = new GregorianCalendar(1995, Calendar.MAY, 23);
        theirBirthday.setTime(date);
        ageDifference = dukesBirthday.get(Calendar.YEAR)
                - theirBirthday.get(Calendar.YEAR);
        logger.info("Raw ageDifference is: " + ageDifference);

        if (dukesBirthday.before(theirBirthday) && (ageDifference > 0)) {
            ageDifference--;
        }
        FirstcupUser user = new FirstcupUser(date, ageDifference);
        em.persist(user);

        logger.info("Final ageDifference is: " + ageDifference);
        return ageDifference;
    }
}