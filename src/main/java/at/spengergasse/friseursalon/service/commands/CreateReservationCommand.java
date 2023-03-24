package at.spengergasse.friseursalon.service.commands;


public record CreateReservationCommand(String reservationName, String customerKey) {
}

//public record CreateReservationCommand(String reservationName, String customerKey, String serviceKey, String productKey) { }
