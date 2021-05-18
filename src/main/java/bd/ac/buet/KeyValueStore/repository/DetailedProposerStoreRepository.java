package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.model.DetailedProposerStore;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.Optional;

public interface DetailedProposerStoreRepository extends CrudRepository<DetailedProposerStore, String> {
    Optional<DetailedProposerStore> findByPaxosStore_TempData_IdAndServerInfo_Id(String tempDataId, String serverInfoId);

    Iterator<DetailedProposerStore> findAllByPaxosStore_Id(String paxosStoreId);
}
