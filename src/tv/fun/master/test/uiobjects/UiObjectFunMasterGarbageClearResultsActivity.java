package tv.fun.master.test.uiobjects;

import com.robotium.solo.Solo;

import tv.fun.master.R;
import tv.fun.master.testutils.TestTasks;
import android.app.Activity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import static tv.fun.master.testutils.TestGlobalVars.*;

public class UiObjectFunMasterGarbageClearResultsActivity {

    public void waitForGarbageClearFinishButtonReady(Solo solo) {
        final String textFinishedButton = "完成";
        
        if (solo.searchButton(textFinishedButton)) {
            Log.d(LOG_TAG, String.format("%s garbage clear results activity ready.", TITLE));
        } else {
            Log.d(LOG_TAG, String.format("%s finish_button on garbage clear results is NOT found.", TITLE));
        }
    }
    
    public Button getGarbageClearFinishButton(Activity act) {
        return (Button) TestTasks.findUIElementById(act, R.id.button_finish);
    }

    public TextView getBestStateTextView(Activity act) {
        return (TextView) TestTasks.findUIElementById(act, R.id.best_state);
    }
    
    public TextView getClearMemoryTextView(Activity act) {
        return (TextView) TestTasks.findUIElementById(act, R.id.delete_memory);
    }
    
    public TextView getClearGarbageTextView(Activity act) {
        return (TextView) TestTasks.findUIElementById(act, R.id.delete_garbage);
    }
}
