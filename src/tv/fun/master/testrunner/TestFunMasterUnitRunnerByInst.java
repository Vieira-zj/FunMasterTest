package tv.fun.master.testrunner;

import static tv.fun.master.testrunner.FunMasterTestCases.unittcsSmoke;

import junit.framework.TestSuite;

public class TestFunMasterUnitRunnerByInst extends TestFunMasterRunnerBasic {

    @Override
    public TestSuite getAllTests() {
        
        return addTests(new TestSuite(), unittcsSmoke);
    }
    
}
