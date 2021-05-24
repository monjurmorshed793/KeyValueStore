package bd.ac.buet.KeyValueStore.dto.response;

import bd.ac.buet.KeyValueStore.model.ObjectStore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObjectStoreResponse {
    private ObjectStore objectStore;
    private String server;
}
