package bd.ac.buet.KeyValueStore.helper;

import bd.ac.buet.KeyValueStore.helper.models.TestObject;

import java.time.Instant;

public class TestObjectService {
    public static TestObject createDummyTestObject(){
        return TestObject
                .builder()
                .id("1")
                .name("test-object")
                .createdOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
    }
}
