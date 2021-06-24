package bd.ac.buet.KeyValueStore.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Instant;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash("ObjectStore")
public class ObjectStore {
    @Id
    @Indexed
    private String id;
    @Indexed
    private String customObject;
    private Instant createdOn;
    @Indexed
    private Instant updatedOn;
}
