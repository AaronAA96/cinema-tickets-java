import org.junit.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.*;
import static uk.gov.dwp.uc.pairtest.util.SeatReservationCalculator.calculateSeatsToAllocate;

public class SeatReservationCalculatorTests {

    @Test
    public void givenAdultsAndChildrenEnteredWhenCalculatingSeatsThenReturnExpectedNumberOfSeats() {

        int expected = 15;

        TicketTypeRequest ticketTypeRequestChild = childTicketRequest(10);
        TicketTypeRequest ticketTypeRequestAdult = adultTicketRequest(5);

        List<TicketTypeRequest> ticketTypeRequestList = new ArrayList<>();

        ticketTypeRequestList.add(ticketTypeRequestChild);
        ticketTypeRequestList.add(ticketTypeRequestAdult);

        int actual = calculateSeatsToAllocate(ticketTypeRequestList);

        assertEquals(actual, expected);
    }

    @Test
    public void givenAdultsChildrenAndInfantsEnteredWhenCalculatingSeatsThenReturnExpectedNumberOfSeats() {

        int expected = 15;

        TicketTypeRequest ticketTypeRequestChild = childTicketRequest(10);
        TicketTypeRequest ticketTypeRequestAdult = adultTicketRequest(5);
        TicketTypeRequest ticketTypeRequestInfant = infantTicketRequest(2);

        List<TicketTypeRequest> ticketTypeRequestList = new ArrayList<>();

        ticketTypeRequestList.add(ticketTypeRequestInfant);
        ticketTypeRequestList.add(ticketTypeRequestChild);
        ticketTypeRequestList.add(ticketTypeRequestAdult);

        int actual = calculateSeatsToAllocate(ticketTypeRequestList);

        assertEquals(actual, expected);
    }


}
