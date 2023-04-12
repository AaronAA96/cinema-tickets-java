package uk.gov.dwp.uc.pairtest.util;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import java.util.List;

public class SeatReservationCalculator {


    public static int calculateSeatsToAllocate(List<TicketTypeRequest> ticketTypeRequestList) {

        return ticketTypeRequestList.stream()
                .filter(ticketTypeRequest ->  ticketTypeRequest.getTicketType() != TicketTypeRequest.Type.INFANT)
                .mapToInt(TicketTypeRequest::getNoOfTickets).sum();
    }
}
