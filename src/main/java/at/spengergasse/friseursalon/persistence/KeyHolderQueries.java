package at.spengergasse.friseursalon.persistence;

import at.spengergasse.friseursalon.domain.KeyHolder;

import java.util.Optional;

public interface KeyHolderQueries<T extends KeyHolder> {

    Optional<T> findByKey(String key);

    void deleteByKey(String key);
}
