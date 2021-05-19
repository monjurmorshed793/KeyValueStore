package bd.ac.buet.KeyValueStore.controller;

import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.repository.ObjectStoreRepository;
import bd.ac.buet.KeyValueStore.service.paxos.ProposerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataStorageController {

    private final ProposerService proposerService;
    private final ObjectStoreRepository objectStoreRepository;

    public DataStorageController(ProposerService proposerService, ObjectStoreRepository objectStoreRepository) {
        this.proposerService = proposerService;
        this.objectStoreRepository = objectStoreRepository;
    }

    @PutMapping("/")
    public  ObjectStore storeOrUpdateData(ObjectStore objectStore){
        return proposerService.propose(objectStore);
    }

    @GetMapping ("/id/{id}")
    public ObjectStore getData( @PathVariable String id){
        return objectStoreRepository.findById(id).get();
    }
}
