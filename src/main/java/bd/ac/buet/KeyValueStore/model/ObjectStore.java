package bd.ac.buet.KeyValueStore.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Objects;

@Data
@Getter
@Setter
@RedisHash("ObjectStore")
public class ObjectStore {
    @Id
    @Indexed
    private String id;
    @Indexed
    private String customObject;

    public ObjectStore() {
    }
}
