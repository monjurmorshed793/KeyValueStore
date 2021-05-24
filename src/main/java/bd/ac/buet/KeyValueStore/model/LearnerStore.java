package bd.ac.buet.KeyValueStore.model;

import bd.ac.buet.KeyValueStore.model.enumeration.State;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("LearnerStore")
public class LearnerStore {
    @Id
    @Indexed
    private String id;
    @Indexed
    private TempData tempData;
    @Indexed
    private State state;
    @Indexed
    private Status status;
    private Instant createdOn;
    private Instant updatedOn;
}
