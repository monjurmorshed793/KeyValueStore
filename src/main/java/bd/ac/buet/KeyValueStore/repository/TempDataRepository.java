package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import bd.ac.buet.KeyValueStore.model.enumeration.StoreType;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

public interface TempDataRepository extends CrudRepository<TempData, String> {
    List<TempData> findByObjectIdAndStatusOrderByCreatedOnDesc(String objectId, Status status);

    List<TempData> findByObjectIdOrderByCreatedOnDesc(String objectId);

    List<TempData> findAllByStoreTypeAndUpdatedOnAfter(StoreType storeType, Instant instant);

    List<TempData> findAllByStoreType(StoreType storeType);

}
