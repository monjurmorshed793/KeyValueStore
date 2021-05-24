package bd.ac.buet.KeyValueStore.dto.response;

import bd.ac.buet.KeyValueStore.model.TempData;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TempDataListResponse {
    List<TempData> tempData;
    String server;
}
