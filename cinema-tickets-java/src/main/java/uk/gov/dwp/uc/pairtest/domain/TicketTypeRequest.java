package uk.gov.dwp.uc.pairtest.domain;

/**
 * Immutable Object
 */

public class TicketTypeRequest {

    private final int noOfTickets;
    private final Type type;

    private TicketTypeRequest(Type type, int noOfTickets) {
        this.type = type;
        this.noOfTickets = noOfTickets;
    }

    public static TicketTypeRequest infantTicketRequest(int noOfTickets) {
        return new TicketTypeRequest(Type.INFANT , noOfTickets);
    }

    public static TicketTypeRequest childTicketRequest(int noOfTickets) {
        return new TicketTypeRequest(Type.CHILD , noOfTickets);
    }

    public static TicketTypeRequest adultTicketRequest(int noOfTickets) {
        return new TicketTypeRequest(Type.ADULT , noOfTickets);
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public Type getTicketType() {
        return type;
    }

    public enum Type {
        ADULT, CHILD , INFANT
    }

}
