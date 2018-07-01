package com.example.android.journalapp;

import android.icu.util.UniversalTimeScale;
import org.junit.Test;
import static org.junit.Assert.*;

public class UnitProject {
    @Test
    public void addition_isCorrect() throws Exception {
        int expected = 5;
        int actual = UniversalTimeScale.DB2_TIME;
                assertEquals(expected, actual);
    }
}
