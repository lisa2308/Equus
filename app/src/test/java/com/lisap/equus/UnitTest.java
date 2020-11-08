package com.lisap.equus;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.lisap.equus.data.entities.Stable;
import com.lisap.equus.data.firestore.DbHorse;
import com.lisap.equus.data.preferences.SharedPreferencesManager;
import com.lisap.equus.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;

import okhttp3.internal.Util;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(RobolectricTestRunner.class)
public class UnitTest {

    final String KEY = "KEY";
    Context context;

    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void capitalizeTest() {
        String actual = Utils.capitalize("test");
        String expected = "Test";

        assertEquals(expected, actual);

        actual = Utils.capitalize(null);
        expected = null;

        assertEquals(expected, actual);
    }

    @Test
    public void getDateInRightFormatTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.YEAR, 2020);

        String actual = Utils.getDateInRightFormat(calendar.getTime());
        String expected = "01/01/2020";

        assertEquals(expected, actual);

        actual = Utils.getDateInRightFormat(null);
        expected = "";

        assertEquals(expected, actual);
    }

//    @Test
//    public void isPasswordValidTest() {
//        boolean actual = Utils.isPasswordValid("test", "test");
//        boolean expected = false;
//
//        assertEquals(expected, actual);
//    }

    @Test
    public void sharedPreferencesManagerStringTest() {
        String actual = SharedPreferencesManager.getString(context, KEY);
        String expected = null;

        assertEquals(expected, actual);

        // put string
        SharedPreferencesManager.putString(context, KEY, "test");

        actual = SharedPreferencesManager.getString(context, KEY);
        expected = "test";

        assertEquals(expected, actual);
    }

    @Test
    public void sharedPreferencesManagerIntTest() {
        int actual = SharedPreferencesManager.getInt(context, KEY, 0);
        int expected = 0;

        assertEquals(expected, actual);

        // put int
        SharedPreferencesManager.putInt(context, KEY, 10);

        actual = SharedPreferencesManager.getInt(context, KEY, 0);
        expected = 10;

        assertEquals(expected, actual);
    }

    @Test
    public void sharedPreferencesManagerBooleanTest() {
        boolean actual = SharedPreferencesManager.getBoolean(context, KEY);
        boolean expected = false;

        assertEquals(expected, actual);

        // put boolean
        SharedPreferencesManager.putBoolean(context, KEY, true);

        actual = SharedPreferencesManager.getBoolean(context, KEY);
        expected = true;

        assertEquals(expected, actual);
    }

    @Test
    public void sharedPreferencesManagerStableTest() {
        Stable actual = SharedPreferencesManager.getStable(context);
        Stable expected = null;

        assertEquals(expected, actual);

        // put stable
        Stable stable = new Stable();
        stable.setIdStable("test");

        SharedPreferencesManager.putStable(context, stable);

        actual = SharedPreferencesManager.getStable(context);
        expected = stable;

        assertEquals(expected, actual);
    }
}