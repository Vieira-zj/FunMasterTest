package tv.fun.master.test;

import com.robotium.solo.Solo;

import tv.fun.master.MainActivity;
import tv.fun.master.NetworkTestActivity;
import tv.fun.master.test.uiobjects.UiObjectFunMasterMainActivity;
import tv.fun.master.test.uiobjects.UiObjectFunMasterNetworkSpeedTestActivity;
import tv.fun.master.testutils.TestTasks;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.util.Log;
import android.widget.TextView;

import static tv.fun.master.testutils.TestGlobalVars.*;

public class TestFunMasterNetworkSpeedTestInst extends ActivityInstrumentationTestCase2<MainActivity> {

    private static MainActivity act = null;
    private static NetworkTestActivity netAct = null;
    private static Instrumentation inst = null;
    private static Solo solo = null;
    
    UiObjectFunMasterMainActivity UiObjMainAct = new UiObjectFunMasterMainActivity();
    UiObjectFunMasterNetworkSpeedTestActivity UiObjNetTestAct = new UiObjectFunMasterNetworkSpeedTestActivity();
    
    public TestFunMasterNetworkSpeedTestInst(String name) {
        super(MainActivity.class);
        super.setName(name);
//        this.setActivityInitialTouchMode(false);
    }

    @Override
    public void setUp() {
        Log.d(LOG_TAG, String.format("%s ***** test case setup -> %s", TITLE, this.getName()));

        if (act == null) {act = this.getActivity();}
        if (inst == null) {inst = this.getInstrumentation();}
        if (solo == null) {solo = new Solo(inst, act);}
    }

    @Override
    public void tearDown() throws Exception {
        Log.d(LOG_TAG, String.format("%s ***** test case teardown -> %s", TITLE, this.getName()));

        //tearDown() is run after a test case has finished. 
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
//        solo.finishOpenedActivities();
    }
    
    public void testFunMasterNetworkSpeedTestSetUp() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        TestTasks.waitFunMasterReady(solo);
    }
    
    public void testFunMasterNetworkSpeedTestTearDown() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        TestTasks.sendKeyBackAndWait(solo, 2);
    }
    
    // test case 1: Verify network checking fragment UI.
    @Smoke
    public void testFunMasterNetworkCheckingUI() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        TestTasks.sendkeyAndWait(solo, Solo.DOWN);
        TestTasks.sendkeyAndWait(solo, Solo.ENTER, 2);
        
        final String textCheckingNetwork = "正在检测网络连接";
        assertTrue("Verify checking network text failed.", solo.searchText(textCheckingNetwork));
        netAct = (NetworkTestActivity) solo.getCurrentActivity();
        
        TextView netResult = UiObjNetTestAct.getCheckNetworkResultsTextView(netAct);
        TextView netType = UiObjNetTestAct.getCheckNetworkTypeTextView(netAct);
        
        final String textNetworkCheckSuccess = "网络检测正常";
        assertTrue("Verify network check success text failed.", solo.searchText(textNetworkCheckSuccess));
        
        final String textNetworkWired = "有线网";
        final String textNetworkWifi = "WiFi";
        assertTrue("Verify network wired text failed.", 
                textNetworkWired.equals(netType.getText().toString()) || 
                textNetworkWifi.equals(netType.getText().toString()));
        
        final String textNetworkConnectSuccess = "连接成功";
        assertTrue("Verify network connect success text failed.", 
                textNetworkConnectSuccess.equals(netResult.getText().toString()));
    }

    // test case 2: Verify network speed testing fragment UI.
    @Smoke
    public void testFunMasterNetworkSpeedTestingUI() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        final String textSpeedTesting = "正在测试网速";

        TestTasks.wait(solo, 3);
        assertTrue("Verify network speed testing text failed.", solo.searchText(textSpeedTesting));
    }
    
    // test case 3: Verify network speed test results fragment UI.
    @Smoke
    public void testFunMasterNetworkSpeedTestResultsUI() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        
//        final String textSpeedTesting = "正在测试网速";
//        TestTasks.waitForViewDisappear(solo, textSpeedTesting, 15);
        TestTasks.wait(solo, 18);
        
        final String textNetworkDownloadSpeed = "平均下载速度";
        assertTrue("Verify network download speed text failed.", solo.searchText(textNetworkDownloadSpeed));
        
        final String textNetworkSpeedRetestButton = "重新测速";
        assertTrue("Verify text for network speed retest button failed.", 
                textNetworkSpeedRetestButton.equals(UiObjNetTestAct.getNetworkSpeedRetestButton(netAct).getText().toString()));
        
        final String textNetworkSpeedTestFinishButton = "完 成";
        assertTrue("Verify text for network speed test finish button failed.", 
                textNetworkSpeedTestFinishButton.equals(UiObjNetTestAct.getNetworkSpeedTestFinishButton(netAct).getText().toString()));
        
        assertTrue("Verify network speed test finish button is displayed and focused, failed.", 
                UiObjNetTestAct.getNetworkSpeedTestFinishButton(netAct).isFocused());
    }
    
    // test case 4: Verify after click network speed test finish button, back to main activity and test speed button is focused.
    @Smoke
    public void testFunMasterNetworkSpeedTestBackToMain() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        
        TestTasks.sendkeyAndWait(solo, Solo.ENTER, 2);
        solo.waitForView(UiObjMainAct.getTvSpeedTestTextView(act));
        
        assertTrue("Verify back to main activity and test speed button is focused, failed.", 
                UiObjMainAct.getTvSpeedTestTextView(act).isFocused());
    }
    
    
}
