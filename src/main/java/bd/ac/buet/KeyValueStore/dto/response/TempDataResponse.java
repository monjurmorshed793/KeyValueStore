package bd.ac.buet.KeyValueStore.dto.response;

import bd.ac.buet.KeyValueStore.model.TempData;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TempDataResponse {
    private TempData tempData;
    private String server;
}
