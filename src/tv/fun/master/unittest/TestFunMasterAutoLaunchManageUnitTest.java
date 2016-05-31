package tv.fun.master.unittest;

import static tv.fun.master.testutils.TestGlobalVars.LOG_TAG;
import static tv.fun.master.testutils.TestGlobalVars.TITLE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tv.fun.master.testutils.TestUtils;
import tv.fun.master.view.SelfStartManageActivity;
import tv.fun.master.view.SelfStartManageActivity.AppDetail;
import android.content.pm.PackageManager;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.Smoke;
import android.util.Log;

public class TestFunMasterAutoLaunchManageUnitTest extends AndroidTestCase {
    
    SelfStartManageActivity act;
    
    public TestFunMasterAutoLaunchManageUnitTest(String name) {
        super.setName(name);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        act = new SelfStartManageActivity();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Override
    @Smoke
    public void testAndroidTestCaseSetupProperly() {
        Log.d(LOG_TAG, String.format("%s ----- running class TestFunMasterAutoLaunchManageUnitTest", TITLE));
    };

    // --------------- isEnableApp() -----------------------
    @Smoke
    public void testIsEnableAppWithTwoEnabledComponents() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final String packageName = "com.example.testisenableapp";
        
        // build list
        List<String> listpk = new ArrayList<String>(3);
        listpk.add(packageName);
        TestUtils.PrivateUtil.setFieldValue(act, "mPackageList", listpk);
        
        // build map
        AppDetail ad1 = new AppDetail();
        ad1.packageName = packageName;
        ad1.receiverName = "com.example.testreceiver01";
        ad1.bootState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;  // to be update
        
        AppDetail ad2 = new AppDetail();
        ad2.packageName = packageName;
        ad2.receiverName = "com.example.testreceiver02";
        ad2.bootState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;  // to be update
        
        List<AppDetail> listad = new ArrayList<AppDetail>(3);
        listad.add(ad1);
        listad.add(ad2);
        
        Map<String, List<AppDetail>> mapad = new HashMap<String, List<AppDetail>>(3);
        mapad.put(packageName, listad);
        TestUtils.PrivateUtil.setFieldValue(act, "mAppReceiverMap", mapad);
        
        // assert
        boolean actual = (Boolean) TestUtils.PrivateUtil.invoke(
                act, "isEnableApp", new Class<?>[] {String.class}, new Object[] {packageName});
        
        assertTrue("Failed, invoke isEnableApp(), pass app with 2 enabled receivers, expected return true.", actual);
    }
    
    @Smoke
    public void testIsEnableAppWithTwoDisabledComponents() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final String packageName = "com.example.testisenableapp";
        
        // build list
        List<String> listpk = new ArrayList<String>(3);
        listpk.add(packageName);
        TestUtils.PrivateUtil.setFieldValue(act, "mPackageList", listpk);
        
        // build map
        AppDetail ad1 = new AppDetail();
        ad1.packageName = packageName;
        ad1.receiverName = "com.example.testreceiver01";
        ad1.bootState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        
        AppDetail ad2 = new AppDetail();
        ad2.packageName = packageName;
        ad2.receiverName = "com.example.testreceiver02";
        ad2.bootState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        
        List<AppDetail> listad = new ArrayList<AppDetail>(3);
        listad.add(ad1);
        listad.add(ad2);
        
        Map<String, List<AppDetail>> mapad = new HashMap<String, List<AppDetail>>(3);
        mapad.put(packageName, listad);
        TestUtils.PrivateUtil.setFieldValue(act, "mAppReceiverMap", mapad);
        
        // assert
        boolean actual = (Boolean) TestUtils.PrivateUtil.invoke(
                act, "isEnableApp", new Class<?>[] {String.class}, new Object[] {packageName});
        
