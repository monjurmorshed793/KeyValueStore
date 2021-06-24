package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.model.ObjectStore;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

public interface ObjectStoreRepository extends CrudRepository<ObjectStore, String> {
    List<ObjectStore> findAllByUpdatedOnAfter(Instant instant);
}
