package at.spengergasse.friseursalon.service;

import at.spengergasse.friseursalon.domain.BookedProduct;
import at.spengergasse.friseursalon.domain.Product;
import at.spengergasse.friseursalon.foundation.Base58;
import at.spengergasse.friseursalon.foundation.DateTimeFactory;
import at.spengergasse.friseursalon.persistence.ProductRepository;
import at.spengergasse.friseursalon.persistence.ReservationRepository;
import at.spengergasse.friseursalon.service.commands.CreateProductCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)

public class ProductService {

    private final ProductRepository productRepository;
    private final ReservationRepository reservationRepository;
    private final DateTimeFactory dateTimeFactory;
    private final Base58 keyGen;
    private final Base58 base58;

    @Transactional
    public Product createProduct(CreateProductCommand cmd) {
        return createProduct(
                cmd.productName(),
//                cmd.productPreis(),
//                cmd.productCurrency(),
                cmd.reservationKey());
    }

    @Transactional
//    public Product createProduct(String productName, BigDecimal productPreis, Currency productCurrency, String reservationKey) {
        public Product createProduct(String productName, String reservationKey) {

        return productRepository.findByKey(reservationKey)  //reservation Key bulup map ediyor
                .map(reservation -> Product.builder()
                        .key(keyGen.randomString(Product.KEY_LENGTH))
                        .productName(productName)
 //                       .productPreis(productPreis)
 //                       .productCurrency(productCurrency)
                        .creationTS(dateTimeFactory.now())
//                        .reservations(BookedProduct)
                        .build())
                .map(productRepository::save)
                .orElseThrow();

    }

    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    public Optional<Product> getProduct(Long id) {

        return productRepository.findById(id);
    }

    public Optional<Product> getProduct(String key) {

        return productRepository.findByKey(key);

    }

    public Optional<Product> findByKey(String key) {
        return productRepository.findByKey(key);
    }

}
