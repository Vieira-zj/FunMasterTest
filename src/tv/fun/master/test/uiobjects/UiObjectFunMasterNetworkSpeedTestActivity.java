package tv.fun.master.test.uiobjects;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;
import tv.fun.master.R;
import tv.fun.master.testutils.TestTasks;

public class UiObjectFunMasterNetworkSpeedTestActivity {

    public TextView getCheckNetworkResultsTextView (Activity act) {
        return (TextView) TestTasks.findUIElementById(act, R.id.result);
    }
    
    public TextView getCheckNetworkTypeTextView (Activity act) {
        return (TextView) TestTasks.findUIElementById(act, R.id.net_type);
    }
    
    public TextView getCheckingNetworkTextView(Activity act) {
        return (TextView) TestTasks.findUIElementById(act, R.id.tv_netchecktest);
    }
    
    public TextView getSpeedUnitTextView(Activity act) {
        return (TextView) TestTasks.findUIElementById(act, R.id.speed_uint);
    }
    
    public Button getNetworkSpeedRetestButton(Activity act) {
        return (Button) TestTasks.findUIElementById(act, R.id.retry_button);
    }
    
    public Button getNetworkSpeedTestFinishButton(Activity act) {
        return (Button) TestTasks.findUIElementById(act, R.id.finish_button);
    }
    
    
}
