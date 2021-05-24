package bd.ac.buet.KeyValueStore.helper.models;

import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestObject {
    private String id;
    private String name;
    private Instant createdOn;
    private Instant updatedOn;
}
