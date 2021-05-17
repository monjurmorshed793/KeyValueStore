package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.model.DetailedPaxosStore;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface DetailedPaxosStoreRepository extends CrudRepository<DetailedPaxosStore, String> {
    Optional<DetailedPaxosStore> findByPaxosStore_TempData_IdAndServerInfo_Id(String tempDataId, String serverInfoId);

    Iterator<DetailedPaxosStore> findAllByPaxosStore_Id(String paxosStoreId);
}
