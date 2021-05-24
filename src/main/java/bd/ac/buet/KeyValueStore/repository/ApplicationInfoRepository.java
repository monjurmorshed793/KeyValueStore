package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.model.ApplicationInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ApplicationInfoRepository extends CrudRepository<ApplicationInfo, String> {
    Optional<ApplicationInfo> findByNameEquals(String name);

    void deleteByName(String name);
}
