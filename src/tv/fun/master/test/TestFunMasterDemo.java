package tv.fun.master.test;

import tv.fun.master.MainActivity;
import tv.fun.master.testutils.TestGlobalVars;
import tv.fun.master.testutils.TestTasks;
import android.app.Instrumentation;
//import android.app.Activity;
//import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
//import android.test.MoreAsserts;
//import android.test.UiThreadTest;
//import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.Smoke;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;
//import android.view.KeyEvent;

import com.robotium.solo.Solo;

public class TestFunMasterDemo extends ActivityInstrumentationTestCase2<MainActivity> {

//    private static MainActivity act = null;
//    private static Instrumentation inst = null;
//    private static Solo solo = null;
    
    private MainActivity act = null;
    private Instrumentation inst = null;
    private Solo solo;
    
    public TestFunMasterDemo(String name) {
        super(MainActivity.class);
        super.setName(name);
        
        this.setActivityInitialTouchMode(false);
    }

    @Override
    public void setUp() {
        Log.d(TestGlobalVars.LOG_TAG, String.format("%s ***** test case setup -> %s", TestGlobalVars.TITLE, this.getName()));

//        if (act == null) {act = this.getActivity();}
//        if (inst == null) {inst = this.getInstrumentation();}
//        if (solo == null) {solo = new Solo(inst, act);}
        
        inst = this.getInstrumentation();
        act = (MainActivity) TestTasks.startAndGetActivity(inst, MainActivity.class);
//        act = this.getActivity();  // test process is pending for master main activity idle
        solo = new Solo(inst, act);
        
//        TestTasks.restartFunMaster(solo, inst);
    }

    @Override
    public void tearDown() throws Exception {
        Log.d(TestGlobalVars.LOG_TAG, String.format("%s ***** test case teardown -> %s", TestGlobalVars.TITLE, this.getName()));

        //tearDown() is run after a test case has finished. 
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
//        act.finish();
    }

    @Smoke
    public void testFunMasterDemo01() {
        Log.d(TestGlobalVars.LOG_TAG, String.format("%s running test case -> %s", TestGlobalVars.TITLE, this.getName()));

        TestTasks.waitFunMasterReady(solo);
        TestTasks.sendkeyAndWait(solo, Solo.RIGHT, 1);
        TestTasks.sendkeyAndWait(solo, Solo.RIGHT, 1);
        TestTasks.sendkeyAndWait(solo, Solo.RIGHT, 1);
        
//        solo.wait(1000);
        solo.sleep(1);
        
//        Button btnOneKey = solo.getButton("一键优化");
//        Log.d(TestGlobalVars.LOG_TAG, TestGlobalVars.TITLE + btnOneKey.getText());
//        solo.clickOnButton("一键优化");
//        solo.waitForText("完成");
//        TestTasks.wait(solo, 3 * 1000);
//        solo.sendKey(solo.ENTER);
//        solo.clickOnButton("完成");
//        TestTasks.wait(solo, 3 * 1000);
        
//          solo.clickOnText("网络测速");
        
        Log.d(TestGlobalVars.LOG_TAG, 
                String.format("%s current activity -> %s", TestGlobalVars.TITLE, solo.getCurrentActivity().getPackageName()));
        assertEquals(true, solo.waitForActivity(MainActivity.class));
    }
    
    @Smoke
    public void testFunMasterDemo02() {
        Log.d(TestGlobalVars.LOG_TAG, String.format("%s running test case -> %s", TestGlobalVars.TITLE, this.getName()));

        TestTasks.waitFunMasterReady(solo);
//        Button btnOneKey = solo.getButton("一键优化");
//        Button btnOneKey = (Button) act.findViewById(0x7f08000e);
        solo.sendKey(Solo.ENTER);
        solo.sleep(1000 * 10);
        
//        TestTasks.wait(solo, 10);
//        Log.d(TestGlobalVars.LOG_TAG, String.format("%s one key button text -> %s", TestGlobalVars.TITLE, btnOneKey.getText()));
        
//        MoreAsserts.assertContainsRegex(expectedRegex, actual);
//        ViewAsserts.assertHasScreenCoordinates(origin, view, x, y);
    }
    
    @Suppress
    public void testFunMasterDemo03() {
        TestTasks.waitFunMasterReady(solo);
        solo.takeScreenshot("main_activity");
        
//        int pid = TestUtils.getProcessId(inst.getTargetContext());
//        TestUtils.execShellAdminCmd("kill " + String.valueOf(pid));
        
//        solo.goBack();
//        solo.sendKey(solo.CLOSED);
//        TestTasks.wait(solo, 3);
//        Log.d(TestGlobalVars.LOG_TAG, TestGlobalVars.TITLE + "go back done!");
    }
}
