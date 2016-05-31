package tv.fun.master.testrunner;

import static tv.fun.master.testrunner.FunMasterTestCases.*;

import junit.framework.TestSuite;
import android.test.InstrumentationTestSuite;


public class TestFunMasterInstRunner extends TestFunMasterRunnerBasic {
// TestFunMasterRunnerBasic or InstrumentationTestRunner
    
    @Override
    public TestSuite getAllTests() {
        
        TestSuite ts = new InstrumentationTestSuite(this);
        
        // smoke test
//        this.addTests(ts, tcsMain);
//        this.addTests(ts, tcsDeviceInfo);
//        this.addTests(ts, tcsGarbageClear);
//        this.addTests(ts, tcsNetworkSpeedTest);
//        this.addTests(ts, tcsAutoLaunchManage);
        
        return addTests(ts, tcsSmoke);
    }
    
    
}
