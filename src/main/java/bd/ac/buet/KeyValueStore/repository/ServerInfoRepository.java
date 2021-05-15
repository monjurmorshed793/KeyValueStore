package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.model.ServerInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ServerInfoRepository extends CrudRepository<ServerInfo, UUID> {

}
