package at.spengergasse.friseursalon.persistence;

import at.spengergasse.friseursalon.domain.Service;
import at.spengergasse.friseursalon.persistence.projections.Projections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long>, KeyHolderQueries<Service> {

    List<Projections.ServiceInfoDTO> findAllByServiceNameLike(String serviceName);

    List<Service> findAll();

    Optional<Service> findByServiceName(String serviceName);

    Optional<Service> findByKey(String key);

}
