package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.*;

public class Application {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        TicketPaymentServiceImpl ticketPaymentService = new TicketPaymentServiceImpl();
        SeatReservationServiceImpl seatReservationService = new SeatReservationServiceImpl();

        TicketServiceImpl ticketService = new TicketServiceImpl(ticketPaymentService, seatReservationService);
        List<TicketTypeRequest> ticketTypeRequests = new ArrayList<>();

        int finished = 0;
        long accountNumber = 0;

        System.out.println("/********* WELCOME TO THE SHOW *********/");
        System.out.println("What is your account number?");
        accountNumber = scanner.nextLong();

        while (finished == 0){

            System.out.println("Who would you like to buy tickets for? (One Type at a time) " +
                    "\n A = Adult, C = Child, I = Infant");
            String type = scanner.next().toUpperCase();

            while (type.isEmpty() || type.isBlank() || type.matches("[^ACI]"))
            {
                System.out.println("Who would you like to buy tickets for? (One Type at a time) " +
                        "\n A = Adult, C = Child, I = Infant");
                type = scanner.next().toUpperCase();
            }

            System.out.println("How many tickets of that type?");
            int numberOfTickets = scanner.nextInt();

            ticketTypeRequests.add(createTicketTypeRequest(type, numberOfTickets));

            System.out.println("Are you finished? 1 = Yes, 0 = No");
            finished = scanner.nextInt();
        }

        TicketTypeRequest[] ticketTypeRequestsArray =  new TicketTypeRequest[ticketTypeRequests.size()];
        ticketTypeRequests.toArray(ticketTypeRequestsArray);

        ticketService.purchaseTickets(accountNumber, ticketTypeRequestsArray);
    }

    private static TicketTypeRequest createTicketTypeRequest(String type, int numberOfTickets) {

        switch (type) {
            case "A" -> {
                return adultTicketRequest(numberOfTickets);
            }
            case "C" -> {
                return childTicketRequest(numberOfTickets);
            }
            case "I" -> {
                return infantTicketRequest(numberOfTickets);
            }
            default -> {
                throw new InvalidPurchaseException("ERROR - INVALID TICKET REQUEST.");
            }
        }
    }
}
