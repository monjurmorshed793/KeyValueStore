package bd.ac.buet.KeyValueStore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("ServerInfo")
public class ServerInfo {
    @Id
    @Indexed
    private String id;
    @Indexed
    private String name;
    private Instant createdOn;
    private Instant updatedOn;
}
