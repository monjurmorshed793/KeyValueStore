package bd.ac.buet.KeyValueStore.dto;

import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestoreDTO {
    ServerInfo serverInfo;
    List<ObjectStore> objectStores;
    List<String> deleteIds;
}
