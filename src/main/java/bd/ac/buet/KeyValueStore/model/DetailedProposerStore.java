package bd.ac.buet.KeyValueStore.model;

import bd.ac.buet.KeyValueStore.model.enumeration.State;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("DetailedProposerStore")
public class DetailedProposerStore {
    @Id
    @Indexed
    private String id;
    @Indexed
    @Reference
    private ProposerStore proposerStore;
    @Reference
    @Indexed
    private ServerInfo serverInfo;
    private TempData respondedTempData;
    private State state;
    private Status status;
    @CreatedDate
    private Instant createdOn;
    @LastModifiedDate
    private Instant updatedOn;
}
