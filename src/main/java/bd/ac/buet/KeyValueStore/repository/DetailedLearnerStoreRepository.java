package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.model.DetailedLearnerStore;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;

public interface DetailedLearnerStoreRepository extends CrudRepository<DetailedLearnerStore, String> {

    Boolean existsByServerInfoIdAndLearnerStoreTempDataId(String serverInfoId, String tempDataId);

    Iterator<DetailedLearnerStore> findAllByLearnerStore_Id(String learnerStoreId);

}
