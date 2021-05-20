package bd.ac.buet.KeyValueStore.helper;

import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.helper.models.TestObject;
import com.google.gson.Gson;

import java.time.Instant;

public class DummyObjectStore {
    public static ObjectStore createDummyObjectStore(){
        TestObject testObject = TestObjectService.createDummyTestObject();
        Gson gson = new Gson();
        return ObjectStore
                .builder()
                .customObject(gson.toJson(testObject))
                .createdOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
    }

}
