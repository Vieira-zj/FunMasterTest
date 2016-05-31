package tv.fun.master.unittest;

import static tv.fun.master.testutils.TestGlobalVars.LOG_TAG;
import static tv.fun.master.testutils.TestGlobalVars.TITLE;

import java.util.List;

import tv.fun.master.testutils.TestUtils;
import tv.fun.master.view.SelfStartManageActivity;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.Smoke;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;

public class TestFunMasterAutoLaunchManageActUnitTest extends ActivityUnitTestCase<SelfStartManageActivity> {
    
    private Intent mStartIntent;
    
    public TestFunMasterAutoLaunchManageActUnitTest(String name) {
        super(SelfStartManageActivity.class);
        super.setName(name);
    }
    
    @Override
    public void setUp() throws Exception {
        super.setUp();
        mStartIntent = new Intent();
    }
    
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Suppress
    public void testPreconditions() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        this.startActivity(mStartIntent, null, null);
        assertNotNull("SettingsActivity start failed.", this.getActivity());
    }
    
    @Smoke
    public void testGetPrivateFieldValuePackageList() {
        this.startActivity(mStartIntent, null, null);
        
        @SuppressWarnings("unchecked")
        List<String> list = (List<String>) TestUtils.PrivateUtil.getFieldValue(this.getActivity(), "mPackageList");
        
        assertNotNull(list);
    }
    
}
