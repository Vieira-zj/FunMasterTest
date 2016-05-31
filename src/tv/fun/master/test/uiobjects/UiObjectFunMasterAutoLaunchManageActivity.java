package tv.fun.master.test.uiobjects;

import com.robotium.solo.Solo;

import tv.fun.master.R;
import tv.fun.master.testutils.TestTasks;
import tv.fun.master.view.SwitchButton;
import android.app.Activity;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import static tv.fun.master.testutils.TestGlobalVars.*;

public class UiObjectFunMasterAutoLaunchManageActivity {

    public TextView getAutoLaunchManageTitleTextView(Activity act) {
        return (TextView) TestTasks.findUIElementById(act, R.id.title);
    }
    
    public TextView getAutoLaunchManageWordHaveTextView(Activity act) {
        return (TextView) TestTasks.findUIElementById(act, R.id.have);
    }
    
    public TextView getAutoLaunchManageAppCountTextView(Activity act) {
        return (TextView) TestTasks.findUIElementById(act, R.id.app_count);
    } 
    
    public ListView getAutoLaunchManageListView(Activity act) {
        return (ListView) TestTasks.findUIElementById(act, R.id.listview);
    }
    
    public TextView getAutoLaunchManageAppNameFromListView(Activity act, int index) {
        LinearLayout layout = (LinearLayout) this.getAutoLaunchManageListView(act).getItemAtPosition(index);
        return (TextView) TestTasks.findChildElementById(layout, R.id.appName);
    }
    
    public SwitchButton getAutoLaunchManageSwitchButtonFromListView(Activity act, int index) {
        LinearLayout layout = (LinearLayout) this.getAutoLaunchManageListView(act).getItemAtPosition(index);
        return (SwitchButton) TestTasks.findChildElementById(layout, R.id.switch_button);
    }
    
    public int getAutoLaunchManageSelectedListItem(Activity act) {
        return this.getAutoLaunchManageListView(act).getSelectedItemPosition();
    }
    
    public int getCountOfAutoLaunchApps(Activity act) {
        return this.getAutoLaunchManageListView(act).getCount();
    }
    
    public Button getAutoLaunchManageDisableAllButton(Activity act) {
        return (Button) TestTasks.findUIElementById(act, R.id.disable_all_btn);
    }
    
    public void openFunMasterAutoLaunchManageActivity(Solo solo) {
        TestTasks.sendkeyAndWait(solo, Solo.DOWN);
        TestTasks.sendkeyAndWait(solo, Solo.RIGHT);
        TestTasks.sendkeyAndWait(solo, Solo.ENTER, 2);
        
        final String title = "自启动管理";
        if (solo.searchText(title)) {
            return;
        } else {
            Log.d(LOG_TAG, String.format("%s auto launch manage title is NOT found.", TITLE));
        }
    }
    
    
}
