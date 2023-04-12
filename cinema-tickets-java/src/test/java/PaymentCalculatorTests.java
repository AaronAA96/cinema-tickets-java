import org.junit.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.*;
import static uk.gov.dwp.uc.pairtest.util.PaymentCalculator.calculateTotalNumberToPay;

public class PaymentCalculatorTests {

    @Test
    public void givenInfantsChildrenAndAdultsEnteredWhenCalculatingTotalPriceThenReturnExpectedNumber() {

        int expected = 300;

        TicketTypeRequest ticketTypeRequestInfant = infantTicketRequest(5);
        TicketTypeRequest ticketTypeRequestChild = childTicketRequest(5);
        TicketTypeRequest ticketTypeRequestAdult = adultTicketRequest(10);

        List<TicketTypeRequest> ticketTypeRequestList = new ArrayList<>();

        ticketTypeRequestList.add(ticketTypeRequestInfant);
        ticketTypeRequestList.add(ticketTypeRequestChild);
        ticketTypeRequestList.add(ticketTypeRequestAdult);

        int actual = calculateTotalNumberToPay(ticketTypeRequestList);

        assertEquals(actual, expected);
    }

}
