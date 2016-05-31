package tv.fun.master.testrunner;

import junit.framework.TestSuite;
import tv.fun.master.test.TestFunMasterDemo;
import android.test.InstrumentationTestRunner;
import android.test.InstrumentationTestSuite;


public class TestFunMasterRunnerDemo extends InstrumentationTestRunner {

    @Override
    public TestSuite getAllTests() {
        TestSuite ts = new InstrumentationTestSuite(this);

        ts.addTestSuite(TestFunMasterDemo.class);
        
//      ts.addTest(new TestFunMasterMainInst("testFunMasterMainSetUp"));
//      ts.addTest(new TestFunMasterMainInst("testFunMasterMainActivityUI"));
//      ts.addTest(new TestFunMasterMainInst("testFunMasterMainActivityDefaultFocus"));
//      ts.addTest(new TestFunMasterMainInst("testFunMasterMainTearDown"));

        return ts;
    }

}
