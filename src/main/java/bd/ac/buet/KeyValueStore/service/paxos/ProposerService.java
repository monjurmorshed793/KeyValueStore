package bd.ac.buet.KeyValueStore.service.paxos;

import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.repository.TempDataRepository;
import bd.ac.buet.KeyValueStore.service.KafkaProducer;
import bd.ac.buet.KeyValueStore.service.ObjectStoreService;
import bd.ac.buet.KeyValueStore.service.TempDataService;
import org.springframework.stereotype.Service;

@Service
public class ProposerService {
    private final TempDataService tempDataService;
    private final ObjectStoreService objectStoreService;
    private final KafkaProducer kafkaProducer;

    public ProposerService(TempDataService tempDataService, ObjectStoreService objectStoreService, KafkaProducer kafkaProducer) {
        this.tempDataService = tempDataService;
        this.objectStoreService = objectStoreService;
        this.kafkaProducer = kafkaProducer;
    }

    public ObjectStore propose(ObjectStore objectStore){
        TempData tempData = objectStoreService.convertToTempData(objectStore);
        tempData = tempDataService.save(tempData);
        kafkaProducer.send("proposer", tempData);
        return tempDataService.convertToObjectStore(tempData); //todo implement
    }
}
