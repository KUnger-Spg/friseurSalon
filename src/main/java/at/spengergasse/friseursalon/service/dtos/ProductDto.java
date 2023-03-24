package at.spengergasse.friseursalon.service.dtos;

import at.spengergasse.friseursalon.domain.Product;
import at.spengergasse.friseursalon.domain.Service;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Getter
@RequiredArgsConstructor
@Relation(collectionRelation = "products", itemRelation = "product")


public class ProductDto extends RepresentationModel<ProductDto> {
    private final String productName;
    private final LocalDateTime createdAt;
    private final BigDecimal productPreis;
    private final Currency productCurrency;
//    private final String reservation;
//    private final ReservationDto reservationDto;

    public ProductDto(Product product) {
        this(product.getProductName(), product.getCreationTS(), product.getProductPreis(), product.getProductCurrency());
//                new ReservationDto(product.getReservations()));

    }
}

/*
    public record ProductDto(String productName, BigDecimal productPreis, Currency productCurrency) {

        public ProductDto(Product product){
            this(product.getProductName(),
                    product.getProductPreis(),
                    product.getProductCurrency());
        }
    }
*/

