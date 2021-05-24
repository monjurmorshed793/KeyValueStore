package bd.ac.buet.KeyValueStore.controller;

import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.repository.ObjectStoreRepository;
import bd.ac.buet.KeyValueStore.service.paxos.ProposerService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/save")
    public ResponseEntity<ObjectStore> storeOrUpdateData(ObjectStore objectStore){
        return new ResponseEntity<>(proposerService.propose(objectStore), HttpStatus.OK);
    }

    @GetMapping ("/id/{id}")
    public ResponseEntity<ObjectStore> getData( @PathVariable String id){
        return new ResponseEntity<>(objectStoreRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "hello from key value store";
    }
}
