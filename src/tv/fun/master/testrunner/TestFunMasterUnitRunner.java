package tv.fun.master.testrunner;

import static tv.fun.master.testrunner.FunMasterTestCases.unittcsSmoke;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestFunMasterUnitRunner extends TestCase {
    
//    public static void main(String[] args) {
//        junit.textui.TestRunner.run(getTestSuite());
//    }
    
    public static Test suite() {
        
        TestSuite suite = new TestSuite();
        
//        suite.addTestSuite(TestFunMasterAutoLaunchManageUnitTest.class);
        
//        suite.addTest(new TestFunMasterAutoLaunchManageUnitTest("testIsEnableAppWithTwoEnabledComponents"));
//        suite.addTest(new TestFunMasterAutoLaunchManageUnitTest("testIsEnableAppWithTwoDisabledComponents"));
        
        return TestFunMasterRunnerBasic.addTests(suite, unittcsSmoke);
    }

}
