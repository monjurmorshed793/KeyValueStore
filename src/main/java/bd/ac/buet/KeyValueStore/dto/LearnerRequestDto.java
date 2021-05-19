package bd.ac.buet.KeyValueStore.dto;

import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.model.TempData;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearnerRequestDto {
    private TempData tempData;
    private ServerInfo serverInfo;
}