        assertFalse("Failed, invoke isEnableApp(), pass app with 2 disabled receivers, expected return false.", actual);
    }
    
    @Smoke
    public void testIsEnableAppWithOneEnabledOneDisabledComponents() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final String packageName = "com.example.testisenableapp";
        
        // build list
        List<String> listpk = new ArrayList<String>(3);
        listpk.add(packageName);
        TestUtils.PrivateUtil.setFieldValue(act, "mPackageList", listpk);
        
        // build map
        AppDetail ad1 = new AppDetail();
        ad1.packageName = packageName;
        ad1.receiverName = "com.example.testreceiver01";
        ad1.bootState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        
        AppDetail ad2 = new AppDetail();
        ad2.packageName = packageName;
        ad2.receiverName = "com.example.testreceiver02";
        ad2.bootState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        
        List<AppDetail> listad = new ArrayList<AppDetail>(3);
        listad.add(ad1);
        listad.add(ad2);
        
        Map<String, List<AppDetail>> mapad = new HashMap<String, List<AppDetail>>(3);
        mapad.put(packageName, listad);
        TestUtils.PrivateUtil.setFieldValue(act, "mAppReceiverMap", mapad);
        
        // assert
        boolean actual = (Boolean) TestUtils.PrivateUtil.invoke(
                act, "isEnableApp", new Class<?>[] {String.class}, new Object[] {packageName});
        
        assertTrue(
                "Failed, invoke isEnableApp(), pass app with 1 enabled and 1 disbaled receivers, expected return true.", actual);
    }
    
    @Smoke
    public void testIsEnableAppWithOneDefaultOneDisabledComponents() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final String packageName = "com.example.testisenableapp";
        
        // build list
        List<String> listpk = new ArrayList<String>(3);
        listpk.add(packageName);
        TestUtils.PrivateUtil.setFieldValue(act, "mPackageList", listpk);
        
        // build map
        AppDetail ad1 = new AppDetail();
        ad1.packageName = packageName;
        ad1.receiverName = "com.example.testreceiver01";
        ad1.bootState = PackageManager.COMPONENT_ENABLED_STATE_DEFAULT;
        
        AppDetail ad2 = new AppDetail();
        ad2.packageName = packageName;
        ad2.receiverName = "com.example.testreceiver02";
        ad2.bootState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        
        List<AppDetail> listad = new ArrayList<AppDetail>(3);
        listad.add(ad1);
        listad.add(ad2);
        
        Map<String, List<AppDetail>> mapad = new HashMap<String, List<AppDetail>>(3);
        mapad.put(packageName, listad);
        TestUtils.PrivateUtil.setFieldValue(act, "mAppReceiverMap", mapad);
        
        // assert
        boolean actual = (Boolean) TestUtils.PrivateUtil.invoke(
                act, "isEnableApp", new Class<?>[] {String.class}, new Object[] {packageName});
        
        assertTrue(
                "Failed, invoke isEnableApp(), pass app with 1 disabled and 1 default receivers, expected return true.", actual);
    }
    
    // --------------- getCommands() -----------------------
    @Smoke
    public void testGetCommandsDoUncheckOnSwitchButtonForTwoEnabledComponents() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final String packageName = "com.example.testisenableapp";
        final boolean checked = false;
        final String expectedCmd0 = "pm disable com.example.testisenableapp/com.example.testreceiver01";
        final String expectedCmd1 = "pm disable com.example.testisenableapp/com.example.testreceiver02";
        
        // build map
        AppDetail ad1 = new AppDetail();
        ad1.packageName = packageName;
        ad1.receiverName = "com.example.testreceiver01";
        ad1.bootState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        
        AppDetail ad2 = new AppDetail();
        ad2.packageName = packageName;
        ad2.receiverName = "com.example.testreceiver02";
        ad2.bootState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        
        List<AppDetail> listad = new ArrayList<AppDetail>(3);
        listad.add(ad1);
        listad.add(ad2);
        
        Map<String, List<AppDetail>> mapad = new HashMap<String, List<AppDetail>>(3);
        mapad.put(packageName, listad);
        TestUtils.PrivateUtil.setFieldValue(act, "mAppReceiverMap", mapad);
        
        // assert
        @SuppressWarnings("unchecked")
        List<String> cmds = (List<String>) TestUtils.PrivateUtil.invoke(
                act, "getCommands", new Class<?>[] {String.class, boolean.class}, new Object[] {packageName, checked});
        
        assertEquals("Failed, verify the list size for commands.", 2, cmds.size());
        
        assertEquals("Failed, verify the command0 when do uncheck on switch button for two enabled components.", 
                expectedCmd0, cmds.get(0));
        assertEquals("Failed, verify the command1 when do uncheck on switch button for two enabled components.", 
                expectedCmd1, cmds.get(1));
    }
    
    @Smoke
    public void testGetCommandsDoUncheckOnSwitchButtonForOneDefaultOneEnabledComponents() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final String packageName = "com.example.testisenableapp";
        final boolean checked = false;
        final String expectedCmd0 = "pm disable com.example.testisenableapp/com.example.testreceiver01";
        final String expectedCmd1 = "pm disable com.example.testisenableapp/com.example.testreceiver02";
        
        // build map
        AppDetail ad1 = new AppDetail();
        ad1.packageName = packageName;
        ad1.receiverName = "com.example.testreceiver01";
        ad1.bootState = PackageManager.COMPONENT_ENABLED_STATE_DEFAULT;
        
        AppDetail ad2 = new AppDetail();
        ad2.packageName = packageName;
        ad2.receiverName = "com.example.testreceiver02";
        ad2.bootState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        
        List<AppDetail> listad = new ArrayList<AppDetail>(3);
        listad.add(ad1);
        listad.add(ad2);
        
        Map<String, List<AppDetail>> mapad = new HashMap<String, List<AppDetail>>(3);
        mapad.put(packageName, listad);
        TestUtils.PrivateUtil.setFieldValue(act, "mAppReceiverMap", mapad);
        
        // assert
        @SuppressWarnings("unchecked")
        List<String> cmds = (List<String>) TestUtils.PrivateUtil.invoke(
                act, "getCommands", new Class<?>[] {String.class, boolean.class}, new Object[] {packageName, checked});
        
        assertEquals("Failed, verify the list size for commands.", 2, cmds.size());
        
        assertEquals("Failed, verify the command0 when do uncheck on switch button for one enabled and one default components.", 
                expectedCmd0, cmds.get(0));
        assertEquals("Failed, verify the command1 when do uncheck on switch button for one enabled and one default components.", 
                expectedCmd1, cmds.get(1));
    }
    
    @Smoke
    public void testGetCommandsDoUncheckOnSwitchButtonForOneEnabledOneDisabledComponents() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final String packageName = "com.example.testisenableapp";
        final boolean checked = false;
        final String expectedCmd0 = "pm disable com.example.testisenableapp/com.example.testreceiver01";
        
        // build map
        AppDetail ad1 = new AppDetail();
        ad1.packageName = packageName;
        ad1.receiverName = "com.example.testreceiver01";
        ad1.bootState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        
        AppDetail ad2 = new AppDetail();
        ad2.packageName = packageName;
        ad2.receiverName = "com.example.testreceiver02";
        ad2.bootState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        
        List<AppDetail> listad = new ArrayList<AppDetail>(3);
        listad.add(ad1);
        listad.add(ad2);
        
        Map<String, List<AppDetail>> mapad = new HashMap<String, List<AppDetail>>(3);
        mapad.put(packageName, listad);
        TestUtils.PrivateUtil.setFieldValue(act, "mAppReceiverMap", mapad);
        
        // assert
        @SuppressWarnings("unchecked")
        List<String> cmds = (List<String>) TestUtils.PrivateUtil.invoke(
                act, "getCommands", new Class<?>[] {String.class, boolean.class}, new Object[] {packageName, checked});
        
        assertEquals("Failed, verify the list size for commands.", 1, cmds.size());
        
        assertEquals("Failed, verify the command0 when do uncheck on switch button for one enabled and one disabled components.", 
                expectedCmd0, cmds.get(0));
    }
    
    @Smoke
    public void testGetCommandsDoCheckOnSwitchButtonForTwoDisabledComponents() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final String packageName = "com.example.testisenableapp";
        final boolean checked = true;
        final String expectedCmd0 = "pm enable com.example.testisenableapp/com.example.testreceiver01";
        final String expectedCmd1 = "pm enable com.example.testisenableapp/com.example.testreceiver02";
        
        // build map
        AppDetail ad1 = new AppDetail();
        ad1.packageName = packageName;
        ad1.receiverName = "com.example.testreceiver01";
        ad1.bootState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        
        AppDetail ad2 = new AppDetail();
        ad2.packageName = packageName;
        ad2.receiverName = "com.example.testreceiver02";
        ad2.bootState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        
        List<AppDetail> listad = new ArrayList<AppDetail>(3);
        listad.add(ad1);
        listad.add(ad2);
        
        Map<String, List<AppDetail>> mapad = new HashMap<String, List<AppDetail>>(3);
        mapad.put(packageName, listad);
        TestUtils.PrivateUtil.setFieldValue(act, "mAppReceiverMap", mapad);
        
        // assert
        @SuppressWarnings("unchecked")
        List<String> cmds = (List<String>) TestUtils.PrivateUtil.invoke(
                act, "getCommands", new Class<?>[] {String.class, boolean.class}, new Object[] {packageName, checked});
        
        assertEquals("Failed, verify the list size for commands.", 2, cmds.size());
        
        assertEquals("Failed, verify the command0 when do check on switch button for two disabled components.", 
                expectedCmd0, cmds.get(0));
        assertEquals("Failed, verify the command1 when do check on switch button for two disabled components.", 
                expectedCmd1, cmds.get(1));
    }
    
    @Smoke
    public void testGetCommandsDoCheckOnSwitchButtonForOneDefaultOneDisabledComponents() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final String packageName = "com.example.testisenableapp";
        final boolean checked = true;
        final String expectedCmd1 = "pm enable com.example.testisenableapp/com.example.testreceiver02";
        
        // build map
        AppDetail ad1 = new AppDetail();
        ad1.packageName = packageName;
        ad1.receiverName = "com.example.testreceiver01";
        ad1.bootState = PackageManager.COMPONENT_ENABLED_STATE_DEFAULT;
        
        AppDetail ad2 = new AppDetail();
        ad2.packageName = packageName;
        ad2.receiverName = "com.example.testreceiver02";
        ad2.bootState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        
        List<AppDetail> listad = new ArrayList<AppDetail>(3);
        listad.add(ad1);
        listad.add(ad2);
        
        Map<String, List<AppDetail>> mapad = new HashMap<String, List<AppDetail>>(3);
        mapad.put(packageName, listad);
        TestUtils.PrivateUtil.setFieldValue(act, "mAppReceiverMap", mapad);
        
        // assert
        @SuppressWarnings("unchecked")
        List<String> cmds = (List<String>) TestUtils.PrivateUtil.invoke(
                act, "getCommands", new Class<?>[] {String.class, boolean.class}, new Object[] {packageName, checked});
        
        assertEquals("Failed, verify the list size for commands.", 1, cmds.size());
        
        assertEquals("Failed, verify the command1 when do check on switch button for one default and one disabled components.", 
                expectedCmd1, cmds.get(0));
    }
    
    // --------------- getDisableAllCommands() -----------------------
    @Smoke
    public void testGetDisableAllCommandsWithAllEnabledComponents() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final String packageName1 = "com.example.testgetdisableallCommands1";
        final String packageName2 = "com.example.testgetdisableallCommands2";
        
        final String[] actualCmds = {
                "pm disable com.example.testgetdisableallCommands1/com.example.testreceiver01",
                "pm disable com.example.testgetdisableallCommands1/com.example.testreceiver02",
                "pm disable com.example.testgetdisableallCommands2/com.example.testreceiver03",
                "pm disable com.example.testgetdisableallCommands2/com.example.testreceiver04"
        };
        
        // build list
        List<String> listpk = new ArrayList<String>(3);
        listpk.add(packageName1);
        listpk.add(packageName2);
        
        // build map item1
        AppDetail ad1 = new AppDetail();
        ad1.packageName = packageName1;
        ad1.receiverName = "com.example.testreceiver01";
        ad1.bootState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        
        AppDetail ad2 = new AppDetail();
        ad2.packageName = packageName1;
        ad2.receiverName = "com.example.testreceiver02";
        ad2.bootState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        
        List<AppDetail> listad1 = new ArrayList<AppDetail>(3);
        listad1.add(ad1);
        listad1.add(ad2);
        
        // build map item2
        AppDetail ad3 = new AppDetail();
        ad3.packageName = packageName2;
        ad3.receiverName = "com.example.testreceiver03";
        ad3.bootState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        
        AppDetail ad4 = new AppDetail();
        ad4.packageName = packageName2;
        ad4.receiverName = "com.example.testreceiver04";
        ad4.bootState = PackageManager.COMPONENT_ENABLED_STATE_DEFAULT;  // one default
        
        List<AppDetail> listad2 = new ArrayList<AppDetail>(3);
        listad2.add(ad3);
        listad2.add(ad4);
        
        // build map
        Map<String, List<AppDetail>> mapad = new HashMap<String, List<AppDetail>>(3);
        mapad.put(packageName1, listad1);
        mapad.put(packageName2, listad2);
        TestUtils.PrivateUtil.setFieldValue(act, "mAppReceiverMap", mapad);
        
        // assert
        @SuppressWarnings("unchecked")
        List<String> cmds = (List<String>) TestUtils.PrivateUtil.invoke(
                act, "getDisableAllCommands", new Class<?>[] {List.class}, new Object[] {listpk});
        
        assertEquals("Failed, verify the size of all disabled commands for all enabled components.", 4, cmds.size());
        
        for (int i = 0; i < cmds.size(); i++) {
            assertEquals(String.format("Failed, verify disable all commands for all enabled components. At -> %d", i), 
                    actualCmds[i], cmds.get(i));
        }
    }
    
    @Smoke
    public void testGetDisableAllCommandsWithAllDisabledComponents() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final String packageName1 = "com.example.testgetdisableallCommands1";
        final String packageName2 = "com.example.testgetdisableallCommands2";
        
        // build list
        List<String> listpk = new ArrayList<String>(3);
        listpk.add(packageName1);
        listpk.add(packageName2);
        
        // build map item1
        AppDetail ad1 = new AppDetail();
        ad1.packageName = packageName1;
        ad1.receiverName = "com.example.testreceiver01";
        ad1.bootState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        
        AppDetail ad2 = new AppDetail();
        ad2.packageName = packageName1;
        ad2.receiverName = "com.example.testreceiver02";
        ad2.bootState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        
        List<AppDetail> listad1 = new ArrayList<AppDetail>(3);
        listad1.add(ad1);
        listad1.add(ad2);
        
        // build map item2
        AppDetail ad3 = new AppDetail();
        ad3.packageName = packageName2;
        ad3.receiverName = "com.example.testreceiver03";
        ad3.bootState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        
        AppDetail ad4 = new AppDetail();
        ad4.packageName = packageName2;
        ad4.receiverName = "com.example.testreceiver04";
        ad4.bootState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        
        List<AppDetail> listad2 = new ArrayList<AppDetail>(3);
        listad2.add(ad3);
        listad2.add(ad4);
        
        // build map
        Map<String, List<AppDetail>> mapad = new HashMap<String, List<AppDetail>>(3);
        mapad.put(packageName1, listad1);
        mapad.put(packageName2, listad2);
        TestUtils.PrivateUtil.setFieldValue(act, "mAppReceiverMap", mapad);
        
        // assert
        @SuppressWarnings("unchecked")
        List<String> cmds = (List<String>) TestUtils.PrivateUtil.invoke(
                act, "getDisableAllCommands", new Class<?>[] {List.class}, new Object[] {listpk});
        
        assertEquals("Failed, verify the size of all disabled commands for all disabled components.", 0, cmds.size());
    }
    
    @Smoke
    public void testGetDisableAllCommandsWithSomeEnabledSomeDisabledComponents() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final String packageName1 = "com.example.testgetdisableallCommands1";
        final String packageName2 = "com.example.testgetdisableallCommands2";
        
        final String[] actualCmds = {
                "pm disable com.example.testgetdisableallCommands1/com.example.testreceiver01",
                "pm disable com.example.testgetdisableallCommands2/com.example.testreceiver04"
        };
        
        // build list
        List<String> listpk = new ArrayList<String>(3);
        listpk.add(packageName1);
        listpk.add(packageName2);
        
        // build map item1
        AppDetail ad1 = new AppDetail();
        ad1.packageName = packageName1;
        ad1.receiverName = "com.example.testreceiver01";
        ad1.bootState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        
        AppDetail ad2 = new AppDetail();
        ad2.packageName = packageName1;
        ad2.receiverName = "com.example.testreceiver02";
        ad2.bootState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        
        List<AppDetail> listad1 = new ArrayList<AppDetail>(3);
        listad1.add(ad1);
        listad1.add(ad2);
        
        // build map item2
        AppDetail ad3 = new AppDetail();
        ad3.packageName = packageName2;
        ad3.receiverName = "com.example.testreceiver03";
        ad3.bootState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        
        AppDetail ad4 = new AppDetail();
        ad4.packageName = packageName2;
        ad4.receiverName = "com.example.testreceiver04";
        ad4.bootState = PackageManager.COMPONENT_ENABLED_STATE_DEFAULT;
        
        List<AppDetail> listad2 = new ArrayList<AppDetail>(3);
        listad2.add(ad3);
        listad2.add(ad4);
        
        // build map
        Map<String, List<AppDetail>> mapad = new HashMap<String, List<AppDetail>>(3);
        mapad.put(packageName1, listad1);
        mapad.put(packageName2, listad2);
        TestUtils.PrivateUtil.setFieldValue(act, "mAppReceiverMap", mapad);
        
        // assert
        @SuppressWarnings("unchecked")
        List<String> cmds = (List<String>) TestUtils.PrivateUtil.invoke(
                act, "getDisableAllCommands", new Class<?>[] {List.class}, new Object[] {listpk});
        
        assertEquals("Failed, verify the size of all disabled commands for some enabled and disabled components.", 
                2, cmds.size());
        
        for (int i = 0; i < cmds.size(); i++) {
            assertEquals(String.format("Failed, verify disable all commands for some enabled and disabled components. At -> %d", i), 
                    actualCmds[i], cmds.get(i));
        }
    }
    
    // --------------- end -----------------------
    
}
