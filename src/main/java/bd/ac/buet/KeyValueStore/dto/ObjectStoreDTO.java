package bd.ac.buet.KeyValueStore.dto;

import lombok.*;

import java.time.Instant;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjectStoreDTO {
    private String id;
    private String customObject;
    private Instant createdOn;
    private Instant updatedOn;
}
