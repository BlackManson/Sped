package com.dell.sped;

import org.junit.Test;

import static org.junit.Assert.*;

public class GPSDistTest {

    @Test
    public void check_getters(){
        GPSDist gpsDist = new GPSDist(12,13,14,15);
        assertEquals(12.0,gpsDist.x1, 12.0);
        assertEquals(14.0,gpsDist.x2, 14.0);
        assertEquals(13.0,gpsDist.y1, 13.0);
        assertEquals(15.0,gpsDist.x1, 15.0);
    }


}