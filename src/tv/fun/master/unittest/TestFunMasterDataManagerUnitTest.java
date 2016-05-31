package tv.fun.master.unittest;

import static tv.fun.master.testutils.TestGlobalVars.LOG_TAG;
import static tv.fun.master.testutils.TestGlobalVars.TITLE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import tv.fun.master.MasterApplication;
import tv.fun.master.MasterDataManager;
import tv.fun.master.MasterDataManager.GetJsonDataFromServerAsyncTask;
import tv.fun.master.clean.AppClearInfo;
import tv.fun.master.testutils.TestUtils;
import tv.fun.master.utils.HttpUtil;
import tv.fun.master.utils.StringUtils;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.Smoke;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;
import junit.framework.Assert;

public class TestFunMasterDataManagerUnitTest extends AndroidTestCase {
    
    public TestFunMasterDataManagerUnitTest(String name) {
        super.setName(name);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Override
    @Smoke
    public void testAndroidTestCaseSetupProperly() {
        Log.d(LOG_TAG, String.format("%s ----- running class TestFunMasterDataManagerUnitTest", TITLE));
    };
    
    // --------------- getJsonFromServer() -----------------------
    @Smoke
    public void testGetJsonFromServerForResidueConnectToServer() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final int GET_DATA_RESIDUE = 1;
        JSONObject jsonObj = null;
        
        GetJsonDataFromServerAsyncTask getJsonTask = MasterDataManager.INSTANCE.new GetJsonDataFromServerAsyncTask();
        jsonObj = (JSONObject) TestUtils.PrivateUtil.invoke(getJsonTask, "getJsonFromServer", 
                new Class<?>[] {int.class}, new Object[] {GET_DATA_RESIDUE});
        
        assertNotNull("Failed, connect to server and get json.", jsonObj);
    }
    
    @Smoke
    public void testGetJsonFromServerForResidueWithDefaultTimeStamp() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final String keyReturnCode = "retCode";
        final int codeOK = 200;
        final String keyReturnMsg = "retMsg";
        final String returnMsgContent = "Success.";
        final String keyTimeStamp = "timestamp"; 

        final String serverUrl = "http://ott-api.fun.tv/master-service/api/v1/residue?timestamp=";
        final String defaultTimeStamp = "0";
        
        final String keyResult = "result";
        final String keyData = "data";
        
        // STEP1, verify JSON data from server 
        JSONObject jsonObj = null;
        try {
            String resultStr = new String(HttpUtil.requestGet(serverUrl + defaultTimeStamp));
            jsonObj = JSONObject.parseObject(resultStr);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception when get json data from server.");
        }
        
        assertTrue("Failed, json return code is ok for default timestamp.", 
                (jsonObj.getInteger(keyReturnCode) == codeOK));
        assertEquals("Failed, verify json returned message for default timestamp.", 
                returnMsgContent, jsonObj.getString(keyReturnMsg));
        
        JSONObject jsonObjResult = jsonObj.getJSONObject(keyResult);
        final String timeStamp = jsonObjResult.getString(keyTimeStamp);
        assertFalse("Failed, verify json returned timestamp for default timestamp.", StringUtils.isEmpty(timeStamp));
        Log.d(LOG_TAG, String.format("%s server timestamp -> %s", TITLE, timeStamp));
        MasterApplication.getInstance().setUpdateResidueTimestamp(timeStamp);  // update local timestamp as server
        
        final String rawdata = jsonObjResult.getString(keyData);
        assertFalse("Failed, get data from json object.", StringUtils.isEmpty(rawdata));

        // STEP2, verify parse data from json object
        @SuppressWarnings("unchecked")
        List<AppClearInfo> appInfoList = (List<AppClearInfo>) 
                TestUtils.PrivateUtil.invoke(MasterDataManager.INSTANCE, "parseResidueData", 
                new Class<?>[] {String.class}, new Object[] {rawdata});
        
        assertTrue("Failed, verify size of the parsed data.", (appInfoList.size() == 169));
        
        final String packageName = "cn.vszone.tv.gamebox";
        AppClearInfo app = appInfoList.get(0);
        assertEquals("Failed, verify the package name of 1st record.", packageName, app.getPackageName());
        
