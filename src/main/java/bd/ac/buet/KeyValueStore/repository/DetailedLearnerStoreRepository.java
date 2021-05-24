package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.model.DetailedLearnerStore;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface DetailedLearnerStoreRepository extends CrudRepository<DetailedLearnerStore, String> {

    Optional<DetailedLearnerStore> findByServerInfoIdAndLearnerStoreTempDataId(String serverInfoId, String tempDataId);

    Boolean existsByServerInfoIdAndLearnerStoreTempDataId(String serverInfoId, String tempDataId);

    List<DetailedLearnerStore> findAllByLearnerStoreId(String learnerStoreId);

}
