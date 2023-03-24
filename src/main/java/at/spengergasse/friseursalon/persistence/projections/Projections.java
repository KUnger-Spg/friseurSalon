package at.spengergasse.friseursalon.persistence.projections;

import java.time.LocalDateTime;

public class Projections {

    public static record ProductInfoDTO(String productName) {}

    public static record ServiceInfoDTO(String serviceName) {}

    public static record ReservationInfoDTO(String reservationName, LocalDateTime creationTS) {}

}
