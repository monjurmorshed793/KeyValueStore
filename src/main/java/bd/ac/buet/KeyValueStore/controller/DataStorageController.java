package bd.ac.buet.KeyValueStore.controller;

import bd.ac.buet.KeyValueStore.dto.response.ObjectStoreResponse;
import bd.ac.buet.KeyValueStore.dto.response.TempDataListResponse;
import bd.ac.buet.KeyValueStore.dto.response.TempDataResponse;
import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import bd.ac.buet.KeyValueStore.repository.ObjectStoreRepository;
import bd.ac.buet.KeyValueStore.repository.TempDataRepository;
import bd.ac.buet.KeyValueStore.service.ServerInfoService;
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
    private final ServerInfoService serverInfoService;

    public DataStorageController(ProposerService proposerService, ObjectStoreRepository objectStoreRepository, TempDataService tempDataService, TempDataRepository tempDataRepository, ServerInfoService serverInfoService) {
        this.proposerService = proposerService;
        this.objectStoreRepository = objectStoreRepository;
        this.tempDataService = tempDataService;
        this.tempDataRepository = tempDataRepository;
        this.serverInfoService = serverInfoService;
    }

    @PutMapping("/save")
    public ResponseEntity<ObjectStoreResponse> storeOrUpdateData(@RequestBody ObjectStore objectStore){
        objectStore  = proposerService.propose(objectStore);
        ObjectStoreResponse objectStoreResponse = ObjectStoreResponse
                .builder()
                .objectStore(objectStore)
                .server(serverInfoService.getSelfServerInfo().getName())
                .build();
        return ResponseEntity
                .ok(objectStoreResponse);
    }

    @GetMapping ("/id/{id}")
    public ResponseEntity<ObjectStoreResponse> getData( @PathVariable String id){
        ObjectStore objectStore = objectStoreRepository.findById(id).get();
        ObjectStoreResponse objectStoreResponse = ObjectStoreResponse
                .builder()
                .objectStore(objectStore)
                .server(serverInfoService.getSelfServerInfo().getName())
                .build();
        return ResponseEntity
                .ok(objectStoreResponse);
    }

    @GetMapping("/get-all-object-store")
    public ResponseEntity<List<ObjectStore>> getAllObjectStore(){
        return ResponseEntity
                .ok(IteratorUtils.toList(objectStoreRepository.findAll().iterator()));
    }

    @DeleteMapping("delete-all-object-data")
    public ResponseEntity<Void> deleteAllObjectStore(){
        objectStoreRepository.deleteAll();
        return ResponseEntity.ok().build();
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
    public ResponseEntity<TempDataListResponse> getAllTempData(){
        List<TempData> tempData = IteratorUtils.toList(tempDataRepository.findAll().iterator());
        TempDataListResponse tempDataListResponse = TempDataListResponse
                .builder()
                .tempData(tempData)
                .server(serverInfoService.getSelfServerInfo().getName())
                .build();
        return ResponseEntity
                .ok()
                .body(tempDataListResponse);
    }

    @GetMapping("/temp-data")
    public ResponseEntity<TempDataListResponse> getTempDataByObjectId(@RequestParam("object-id") String objectId){
        List<TempData> tempDataList = tempDataRepository.findByObjectIdOrderByCreatedOnDesc(objectId);
        TempDataListResponse tempDataListResponse = TempDataListResponse
                .builder()
                .tempData(tempDataList)
                .server(serverInfoService.getSelfServerInfo().getName())
                .build();
        return ResponseEntity
                .ok()
                .body(tempDataListResponse);
    }

    @GetMapping("/temp-data-by-status")
    public ResponseEntity<TempDataListResponse> getTempDataByObjectIdAndStatus(@RequestParam("object-id") String objectId,
                                                                         @RequestParam("status") Status status){
        List<TempData> tempDataList = tempDataRepository.findByObjectIdAndStatusOrderByCreatedOnDesc(objectId, status);

        TempDataListResponse tempDataListResponse = TempDataListResponse
                .builder()
                .tempData(tempDataList)
                .server(serverInfoService.getSelfServerInfo().getName())
                .build();
        return ResponseEntity
                .ok()
                .body(tempDataListResponse);
    }
}
