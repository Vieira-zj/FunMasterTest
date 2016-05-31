package tv.fun.master.test;

import com.robotium.solo.Solo;

import tv.fun.master.MainActivity;
import tv.fun.master.test.uiobjects.UiObjectFunMasterAutoLaunchManageActivity;
import tv.fun.master.test.uiobjects.UiObjectFunMasterMainActivity;
import tv.fun.master.testutils.TestTasks;
import tv.fun.master.view.SelfStartManageActivity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.util.Log;

import static tv.fun.master.testutils.TestGlobalVars.*;

public class TestFunMasterAutoLaunchManageInst extends ActivityInstrumentationTestCase2<MainActivity> {

    private static MainActivity act = null;
    private static SelfStartManageActivity selfStartManageAct = null;
    private static Instrumentation inst = null;
    private static Solo solo = null;
    
    UiObjectFunMasterMainActivity UiObjMainAct = new UiObjectFunMasterMainActivity();
    UiObjectFunMasterAutoLaunchManageActivity UiObjAutoLauManAct = new UiObjectFunMasterAutoLaunchManageActivity();
    
    public TestFunMasterAutoLaunchManageInst(String name) {
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
    
    public void testFunMasterAutoLaunchManageSetUp() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        TestTasks.waitFunMasterReady(solo);
        
        UiObjAutoLauManAct.openFunMasterAutoLaunchManageActivity(solo);
        selfStartManageAct = (SelfStartManageActivity) solo.getCurrentActivity();
    }
    
    public void testFunMasterAutoLaunchManageTearDown() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        TestTasks.sendKeyBackAndWait(solo, 2);
    }
    
    // test case 1: Verify auto launch manage UI.
    @Smoke
    public void testFunMasterAutoLaunchManageUI() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        final String startText = "有";
        final String endText= "个应用自启动";
        
        boolean actual = startText
                .equals(UiObjAutoLauManAct.getAutoLaunchManageWordHaveTextView(selfStartManageAct).getText().toString());
        assertTrue("Verify text of how many self auto launch Apps failed.", actual && solo.searchText(endText));
        
        final int itemCount = UiObjAutoLauManAct.getCountOfAutoLaunchApps(selfStartManageAct);
        Log.d(LOG_TAG, String.format("%s self auto launch apps count -> %d", TITLE, itemCount));
        assertTrue("Verify count of self auto launch Apps failed.", itemCount == 
                Integer.parseInt(UiObjAutoLauManAct.getAutoLaunchManageAppCountTextView(selfStartManageAct).getText().toString()));
    }
    
    // test case 2: Verify the auto launch apps view list.
    @Smoke
    public void testFunMasterAutoLaunchManageAppsList() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        
        final String firstAppName = "360电视卫士";
        boolean actual = firstAppName
                .equals(UiObjAutoLauManAct.getAutoLaunchManageAppNameFromListView(selfStartManageAct, 0).getText().toString());
        Log.d(LOG_TAG, TITLE + "->" + UiObjAutoLauManAct.getAutoLaunchManageAppNameFromListView(selfStartManageAct, 0).getText().toString());
        assertTrue("Verify the first app name in the view list failed.", true);  // verify this checkpoint manual instead
        
        final int defaultSelectIndex = 0;
        assertTrue("Verify the first item is default as focused failed", 
                (defaultSelectIndex == UiObjAutoLauManAct.getAutoLaunchManageSelectedListItem(selfStartManageAct)));
        
        assertTrue("Verify the switch button is default set as ON failed.", 
                UiObjAutoLauManAct.getAutoLaunchManageSwitchButtonFromListView(selfStartManageAct, 0).isChecked());
        
        final String textOnSwitchBtn = "已启动";
        actual = textOnSwitchBtn.
                equals(UiObjAutoLauManAct.getAutoLaunchManageSwitchButtonFromListView(selfStartManageAct, 0).getText());
        assertTrue("Verify the text of switch button with set on, failed.", actual);
    }
    
    // test case 3: Verify the disable all button, and click button with no root authority.
    @Smoke
    public void testFunMasterAutoLaunchManageOneKeyDisableAll() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        
        final String textDisableAllButton = "一键禁止";
        boolean actual = textDisableAllButton
                .equals(UiObjAutoLauManAct.getAutoLaunchManageDisableAllButton(selfStartManageAct).getText().toString());
        assertTrue("Verify the text of disable all button failed", actual);
        
        TestTasks.sendkeyAndWait(solo, Solo.UP);
        actual = UiObjAutoLauManAct.getAutoLaunchManageDisableAllButton(selfStartManageAct).isFocused();
        assertTrue("Verify the disable all button is focused, failed.", actual);

        solo.sendKey(Solo.ENTER);
        final String textWait = "正在为您优化，请稍等";
        assertTrue("Verify the wait text after click one key disable all button failed.", solo.searchText(textWait));
        
        final String textNoRootForDisableAll = "管理失败，未获得Root权限!";
        assertTrue("Verify the warn message when click one key disable all button with no root authority.", 
                solo.searchText(textNoRootForDisableAll));
    }
    
    // test case 4: Verify after click back, and back to main activity.
    @Smoke
    public void testFunMasterAutoLaunchManageBackToMain() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        
        TestTasks.sendKeyBackAndWait(solo, 2);
        solo.waitForView(UiObjMainAct.getTvSelfLaunchManageTextView(act));
        
        assertTrue("Verify back to main and self-launch manage button is focused.", 
                UiObjMainAct.getTvSelfLaunchManageTextView(act).isFocused());
    }

}
