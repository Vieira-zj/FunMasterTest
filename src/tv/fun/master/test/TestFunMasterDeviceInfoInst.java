package tv.fun.master.test;

import com.robotium.solo.Solo;

import tv.fun.master.MainActivity;
import tv.fun.master.test.uiobjects.UiObjectFunMasterDeviceInfoActivity;
import tv.fun.master.test.uiobjects.UiObjectFunMasterMainActivity;
import tv.fun.master.testutils.TestTasks;
import tv.fun.master.view.DeviceInfoActivity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.util.Log;
import android.widget.ListView;

import static tv.fun.master.testutils.TestGlobalVars.*;

public class TestFunMasterDeviceInfoInst extends ActivityInstrumentationTestCase2<MainActivity> {

    private static MainActivity act = null;
    private static DeviceInfoActivity deviceInfoAct = null;
    private static Instrumentation inst = null;
    private static Solo solo = null;
    
    UiObjectFunMasterMainActivity UiObjMainAct = new UiObjectFunMasterMainActivity();
    UiObjectFunMasterDeviceInfoActivity UiObjDeviceInfoAct = new UiObjectFunMasterDeviceInfoActivity();
    
    public TestFunMasterDeviceInfoInst(String name) {
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
    
    public void testFunMasterDeviceInfoSetUp() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        
        TestTasks.waitFunMasterReady(solo);
        UiObjMainAct.OpenDeviceInfoView(solo);
        
        assertTrue("Verify title on device info failed", UiObjDeviceInfoAct.WaitForDeviceInfoActivityReady(solo));
    }
    
    public void testFunMasterDeviceInfoTearDown() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        TestTasks.sendKeyBackAndWait(solo, 2);
    }
    
    // test case 1: verify keys in devices info activity are displayed as expected.
    @Smoke
    public void testFunMasterDeviceInfoUIKeys() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        
        String[] deviceinfoKeys = {"设备名称","系统版本","CPU","内存","存储空间","显卡","分辨率"};
        deviceInfoAct = (DeviceInfoActivity) solo.getCurrentActivity();
        ListView list = UiObjDeviceInfoAct.getDeviceInfoListView(deviceInfoAct);
        boolean actual;
        for (int i = 0; i < 7; i++) {
//            Log.d(LOG_TAG, String.format("%s check keys at -> %d", TITLE, i));
            actual = deviceinfoKeys[i].equals(UiObjDeviceInfoAct.getDeviceInfoItemKey(list, i).getText());
            assertTrue(String.format("Verify key %s failed", deviceinfoKeys[i]), actual);
        }
    }

    // test case 2: verify values in devices info activity are displayed as expected.
    @Smoke
    public void testFunMasterDeviceInfoUIValues() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        
        String[] deviceinfoValuesForMiBox = 
            {"MiBOX1S","4.2.2","ARMv7 Processor 2核 1.5GHz","785.3MB","2.2GB","Mali-400 MP","1280*720"};
//        String[] deviceinfoValuesForHimedia = 
//            {"HIMEDIA_A31S","1.7.4-R-20150116.1054","ARMv7 Processor 4核 1GHz","330.5MB","4.6GB","PowerVR SGX 544MP","1280*720"};
        
        ListView list = UiObjDeviceInfoAct.getDeviceInfoListView(deviceInfoAct);
        boolean actual;
        for (int i = 0; i < 7; i++) {
            if (i == 3 || i == 4) {  // memory and storage value is variable, ignore here
                continue;
            }
            
//            Log.d(LOG_TAG, String.format("%s check values at -> %d", TITLE, i));
            actual = deviceinfoValuesForMiBox[i].equals(UiObjDeviceInfoAct.getDeviceInfoItemValue(list, i).getText());
            assertTrue(String.format("Verify value %s failed.", deviceinfoValuesForMiBox[i]), actual);
        }
    }
    
    // test case 3: Verify after click back on device info activity, back to main and device info view is focused.
    @Smoke
    public void testFunMasterDeviceInfoBackToMain() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        
        TestTasks.sendKeyBackAndWait(solo, 2);
        TestTasks.waitForActivityReady(solo, 3);
        
        assertTrue("Verify back to main activity, and device info view is focused, failed", 
                UiObjMainAct.getIvDeviceInfoImageView(act).isFocused());
    }
    
    
}
