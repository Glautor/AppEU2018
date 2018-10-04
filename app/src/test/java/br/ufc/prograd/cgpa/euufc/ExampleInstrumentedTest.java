package br.ufc.prograd.cgpa.euufc;

import android.app.Instrumentation;
import android.content.Context;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.lang.Object;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleInstrumentedTest extends Object{
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Instrumentation getInstrumentation = new Instrumentation();
        Context appContext = getInstrumentation.getTargetContext();

        assertEquals("br.ufc.prograd.cgpa.euufc", appContext.getPackageName());
    }
}
