package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TempDataRepository extends CrudRepository<TempData, String> {
    List<TempData> findByObjectIdAndStatusOrderByCreatedOnDesc(String objectId, Status status);

    List<TempData> findByObjectIdOrderByCreatedOnDesc(String objectId);


}
