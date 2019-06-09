package de.holarse.pagination;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

public class PaginationTest {

    @Test
    public void testGetMedian3() {
        assertEquals(1, new Pagination(1,3).getMedian());
    }
    
    @Test
    public void testGetMedian4() {
        assertEquals(2, new Pagination(2,4).getMedian());
    }    
    
    @Test
    public void testGetMedian5() {
        assertEquals(2, new Pagination(2,5).getMedian());
    }    
    
    @Test
    public void testGetMedian7() {
        assertEquals(3, new Pagination(3,7).getMedian());
    }        
    
    @Test
    public void testGetMedian6() {
        assertEquals(3, new Pagination(3,6).getMedian());
    }
    
    @Test
    public void testGetList5() {
        var expected = Arrays.asList(1,2,3,4,5);
        assertEquals(expected, new Pagination(3, 5).getList());
    }
    
    @Test
    public void testGetList3() {
        var expected = Arrays.asList(1,2,3);
        assertEquals(expected, new Pagination(2, 3).getList());
    }    
    
    @Test
    public void testGetListHigher3() {
        var expected = Arrays.asList(5,6,7);
        assertEquals(expected, new Pagination(6, 3).getList());
    }        

    @Test
    public void testGetListOnlyUpper3() {
        var expected = Arrays.asList(1,2,3);
        assertEquals(expected, new Pagination(1, 3).getList());
    }            
    
}
