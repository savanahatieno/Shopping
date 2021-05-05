package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StoreTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getName() {
        Store testStore = setUpStore();
        assertEquals("Naivas",testStore.getName());
    }

    @Test
    public void getAddress() {
        Store testStore = setUpStore();
        assertEquals("Syokimau", testStore.getAddress());
    }

    @Test
    public void getWebsite() {
        Store testStore = setUpStore();
        assertEquals("www.naivas.com",testStore.getWebsite());
    }

    @Test
    public void getAndSetId() {
        Store testStore = setUpStore();
        testStore.setId(1);
        assertEquals(1,testStore.getId());
    }

    @Test
    public void setName(){
        Store testStore = setUpStore();
        testStore.setName("Uchumi");
        assertNotEquals("Naivas",testStore.getName());
    }

    public Store setUpStore(){
        return new Store("Naivas","Syokimau","www.naivas.com");
    }
}