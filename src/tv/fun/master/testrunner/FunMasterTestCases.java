package tv.fun.master.testrunner;

public class FunMasterTestCases {

// -------------------------------------------------
// Unit test cases
// -------------------------------------------------
    
    public final static String[] unittcsSmoke = {
        "tv.fun.master.unittest.TestFunMasterAutoLaunchManageUnitTest",
        "testAndroidTestCaseSetupProperly",
        "testIsEnableAppWithTwoEnabledComponents",
        "testIsEnableAppWithTwoDisabledComponents",
        "testIsEnableAppWithOneEnabledOneDisabledComponents",
        "testIsEnableAppWithOneDefaultOneDisabledComponents",
        
        "testGetCommandsDoUncheckOnSwitchButtonForTwoEnabledComponents",
        "testGetCommandsDoUncheckOnSwitchButtonForOneDefaultOneEnabledComponents",
        "testGetCommandsDoUncheckOnSwitchButtonForOneEnabledOneDisabledComponents",
        "testGetCommandsDoCheckOnSwitchButtonForTwoDisabledComponents",
        "testGetCommandsDoCheckOnSwitchButtonForOneDefaultOneDisabledComponents",
        
        "testGetDisableAllCommandsWithAllEnabledComponents",
        "testGetDisableAllCommandsWithAllDisabledComponents",
        "testGetDisableAllCommandsWithSomeEnabledSomeDisabledComponents",
        
        "tv.fun.master.unittest.TestFunMasterDataManagerUnitTest",
        "testAndroidTestCaseSetupProperly",
        "testGetJsonFromServerForResidueConnectToServer",
        "testGetJsonFromServerForResidueWithDefaultTimeStamp",
        "testGetJsonFromServerForResidueWithSameTimeStamp",
        "testGetResidueDataFromLocalFileInAssets",
        "testGetResidueDataFromLocalFileInDataData"
    };
    
// -------------------------------------------------
// Activity UI smoke test cases
// -------------------------------------------------
    public final static String[] tcsSmoke = {
        "tv.fun.master.test.TestFunMasterMainInst",
        "testFunMasterMainSetUp", 
        "testFunMasterMainActivityUI", 
        "testFunMasterMainActivityDefaultFocus", 
        "testFunMasterMainTearDown",
        
        "tv.fun.master.test.TestFunMasterGarbageClearInst",
        "testFunMasterGarbageClearSetUp", 
        "testFunMasterGarbageResultsUI", 
        "testFunMasterGarbageClearFinishButton", 
        "testFunMasterGarbageClearBackToMain", 
        "testFinalScoreAfterGarbageClearFinish", 
        "testFunMasterGarbageClearTearDown",
        
        "tv.fun.master.test.TestFunMasterDeviceInfoInst",
        "testFunMasterDeviceInfoSetUp", 
        "testFunMasterDeviceInfoUIKeys", 
//        "testFunMasterDeviceInfoUIValues", 
        "testFunMasterDeviceInfoBackToMain", 
        "testFunMasterDeviceInfoTearDown",
        
        "tv.fun.master.test.TestFunMasterNetworkSpeedTestInst",
        "testFunMasterNetworkSpeedTestSetUp", 
        "testFunMasterNetworkCheckingUI", 
        "testFunMasterNetworkSpeedTestingUI", 
        "testFunMasterNetworkSpeedTestResultsUI",
        "testFunMasterNetworkSpeedTestBackToMain", 
        "testFunMasterNetworkSpeedTestTearDown",
        
        "tv.fun.master.test.TestFunMasterAutoLaunchManageInst",
        "testFunMasterAutoLaunchManageSetUp", 
        "testFunMasterAutoLaunchManageUI",
        "testFunMasterAutoLaunchManageAppsList",
        "testFunMasterAutoLaunchManageOneKeyDisableAll", 
        "testFunMasterAutoLaunchManageBackToMain",
        "testFunMasterAutoLaunchManageTearDown"
        };
    
    public final static String[] tcsMain = {
            "tv.fun.master.test.TestFunMasterMainInst",
            "testFunMasterMainSetUp", 
            "testFunMasterMainActivityUI", 
            "testFunMasterMainActivityDefaultFocus", 
            "testFunMasterMainTearDown"};
    
    public final static String[] tcsDeviceInfo = {
            "tv.fun.master.test.TestFunMasterDeviceInfoInst",
            "testFunMasterDeviceInfoSetUp", 
            "testFunMasterDeviceInfoUIKeys", 
            "testFunMasterDeviceInfoUIValues", 
            "testFunMasterDeviceInfoBackToMain", 
            "testFunMasterDeviceInfoTearDown"};
    
    public final static String[] tcsGarbageClear = {
            "tv.fun.master.test.TestFunMasterGarbageClearInst",
            "testFunMasterGarbageClearSetUp", 
            "testFunMasterGarbageResultsUI", 
            "testFunMasterGarbageClearFinishButton", 
            "testFunMasterGarbageClearBackToMain", 
            "testFinalScoreAfterGarbageClearFinish", 
            "testFunMasterGarbageClearTearDown"};
    
    public final static String[] tcsNetworkSpeedTest = {
            "tv.fun.master.test.TestFunMasterNetworkSpeedTestInst",
            "testFunMasterNetworkSpeedTestSetUp", 
            "testFunMasterNetworkCheckingUI", 
            "testFunMasterNetworkSpeedTestingUI", 
            "testFunMasterNetworkSpeedTestResultsUI",
            "testFunMasterNetworkSpeedTestBackToMain", 
            "testFunMasterNetworkSpeedTestTearDown"};
    
    public final static String[] tcsAutoLaunchManage = {
            "tv.fun.master.test.TestFunMasterAutoLaunchManageInst",
            "testFunMasterAutoLaunchManageSetUp", 
            "testFunMasterAutoLaunchManageUI",
            "testFunMasterAutoLaunchManageAppsList",
            "testFunMasterAutoLaunchManageOneKeyDisableAll", 
            "testFunMasterAutoLaunchManageBackToMain",
            "testFunMasterAutoLaunchManageTearDown"};
    
}
