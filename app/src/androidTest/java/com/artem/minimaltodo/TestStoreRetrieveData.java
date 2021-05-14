/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2015 Miikka Andersson
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.artem.minimaltodo;

import android.content.Context;

import com.artem.minimaltodo.Main.MainFragment;
import com.artem.minimaltodo.Utility.StoreRetrieveData;
import com.artem.minimaltodo.Utility.ToDoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test cases for StoreRetrieveData class
 */
public class TestStoreRetrieveData {

    private ArrayList<ToDoItem> mOriginalData;
    ArrayList<ToDoItem> mTestData;

    @Before
    public void setUp() {
        mOriginalData = new ArrayList<>();

        // Save the original data and wipe out the storage
        StoreRetrieveData dataStorage = getDataStorage();
        try {
            ArrayList<ToDoItem> items = dataStorage.loadFromFile();

            if (items.size() > 0) {
                mOriginalData.clear();
                mOriginalData.addAll(items);

                items.clear();
                dataStorage.saveToFile(items);
            }

        } catch (Exception e) {
            fail("Couldn't store data: " + e.getMessage());
        }
    }

    @After
    public void tearDown() throws IOException, JSONException {

        // Let's restore the data we might have wiped out during setUp()...
        StoreRetrieveData dataStorage = getDataStorage();
        dataStorage.saveToFile(mOriginalData);
    }

    /**
     * We should have an empty data storage at hand for the starters
     */
    @Test
    public void testPreconditions() {
        StoreRetrieveData dataStorage = getDataStorage();

        ArrayList<ToDoItem> items = null;
        try {
            items = dataStorage.loadFromFile();
        } catch (Exception e) {
            fail("Couldn't read from data storage: " + e.getMessage());
        }

        assertEquals(0, items.size());
    }

    /**
     * Write items to data storage and ensure those same items can be retrieved from the storage.
     */
    @Test
    public void testWritingToAndReadingFromTheDataStorage() {
        StoreRetrieveData dataStorage = getDataStorage();
        ArrayList<ToDoItem> retrievedItems = new ArrayList<>();

        // Persist the test data
        try {
            dataStorage.saveToFile(mTestData);
        } catch (Exception e) {
            fail("Couldn't store data: " + e.getMessage());
        }

        // Read from storage
        try {
            retrievedItems = dataStorage.loadFromFile();
        } catch (Exception e) {
            fail("Couldn't read from data storage: " + e.getMessage());
        }

        // We should have equal amount of items than what we just stored
        assertEquals(mTestData.size(), retrievedItems.size());

        // The content should be same as well...
        for (ToDoItem retrievedItem : retrievedItems) {
            // We want to be sure every single item in data storage can also be found from
            // our test data collection
            boolean found = false;
            for (ToDoItem testItem : mTestData) {

                // Check the items are same
                if (retrievedItem.getIdentifier().equals(testItem.getIdentifier()) &&
                        retrievedItem.getToDoText().equals(testItem.getToDoText()) &&
                        retrievedItem.hasReminder() == testItem.hasReminder() &&
                        retrievedItem.getToDoDate().equals(testItem.getToDoDate())) {

                    found = true;
                    break;
                }
            }

            if (!found) {
                fail("Content mis-match between test data and data retrieved from the storage!");
            }
        }
    }

    /**
     * Ensure JSONArray conversion works as intended
     */
    @Test
    public void testArrayListToJsonArrayConversion() {
        try {
            JSONArray array = StoreRetrieveData.toJSONArray(mTestData);
            assertEquals(mTestData.size(), array.length());
        } catch (Exception e) {
            fail("Exception thrown when converting to JSONArray: " + e.getMessage());
        }
    }

    private StoreRetrieveData getDataStorage() {
        Context context = getInstrumentation().getTargetContext();
        return new StoreRetrieveData(context, MainFragment.FILENAME);
    }
}