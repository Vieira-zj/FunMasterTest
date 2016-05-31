package tv.fun.master.test;

import com.robotium.solo.Solo;

import tv.fun.master.MainActivity;
import tv.fun.master.test.uiobjects.UiObjectFunMasterMainActivity;
import tv.fun.master.testutils.TestTasks;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.util.Log;

import static tv.fun.master.testutils.TestGlobalVars.*;

public class TestFunMasterMainInst extends ActivityInstrumentationTestCase2<MainActivity> {

    private static MainActivity act = null;
    private static Instrumentation inst = null;
    private static Solo solo = null;
    
    UiObjectFunMasterMainActivity UiObjMainAct = new UiObjectFunMasterMainActivity();
    
    public TestFunMasterMainInst(String name) {
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
    
    public void testFunMasterMainSetUp() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        TestTasks.waitFunMasterReady(solo);
    }
    
    public void testFunMasterMainTearDown() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        TestTasks.sendKeyBackAndWait(solo, 2);
    }
    
    // test case 1: verify all the UI elements is displayed as expected.
    @Smoke
    public void testFunMasterMainActivityUI() {
        final String textOneKeyOptimizeButton = "一键优化";
        boolean actual = textOneKeyOptimizeButton.equals(UiObjMainAct.getOneKeyOptimizeButton(act).getText().toString());
        assertTrue("Verify one_key_optimize_button failed.", actual);
        
        final String textTVSpeedTestTextView = "网络测速";
        actual = textTVSpeedTestTextView.equals(UiObjMainAct.getTvSpeedTestTextView(act).getText().toString());
        assertTrue("Verify tv_speed_button failed.", actual);
        
        final String textTvSelfLaunchManageTextView = "自启动管理";
        actual = textTvSelfLaunchManageTextView.equals(UiObjMainAct.getTvSelfLaunchManageTextView(act).getText().toString());
        assertTrue("Verify tv_self_launch_manage_button failed.", actual);
        
        final String textTvRestartTextView = "重启设备";
        actual = textTvRestartTextView.equals(UiObjMainAct.getTvRestartTextView(act).getText().toString());
        assertTrue("Verify tv_restart_button failed.", actual);
        
        assertTrue("Veirfy iv_device_info_button failed.", 
                solo.waitForView(UiObjMainAct.getIvDeviceInfoImageView(act)));
        
        assertTrue("Veirfy iv_settings_button failed.", 
                solo.waitForView(UiObjMainAct.getIvSettingsImageView(act)));
    }
    
    // test case 2: verify the default focus is set on the one key optimize button.
    @Smoke
    public void testFunMasterMainActivityDefaultFocus() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        
        assertTrue("Verify one_key_optimize_button is focused, failed.", 
                UiObjMainAct.getOneKeyOptimizeButton(act).isFocused());
    }
}
