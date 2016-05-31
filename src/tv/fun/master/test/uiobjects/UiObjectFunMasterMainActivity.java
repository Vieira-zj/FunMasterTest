package tv.fun.master.test.uiobjects;

import com.robotium.solo.Solo;

import android.app.Activity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tv.fun.master.R;
import tv.fun.master.testutils.TestGlobalVars;
import tv.fun.master.testutils.TestTasks;

public class UiObjectFunMasterMainActivity {

    public Button getOneKeyOptimizeButton(Activity act) {
//        Button btn = (Button) TestTasks.findUIElementById(act, R.id.bt_one_key_optimize);
//        
//        if (btn != null) {
//            return btn;
//        } else {
//            throw new RuntimeException("One key optimize button is not found.");
//        }
        
        return null;
    }
    
    public void ClickOneKeyOptimizeButton(Solo solo) {
        solo.clickOnButton("一键优化");
    }
    
    public TextView getTvSpeedTestTextView(Activity act) {
//        return (TextView) TestTasks.findUIElementById(act, R.id.tv_speed_test);
        return null;
    }
    
    public void ClickTvSpeedTestTextView(Solo solo) {
        solo.clickOnText("网络测速");
    }
    
    public TextView getTvSelfLaunchManageTextView(Activity act) {
//        return (TextView) TestTasks.findUIElementById(act, R.id.tv_self_launch_manage);
        return null;
    }
    
    public TextView getTvRestartTextView(Activity act) {
//        return (TextView) TestTasks.findUIElementById(act, R.id.tv_restart);
        return null;
    }
    
    public ImageView getIvDeviceInfoImageView(Activity act) {
        return (ImageView) TestTasks.findUIElementById(act, R.id.iv_device_info);
    }
    
    public void ClickDeviceInfoImageView(Solo solo, Activity act) {
        solo.clickOnView(this.getIvDeviceInfoImageView(act));
    }
    
    public void OpenDeviceInfoView(Solo solo) {
        TestTasks.sendkeyAndWait(solo, Solo.UP);
        TestTasks.sendkeyAndWait(solo, Solo.ENTER, 2);
    }
    
    public ImageView getIvSettingsImageView(Activity act) {
        return (ImageView) TestTasks.findUIElementById(act, R.id.iv_settings);
    }
    
    public TextView getGarbageClearResultsScoreTextView(Activity act) {
        return (TextView) TestTasks.findUIElementById(act, R.id.tv_result_score);
    }
    
    public void waitForGarbageClearResultsScoreReady(Solo solo, Activity act) {
        Log.d(TestGlobalVars.LOG_TAG, String.format("%s wait for garbage clear results score ready.", TestGlobalVars.TITLE));
        TextView score = this.getGarbageClearResultsScoreTextView(act);
        
        String temp = "";
        for (int i = 0; i < 3; i++) {
            if (temp.equals(score.getText().toString())) {
                break;
            } else {
                temp = score.getText().toString();
                TestTasks.wait(solo, 2);
            }
        }
    }
    
    public TextView getGarbageClearResultsContentTextView(Activity act) {
        return (TextView) TestTasks.findUIElementById(act, R.id.tv_result_text);
    }
}
