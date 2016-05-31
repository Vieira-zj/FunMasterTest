package tv.fun.master.unittest;

import tv.fun.master.R;
import tv.fun.master.SettingsActivity;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.Smoke;
import android.util.Log;
import android.widget.ListView;

import static tv.fun.master.testutils.TestGlobalVars.*;

public class TestFunMasterSettingsActUnitTest extends ActivityUnitTestCase<SettingsActivity> {
    
    private Intent mStartIntent;
    private Activity act;
//    private Instrumentation inst;
    
    public TestFunMasterSettingsActUnitTest(String name) {
        super(SettingsActivity.class);
        super.setName(name);
    }
    
    @Override
    protected void setUp() throws Exception {
//        Log.d(LOG_TAG, String.format("%s activity unit test case setup -> %s", TITLE, this.getName()));
        
        super.setUp();
//        mStartIntent = new Intent(Intent.ACTION_MAIN);
        mStartIntent = new Intent();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        
        //TODO
    }
    
    @Smoke
    public void testPreconditions() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        this.startActivity(mStartIntent, null, null);
        assertNotNull("SettingsActivity start failed.", this.getActivity());
    }
    
    @Smoke
    public void testSettingsListView() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        this.startActivity(mStartIntent, null, null);
        
        act = this.getActivity();
        ListView list = (ListView) act.findViewById(R.id.list);
        assertNotNull("List view is displayed failed.", list);
        
        assertTrue("Verify there are 4 setting items failed.", list.getAdapter().getCount() == 4);
        
        String itemDesc = list.getAdapter().getItem(0).toString();
        assertTrue("Verify the title of 1st item in list view failed", 
                itemDesc.contains(this.getInstrumentation().getTargetContext().getString(R.string.setting_auto_optimize_title)));
    }
}
