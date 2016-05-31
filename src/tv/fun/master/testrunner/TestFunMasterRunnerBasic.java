package tv.fun.master.testrunner;

import static tv.fun.master.testutils.TestGlobalVars.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import tv.fun.master.testutils.TestUtils;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.test.InstrumentationTestRunner;
import android.util.Log;

public class TestFunMasterRunnerBasic extends InstrumentationTestRunner {

    private Writer mWriter;
    private XmlSerializer mTestSuiteSerializer;
    private long mTestStarted;
    
    private static final String JUNIT_XML_FILE = 
            String.format("Robotium_master_TEST-all_%s_%s.xml", TestUtils.getDeviceType(), TestUtils.getDateTime());
    private static final String TEST_PASS = "pass";
    private static final String TEST_FAIL = "fail";
    
    private int totalPass = 0;
    private int totalFail = 0;
    private float totalTime = 0;  // mins
    
    @Override
    public void onStart() {
        try {
            File fileBobo = new File(this.getTestResultDir(this.getTargetContext()));
            if (!fileBobo.exists()) {
                fileBobo.mkdir();
            }
            
            File resultFile;
//            if (isSDCardAvaliable()) {
//                resultFile = new File((this.getTestResultDir(this.getTargetContext())), JUNIT_XML_FILE);
//            } else {
//                resultFile = new File(this.getTargetContext().getFilesDir(), JUNIT_XML_FILE);
//            }
            resultFile = new File((this.getTestResultDir(this.getTargetContext())), JUNIT_XML_FILE);
            Log.d(REPORT_LOG_TAG, REPORT_TITLE + "save xml report ->" + resultFile.getAbsolutePath());
            
            this.startJunitOutput(new FileWriter(resultFile));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        
        super.onStart();
    }
    
//    public static boolean isSDCardAvaliable() {
//        Log.d(TestGlobalVars.LOG_TAG, TestGlobalVars.TITLE + "verify sdcard available.");
//        
//        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
//        
//    }

    private String getTestResultDir(Context context) {
        final String subDirName = "robotium";  // sub directory name to be update
        final String packageName = "/" + subDirName;
        String filePath;
        
        if(TestUtils.isSDCardAvaliable()) {
            Log.d(REPORT_LOG_TAG, REPORT_TITLE + "sdcasrd is available");
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + packageName;
        } else {
            Log.d(REPORT_LOG_TAG, REPORT_TITLE + "sdcasrd is NOT available");
            filePath = context.getCacheDir().getPath() + packageName;
        }

        Log.d(REPORT_LOG_TAG, REPORT_TITLE + "get test results dir -> " + filePath);
        return filePath;
    }
    
    /***
     * Write the header of xml report
     * @param write
     */
    private void startJunitOutput(Writer write) {
        Log.d(REPORT_LOG_TAG, REPORT_TITLE + "startJunitOutput");
        
        try {
            mWriter = write;
            mTestSuiteSerializer = newSerializer(mWriter);
            mTestSuiteSerializer.startDocument(null, null);
            
            mTestSuiteSerializer.startTag(null, "testsuites");
            mTestSuiteSerializer.startTag(null, "systeminfo");
            mTestSuiteSerializer.attribute(null, "sdkversion", TestUtils.getSDKVersion());
            mTestSuiteSerializer.attribute(null, "devicetype", TestUtils.getDeviceType());
            mTestSuiteSerializer.endTag(null, "systeminfo");
            mTestSuiteSerializer.startTag(null, "testsuite");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private XmlSerializer newSerializer(Writer writer) {
        try {
            XmlPullParserFactory pf = XmlPullParserFactory.newInstance();
            XmlSerializer serializer = pf.newSerializer();
            serializer.setOutput(writer);
            return serializer;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void sendStatus(int resultCode, Bundle results) {
        Log.d(REPORT_LOG_TAG, REPORT_TITLE + "sendStatus");
        super.sendStatus(resultCode, results);
        
        switch(resultCode) {
        case REPORT_VALUE_RESULT_ERROR:
            // add code for test error
        case REPORT_VALUE_RESULT_FAILURE:
            // add code for test failure
        case REPORT_VALUE_RESULT_OK:
            try {
                this.recordTestResult(resultCode, results);
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
            break;
        case REPORT_VALUE_RESULT_START:
            this.recordTestStart(results);
        default:
            break;
        }
    }
    
    private void recordTestStart(Bundle results) {
        Log.d(REPORT_LOG_TAG, REPORT_TITLE + "recordTestStart");
        
        mTestStarted = System.currentTimeMillis();
    }
    
    private void recordTestResult(int resultCode, Bundle results) throws IOException {
        Log.d(REPORT_LOG_TAG, REPORT_TITLE + "record test result for each test case");
        
        float time = (System.currentTimeMillis() - mTestStarted) / 1000.0f;
        String className = results.getString(REPORT_KEY_NAME_CLASS);
        String testMethod = results.getString(REPORT_KEY_NAME_TEST);
        String stack = results.getString(REPORT_KEY_STACK);
        int current = results.getInt(REPORT_KEY_NUM_CURRENT);
        int total = results.getInt(REPORT_KEY_NUM_TOTAL);
        
        mTestSuiteSerializer.startTag(null, "testcase");
        mTestSuiteSerializer.attribute(null, "classname", className);
        mTestSuiteSerializer.attribute(null, "name", testMethod);
        
        if (resultCode != REPORT_VALUE_RESULT_OK) {
            mTestSuiteSerializer.attribute(null, "testresult", TEST_FAIL);
            totalFail += 1;
            
            mTestSuiteSerializer.startTag(null, "failure");
            if (stack != null) {
                String reason = stack.substring(0, stack.indexOf('\n'));
                String message = "";
                int index = reason.indexOf(':');
                if (index > -1) {
                    message = reason.substring(index + 1);
                    reason = reason.substring(0, index);
                }
                mTestSuiteSerializer.attribute(null, "message", message);
                mTestSuiteSerializer.attribute(null, "type", reason);
                mTestSuiteSerializer.text(stack);
            }
            mTestSuiteSerializer.endTag(null, "failure");
        } else {
            mTestSuiteSerializer.attribute(null, "time", String.format("%.3f", time));
            mTestSuiteSerializer.attribute(null, "testresult", TEST_PASS);
            totalPass += 1;
        }
        mTestSuiteSerializer.endTag(null, "testcase"); 
        
        totalTime += time;
        
        if (current == total) {
            mTestSuiteSerializer.startTag(null, "system-out");
            mTestSuiteSerializer.endTag(null, "system-out");
            mTestSuiteSerializer.startTag(null, "system-err");
            mTestSuiteSerializer.endTag(null, "system-err");
            mTestSuiteSerializer.endTag(null, "testsuite");
            mTestSuiteSerializer.flush();
        }
    }
    
    @Override
    public void finish(int resultCode, Bundle results) {
        this.endTestSuites();
        super.finish(resultCode, results);
    }

    private void endTestSuites() {
        Log.d(REPORT_LOG_TAG, REPORT_TITLE + "endTestSuites");
        
        try {
            mTestSuiteSerializer.startTag(null, "summary");
            mTestSuiteSerializer.attribute(null, "totaltime", String.valueOf(totalTime));
            mTestSuiteSerializer.attribute(null, "totalfailed", String.valueOf(totalFail));
            mTestSuiteSerializer.attribute(null, "totalpass", String.valueOf(totalPass));
            mTestSuiteSerializer.attribute(null, "total", String.valueOf(totalPass + totalFail));
            mTestSuiteSerializer.endTag(null, "summary");
            
            mTestSuiteSerializer.endTag(null, "testsuites");
            mTestSuiteSerializer.endDocument();
            mTestSuiteSerializer.flush();
            mWriter.flush();
            mWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    protected static TestSuite addTests(final TestSuite ts, final String[] tcs) {
        
        final String pkName = "tv.fun.master";
        final String prefixTest = "test";
        final TestSuite suite = ts;
        
        Class<?> clazz;
        Constructor<?> cons = null;
        try {
            for (int i = 0; i < tcs.length; i++) {
                if (tcs[i].startsWith(pkName)) {
                    clazz = Class.forName(tcs[i]);
                    cons = clazz.getConstructor(String.class);
                } else if (tcs[i].startsWith(prefixTest)) {
                    suite.addTest((Test) cons.newInstance(tcs[i]));
                } else {
                    throw new RuntimeException("Invalid test case name or test class name!");
                }
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, String.format("%s Exception when build test suite.", TITLE));
            throw new RuntimeException(e.getMessage());
        }
        
        return suite;
    }
}
