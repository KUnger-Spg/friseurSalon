package at.spengergasse.friseursalon.persistence;

import at.spengergasse.friseursalon.domain.Customer;
import at.spengergasse.friseursalon.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, KeyHolderQueries<Customer> {

    //    List<Photographer> findByUserName(String s);
    Optional<Customer> findByUserName(String s);

    Optional<Customer> findByKey(String key);

    Customer getReferenceByKey(String key);
}
