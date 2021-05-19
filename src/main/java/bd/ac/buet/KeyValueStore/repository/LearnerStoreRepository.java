package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.model.LearnerStore;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LearnerStoreRepository extends CrudRepository<LearnerStore, String> {
    Optional<LearnerStore> findByTempData_Id(String tempDataId);
}
