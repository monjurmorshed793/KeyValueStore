package bd.ac.buet.KeyValueStore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("ApplicationInfo")
public class ApplicationInfo {
    @Id
    private String id;
    private String name;
    private Instant createdOn;
    private Instant updatedOn;
}
