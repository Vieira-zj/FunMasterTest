package tv.fun.master.test;

import com.robotium.solo.Solo;

import tv.fun.master.MainActivity;
import tv.fun.master.test.uiobjects.UiObjectFunMasterGarbageClearResultsActivity;
import tv.fun.master.test.uiobjects.UiObjectFunMasterMainActivity;
import tv.fun.master.testutils.TestTasks;
import tv.fun.master.view.ClearResultActivity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.util.Log;

import static tv.fun.master.testutils.TestGlobalVars.*;

public class TestFunMasterGarbageClearInst extends ActivityInstrumentationTestCase2<MainActivity> {
    
    private static MainActivity act = null;
    private static ClearResultActivity crAct = null;
    private static Instrumentation inst = null;
    private static Solo solo = null;
    
    UiObjectFunMasterMainActivity UiObjMainAct = new UiObjectFunMasterMainActivity();
    UiObjectFunMasterGarbageClearResultsActivity UiObjGCResAct = new UiObjectFunMasterGarbageClearResultsActivity();
    
    public TestFunMasterGarbageClearInst(String name) {
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
    
    public void testFunMasterGarbageClearSetUp() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        TestTasks.waitFunMasterReady(solo);

        solo.sendKey(Solo.ENTER);
        final int timeout = 15;
        TestTasks.wait(solo, timeout);  // garbage clear process finished within 15 sec.

        crAct = (ClearResultActivity) solo.getCurrentActivity();
    }
    
    public void testFunMasterGarbageClearTearDown() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        
        TestTasks.sendKeyBackAndWait(solo, 2);
    }
    
    // test case 1: verify garbage results UI.
    @Smoke
    public void testFunMasterGarbageResultsUI() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));

        final String textPercent = "100%";
        assertTrue("Verify 100% percent text failed.", solo.searchText(textPercent, true));
        
        final String textBestState = "系统已达最佳状态";
        boolean actual = textBestState.equals(UiObjGCResAct.getBestStateTextView(crAct).getText().toString());
        assertTrue("Verify best state text failed.", actual);
        
        final String textClearMemory = "清理内存";
        actual = UiObjGCResAct.getClearMemoryTextView(crAct).getText().toString().startsWith(textClearMemory);
        assertTrue("Verify clear memory text failed.", actual);
        
        final String textClearGarbage = "清理垃圾";
        actual = UiObjGCResAct.getClearGarbageTextView(crAct).getText().toString().startsWith(textClearGarbage);
        assertTrue("Verify clear garbage text failed.", actual);
    }
    
    // test case 2: verify after garbage clear process finished, finish_button is displayed and focused. 
    @Smoke
    public void testFunMasterGarbageClearFinishButton() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        UiObjGCResAct.waitForGarbageClearFinishButtonReady(solo);

        assertTrue("Verify finish_button is displayed and focused, failed.", 
                UiObjGCResAct.getGarbageClearFinishButton(crAct).isFocused());
    }
    
    // test case 3: verify after click finish button, back to main and one key optimize button is focused.
    @Smoke
    public void testFunMasterGarbageClearBackToMain() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        TestTasks.sendkeyAndWait(solo, Solo.ENTER, 3);
        TestTasks.waitForActivityReady(solo);
        
        final String textGCRes = "体检得分";
        boolean actual = textGCRes.equals(UiObjMainAct.getGarbageClearResultsContentTextView(act).getText().toString());
        assertTrue("Verify garbage clear results content text failed.", actual);
        
        assertTrue("Verify one key optimize button is focused, failed.", UiObjMainAct.getOneKeyOptimizeButton(act).isFocused());
    }
    
    // test case 4: verify the final score is updated as greater than 90.
    @Smoke
    public void testFinalScoreAfterGarbageClearFinish() {
        Log.d(LOG_TAG, String.format("%s running test case -> %s", TITLE, this.getName()));
        
        UiObjMainAct.waitForGarbageClearResultsScoreReady(solo, act);
        
        int score = Integer.parseInt(UiObjMainAct.getGarbageClearResultsScoreTextView(act).getText().toString());
        Log.d(LOG_TAG, String.format("%s garbage clear results score -> %d", TITLE, score));
        boolean actual = score >= 90 ? true : false;
        assertTrue("Verify garbage clear results score is greater than 90 failed.", actual);
        
        TestTasks.takeSnapshot(solo, "final_scores");
    }
    
    
}
