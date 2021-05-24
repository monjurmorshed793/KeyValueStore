package bd.ac.buet.KeyValueStore.controller;

import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import bd.ac.buet.KeyValueStore.repository.ObjectStoreRepository;
import bd.ac.buet.KeyValueStore.repository.TempDataRepository;
import bd.ac.buet.KeyValueStore.service.TempDataService;
import bd.ac.buet.KeyValueStore.service.paxos.ProposerService;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DataStorageController {

    private final ProposerService proposerService;
    private final ObjectStoreRepository objectStoreRepository;
    private final TempDataService tempDataService;
    private final TempDataRepository tempDataRepository;

    public DataStorageController(ProposerService proposerService, ObjectStoreRepository objectStoreRepository, TempDataService tempDataService, TempDataRepository tempDataRepository) {
        this.proposerService = proposerService;
        this.objectStoreRepository = objectStoreRepository;
        this.tempDataService = tempDataService;
        this.tempDataRepository = tempDataRepository;
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

    @DeleteMapping("/delete-temp-data")
    public ResponseEntity<Void> deleteAllTempData(){
        tempDataService.deleteAllTempData();
        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("/temp-data-all")
    public ResponseEntity<List<TempData>> getAllTempData(){
        List<TempData> tempData = IteratorUtils.toList(tempDataRepository.findAll().iterator());
        return ResponseEntity
                .ok()
                .body(tempData);
    }

    @GetMapping("/temp-data")
    public ResponseEntity<List<TempData>> getTempDataByObjectId(@RequestParam("object-id") String objectId){
        List<TempData> tempDataList = tempDataRepository.findByObjectIdOrderByCreatedOnDesc(objectId);
        return ResponseEntity
                .ok()
                .body(tempDataList);
    }

    @GetMapping("/temp-data-by-status")
    public ResponseEntity<List<TempData>> getTempDataByObjectIdAndStatus(@RequestParam("object-id") String objectId,
                                                                         @RequestParam("status") Status status){
        List<TempData> tempDataList = tempDataRepository.findByObjectIdAndStatusOrderByCreatedOnDesc(objectId, status);
        return ResponseEntity
                .ok()
                .body(tempDataList);
    }
}
