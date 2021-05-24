package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.model.ProposerStore;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProposerStoreRepository extends CrudRepository<ProposerStore, String> {
    Optional<ProposerStore> findByTempDataId(String tempDataId);

}
