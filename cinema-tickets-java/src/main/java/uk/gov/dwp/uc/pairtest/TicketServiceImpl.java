package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.util.SeatReservationCalculator;

import java.util.List;

import static uk.gov.dwp.uc.pairtest.util.PaymentCalculator.calculateTotalNumberToPay;
import static uk.gov.dwp.uc.pairtest.util.PaymentCalculator.numberOfTicketsPerType;
import static uk.gov.dwp.uc.pairtest.util.SeatReservationCalculator.calculateSeatsToAllocate;

public class TicketServiceImpl implements TicketService {

    private final TicketPaymentServiceImpl ticketPaymentService;
    private final SeatReservationServiceImpl seatReservationService;

    public TicketServiceImpl(TicketPaymentServiceImpl ticketPaymentService,
                             SeatReservationServiceImpl seatReservationService) {
        this.ticketPaymentService = ticketPaymentService;
        this.seatReservationService = seatReservationService;
    }

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {

        checkAccountIsValid(accountId);

        List<TicketTypeRequest> ticketTypeRequestList = List.of(ticketTypeRequests);
        checkSupervision(ticketTypeRequestList);
        checkTicketNumberLimit(ticketTypeRequestList);

        int totalAmountToPay = calculateTotalNumberToPay(ticketTypeRequestList);
        int totalSeatsToAllocate = calculateSeatsToAllocate(ticketTypeRequestList);

        long numberOfAdults = numberOfTicketsPerType(ticketTypeRequestList, TicketTypeRequest.Type.ADULT);
        long numberOfChildren = numberOfTicketsPerType(ticketTypeRequestList, TicketTypeRequest.Type.CHILD);
        long numberOfInfants = numberOfTicketsPerType(ticketTypeRequestList, TicketTypeRequest.Type.INFANT);

        System.out.println("/********** Receipt **********/");
        System.out.println("Number Of Adults: " + numberOfAdults);
        System.out.println("Number Of Child: " + numberOfChildren);
        System.out.println("Number Of Infants:" + numberOfInfants);
        System.out.println("Total to pay today: £" + totalAmountToPay);
        System.out.println("Total number of seat needed: " + totalSeatsToAllocate);

        ticketPaymentService.makePayment(accountId, totalAmountToPay);
        seatReservationService.reserveSeat(accountId, totalSeatsToAllocate);
    }

    private void checkAccountIsValid(Long accountId) {
        if (accountId <= 0 ) {
            throw new InvalidPurchaseException("ERROR - Invalid account number may not continue with purchase.");
        }
    }

    private void checkSupervision(List<TicketTypeRequest> ticketTypeRequests) {
        boolean hasChild = ticketTypeRequests.stream()
                .anyMatch(ticketTypeRequest -> ticketTypeRequest.getTicketType() == TicketTypeRequest.Type.CHILD);

        boolean hasInfant = ticketTypeRequests.stream()
                .anyMatch(ticketTypeRequest -> ticketTypeRequest.getTicketType() == TicketTypeRequest.Type.INFANT);

        boolean hasAdultSupervision = ticketTypeRequests.stream()
                .anyMatch(ticketTypeRequest -> ticketTypeRequest.getTicketType() == TicketTypeRequest.Type.ADULT);

        if ((hasChild || hasInfant) && !hasAdultSupervision) {
            throw new InvalidPurchaseException("ERROR - Children and/or Infants have no supervision cannot purchase ticket.");
        }
    }

    private void checkTicketNumberLimit(List<TicketTypeRequest> ticketTypeRequests) {
        int numberOfTickets = ticketTypeRequests.stream().mapToInt(TicketTypeRequest::getNoOfTickets).sum();

        if (numberOfTickets > 20) {
            throw new InvalidPurchaseException("ERROR - Cannot purchase over 20 tickets.");
        }
    }
}
