package bd.ac.buet.KeyValueStore.controller;

import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.service.paxos.ProposerService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class DataStorageController {

    private final ProposerService proposerService;

    public DataStorageController(ProposerService proposerService) {
        this.proposerService = proposerService;
    }

    @PutMapping
    public  ObjectStore storeOrUpdateData(ObjectStore objectStore){
        return proposerService.propose(objectStore);
    }
}
