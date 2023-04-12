package uk.gov.dwp.uc.pairtest.util;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import java.util.List;

public class PaymentCalculator {

    static final int ADULT_PRICE = 20;
    static final int CHILD_PRICE = 10;
    static final int INFANT_PRICE = 0;

    public static int calculateTotalNumberToPay(List<TicketTypeRequest> ticketTypeRequests) {

        int totalAmountToPay = 0;

        int numberOfAdultsTickets = numberOfTicketsPerType(ticketTypeRequests, TicketTypeRequest.Type.ADULT);
        int numberOfChildrenTickets =  numberOfTicketsPerType(ticketTypeRequests, TicketTypeRequest.Type.CHILD);
        int numberOfInfantTickets = numberOfTicketsPerType(ticketTypeRequests, TicketTypeRequest.Type.INFANT);

        totalAmountToPay += numberOfAdultsTickets * ADULT_PRICE;
        totalAmountToPay += numberOfChildrenTickets * CHILD_PRICE;
        totalAmountToPay += numberOfInfantTickets * INFANT_PRICE;

        return totalAmountToPay;
    }

    public static int numberOfTicketsPerType(List<TicketTypeRequest> ticketTypeRequests,
                                              TicketTypeRequest.Type type) {
        return ticketTypeRequests.stream()
                .filter(ticketTypeRequest ->  ticketTypeRequest.getTicketType() == type)
                .mapToInt(TicketTypeRequest::getNoOfTickets).sum();
    }

}
