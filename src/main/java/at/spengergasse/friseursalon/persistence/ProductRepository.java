package at.spengergasse.friseursalon.persistence;

import at.spengergasse.friseursalon.domain.Product;
import at.spengergasse.friseursalon.domain.Reservation;
import at.spengergasse.friseursalon.domain.Service;
import at.spengergasse.friseursalon.persistence.projections.Projections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//public interface ProductRepository extends JpaRepository<Reservation, Long> //was wrong. It points to Reservation

public interface ProductRepository extends JpaRepository<Product, Long>, KeyHolderQueries<Product> {

    List<Projections.ProductInfoDTO> findAllByProductNameLike(String productName);

    List<Product> findAll();

    Optional<Product> findByProductName(String productName);

    Optional<Product> findByKey(String key);

}
