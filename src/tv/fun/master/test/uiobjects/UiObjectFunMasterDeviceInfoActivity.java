package tv.fun.master.test.uiobjects;

import tv.fun.master.testutils.TestTasks;
import tv.fun.master.R;
import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

public class UiObjectFunMasterDeviceInfoActivity {

    public boolean WaitForDeviceInfoActivityReady(Solo solo) {
        final String DeviceInfoTitle = "设备信息";
        
        return solo.searchText(DeviceInfoTitle);
    }
    
    public ListView getDeviceInfoListView(Activity act) {
        ListView list = (ListView) TestTasks.findUIElementById(act, android.R.id.list);
        
        if (list != null) {
            return list;
        } else {
            throw new RuntimeException("Device info list is not found.");
        }
    }
    
    // valid value for index, 0 - 6
    public TextView getDeviceInfoItemKey(ListView list, int index) {
        LinearLayout layout = (LinearLayout) list.getChildAt(index);
        TextView key = (TextView) TestTasks.findChildElementById(layout, R.id.info_key);
        
        if (key != null) {
            return key;
        } else {
            throw new RuntimeException(String.format("Device info item %d in list is not found.", index));
        }
    }
    
    public TextView getDeviceInfoItemValue(ListView list, int index) {
        LinearLayout layout = (LinearLayout) list.getChildAt(index);
        TextView key = (TextView) TestTasks.findChildElementById(layout, R.id.info_value);
        
        if (key != null) {
            return key;
        } else {
            throw new RuntimeException(String.format("Device info item %d in list is not found.", index));
        }
    }
    
    
}
