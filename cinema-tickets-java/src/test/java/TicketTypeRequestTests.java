import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.*;

public class TicketTypeRequestTests {

    private final SeatReservationServiceImpl seatReservationService = new SeatReservationServiceImpl();
    private final TicketPaymentServiceImpl ticketPaymentService = new TicketPaymentServiceImpl();
    private final TicketServiceImpl ticketService = new TicketServiceImpl(ticketPaymentService, seatReservationService);

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    public TicketTypeRequestTests() {}

    @Test
    public void givenAccountIdIsInvalidWhenPurchaseTicketRequestThenThrowInvalidPurchaseException() {
        exceptionRule.expect(InvalidPurchaseException.class);
        exceptionRule.expectMessage("ERROR - Invalid account number may not continue with purchase.");

        TicketTypeRequest ticketTypeRequest = adultTicketRequest(10);
        ticketService.purchaseTickets( -1L, ticketTypeRequest );
    }

    @Test
    public void givenInfantsEnteredButNoAdultsWhenPurchaseTicketsThenThrowInvalidPurchaseException() {
        exceptionRule.expect(InvalidPurchaseException.class);
        exceptionRule.expectMessage("ERROR - Children and/or Infants have no supervision cannot purchase ticket.");

        TicketTypeRequest ticketTypeRequest = infantTicketRequest(10);
        ticketService.purchaseTickets(1L, ticketTypeRequest);
    }

    @Test
    public void givenChildrenEnteredButNoAdultsWhenPurchaseTicketsThenThrowInvalidPurchaseException() {
        exceptionRule.expect(InvalidPurchaseException.class);
        exceptionRule.expectMessage("ERROR - Children and/or Infants have no supervision cannot purchase ticket.");

        TicketTypeRequest ticketTypeRequest = childTicketRequest(10);
        ticketService.purchaseTickets(1L, ticketTypeRequest);
    }

    @Test
    public void givenInfantsAndChildrenEnteredButNoAdultsWhenPurchaseTicketsThenThrowInvalidPurchaseException() {
        exceptionRule.expect(InvalidPurchaseException.class);
        exceptionRule.expectMessage("ERROR - Children and/or Infants have no supervision cannot purchase ticket.");

        TicketTypeRequest ticketTypeRequestChild = childTicketRequest(10);
        TicketTypeRequest ticketTypeRequestInfant = infantTicketRequest(10);

        ticketService.purchaseTickets(1L, ticketTypeRequestChild, ticketTypeRequestInfant);
    }

    @Test
    public void givenNumberOfTicketEnteredIsOver20WhenPurchaseTicketsThenThrowInvalidPurchaseException() {
        exceptionRule.expect(InvalidPurchaseException.class);
        exceptionRule.expectMessage("ERROR - Cannot purchase over 20 tickets.");

        TicketTypeRequest ticketTypeRequestAdults = adultTicketRequest(15);
        TicketTypeRequest ticketTypeRequestChild = childTicketRequest(10);

        ticketService.purchaseTickets(1L , ticketTypeRequestAdults,ticketTypeRequestChild);
    }
}