        // STEP3, verify write file from data.data
        final String fileName = "master_residue";
        
//        File oldDataFile = MasterApplication.getInstance().getApplicationContext().getFileStreamPath(fileName);
        File oldDataFile = this.getContext().getApplicationContext().getFileStreamPath(fileName);
        if (oldDataFile.isFile() && oldDataFile.exists()) {
            oldDataFile.delete();
            TestUtils.systemWait(1);
        }
        
        TestUtils.PrivateUtil.invoke(MasterDataManager.INSTANCE, "writeToFile", 
                new Class<?>[] {String.class, String.class}, new Object[] {rawdata, fileName});
//        File newDataFile = MasterApplication.getInstance().getApplicationContext().getFileStreamPath(fileName);  // build file in data.data
        File newDataFile = this.getContext().getApplicationContext().getFileStreamPath(fileName);
        assertTrue("Failed, verify data file is created from data.data.", newDataFile.exists());
        assertTrue("Failed, verify data file length from data.data.", (newDataFile.length() > 0));
    }
    
    @Smoke
    public void testGetJsonFromServerForResidueWithSameTimeStamp() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final int GET_DATA_RESIDUE = 1;
        final String keyReturnMsg = "retMsg";
        final String returnMsgContent = "Lastest residue.";
        
        GetJsonDataFromServerAsyncTask getJsonTask = MasterDataManager.INSTANCE.new GetJsonDataFromServerAsyncTask();
        JSONObject jsonObj = (JSONObject) TestUtils.PrivateUtil.invoke(
                getJsonTask, "getJsonFromServer", new Class<?>[] {int.class}, new Object[] {GET_DATA_RESIDUE});
        
        assertEquals("Failed, verify return message from json object.", returnMsgContent, jsonObj.getString(keyReturnMsg));
    }
    
    @Suppress
    public void testGetJsonFromServerForWhiteList() {
        //TODO
    }
    
    // --------------- getResidueDataFromLocal() -----------------------
    @Smoke
    public void testGetResidueDataFromLocalFileInAssets() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final String fileName = "master_residue";
        final String[] firstRecord = {"cn.vszone.tv.gamebox", "KO GameBox", "KOSDCard,KOTemp", "vszone.cn/log"};
        final String[] at100thRecord = {"com.kokozu.android.tv", "", "koMovie", ""};
        final String[] lastRecord = {"com.tv.zhuangjibibei", "", "zjbb", ""};
        
        // open file
        InputStream inputs = null;
        try {
//            inputs = MasterApplication.getInstance().getApplicationContext().getAssets().open(fileName);
            inputs = this.getContext().getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Exception when open local file master_residue from assets.");
        }
        
        // read encrypted data
        String endata = (String) TestUtils.PrivateUtil.invoke(MasterDataManager.INSTANCE, "getStringFromInputStream", 
                new Class<?>[] {InputStream.class}, new Object[] {inputs});
        
        // parse data
        @SuppressWarnings("unchecked")
        List<AppClearInfo> rawdata = (List<AppClearInfo>) 
                TestUtils.PrivateUtil.invoke(MasterDataManager.INSTANCE, "parseResidueData", 
                new Class<?>[] {String.class}, new Object[] {endata});
        
        // assert
        assertTrue("Failed, verify size of the raw data.", (rawdata.size() == 169));
        
        // assert 1st record
        AppClearInfo app = rawdata.get(0);
        assertEquals("Failed, verify the package name of 1st record.", firstRecord[0], app.getPackageName());
        assertEquals("Failed, verify the app name of 1st record.", firstRecord[1], app.getAppName());
        String[] temp = firstRecord[2].split(",");
        for(int i = 0; i < temp.length; i++) {
            assertEquals("Failed, verify the uninstall folders of 1st record.", temp[i], app.getUninstallFolder()[i]);
        }
        assertEquals("Failed, verify the temp folders of 1st record.", firstRecord[3], app.getTempFolders()[0]);
        
        // assert record at position 100
        app = rawdata.get(99);
        assertEquals("Failed, verify the package name of 100th record.", at100thRecord[0], app.getPackageName());
        assertNull("Failed, verify the app name of 100th record.", app.getAppName());
        assertEquals("Failed, verify the uninstall folders of 100th record.", at100thRecord[2], app.getUninstallFolder()[0]);
        assertNull("Failed, verify the temp folders of 100th record.", app.getTempFolders());
        
        // assert last record
        app = rawdata.get(168);
        assertEquals("Failed, verify the package name of last record.", lastRecord[0], app.getPackageName());
        assertNull("Failed, verify the app name of last record.", app.getAppName());
        assertEquals("Failed, verify the uninstall folders of last record.", lastRecord[2], app.getUninstallFolder()[0]);
        assertNull("Failed, verify the temp folders of last record.", app.getTempFolders());
    }
    
    @Smoke
    public void testGetResidueDataFromLocalFileInDataData() {
        Log.d(LOG_TAG, String.format("%s ***** running activity unit test case -> %s", TITLE, this.getName()));
        
        final String fileName = "master_residue";
        final String[] firstRecord = {"cn.vszone.tv.gamebox", "KO GameBox", "KOSDCard,KOTemp", "vszone.cn/log"};
        final String[] at100thRecord = {"com.kokozu.android.tv", "", "koMovie", ""};
        final String[] lastRecord = {"com.tv.zhuangjibibei", "", "zjbb", ""};
        
        // assert file exist
//        File listFile = MasterApplication.getInstance().getApplicationContext().getFileStreamPath(fileName);
        File listFile = this.getContext().getApplicationContext().getFileStreamPath(fileName);
        assertTrue("Failed, verify list file exist from data.data.", listFile.exists());
        
        // open file
        InputStream listFileInputStream = null;
        try {
            listFileInputStream = new FileInputStream(listFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail("Exception when open local file master_residue from data.data.");
        }
        
        // read encrypted data
        String endata = (String) TestUtils.PrivateUtil.invoke(MasterDataManager.INSTANCE, "getStringFromInputStream", 
                new Class<?>[] {InputStream.class}, new Object[] {listFileInputStream});
        
        // parse data
        @SuppressWarnings("unchecked")
        List<AppClearInfo> rawdata = (List<AppClearInfo>) 
                TestUtils.PrivateUtil.invoke(MasterDataManager.INSTANCE, "parseResidueData", 
                new Class<?>[] {String.class}, new Object[] {endata});
        
        // assert
        assertTrue("Failed, verify size of the raw data.", (rawdata.size() == 169));
        
        // assert 1st record
        AppClearInfo app = rawdata.get(0);
        assertEquals("Failed, verify the package name of 1st record.", firstRecord[0], app.getPackageName());
        assertEquals("Failed, verify the app name of 1st record.", firstRecord[1], app.getAppName());
        String[] temp = firstRecord[2].split(",");
        for(int i = 0; i < temp.length; i++) {
            assertEquals("Failed, verify the uninstall folders of 1st record.", temp[i], app.getUninstallFolder()[i]);
        }
        assertEquals("Failed, verify the temp folders of 1st record.", firstRecord[3], app.getTempFolders()[0]);
        
        // assert record at position 100
        app = rawdata.get(99);
        assertEquals("Failed, verify the package name of 100th record.", at100thRecord[0], app.getPackageName());
        assertNull("Failed, verify the app name of 100th record.", app.getAppName());
        assertEquals("Failed, verify the uninstall folders of 100th record.", at100thRecord[2], app.getUninstallFolder()[0]);
        assertNull("Failed, verify the temp folders of 100th record.", app.getTempFolders());
        
        // assert last record
        app = rawdata.get(168);
        assertEquals("Failed, verify the package name of last record.", lastRecord[0], app.getPackageName());
        assertNull("Failed, verify the app name of last record.", app.getAppName());
        assertEquals("Failed, verify the uninstall folders of last record.", lastRecord[2], app.getUninstallFolder()[0]);
        assertNull("Failed, verify the temp folders of last record.", app.getTempFolders());
    }
    
    
    // --------------- end -----------------------
    
}
