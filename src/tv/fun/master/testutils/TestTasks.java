package tv.fun.master.testutils;

import static tv.fun.master.testutils.TestGlobalVars.LOG_TAG;
import static tv.fun.master.testutils.TestGlobalVars.TITLE;

import tv.fun.master.BaseActivity;
import tv.fun.master.MainActivity;
import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

public class TestTasks {
    
// -----------------------------------------------
// methods related to wait
// -----------------------------------------------
    
    // sec for timeout
    public static void wait(Solo solo, long timeout) {
        Log.d(LOG_TAG, String.format("%s wait %d seconds.", TITLE, timeout));
        try {
            synchronized (solo) {
                solo.wait(timeout * 1000);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void waitFunMasterReady(Solo solo) {
        waitFunMasterReady(solo, 0);
    }
    
    public static void waitFunMasterReady(Solo solo, long timeout) {
        Log.d(LOG_TAG, String.format("%s wait fun master ready.", TITLE));

        if (timeout == 0) {
            timeout = 3;  // default time for wait fun master ready
        }
        waitForActivityReady(solo);
        wait(solo, timeout);
    }
    
    public static void sendkeyAndWait(Solo solo, int key) {
        sendkeyAndWait(solo, key, 0);
    }
    
    public static void sendkeyAndWait(Solo solo, int key, long delay) {
        if (delay == 0) {
            delay = 1;  // default delay 1s
        }
        Log.d(LOG_TAG, String.format("%s send key and wait.", TITLE, delay));
        
        try {
            solo.sendKey(key);
            wait(solo, delay);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void sendKeyBackAndWait(Solo solo) {
        sendKeyBackAndWait(solo, 0);
    }
    
    public static void sendKeyBackAndWait(Solo solo, long delay) {
        Log.d(LOG_TAG, String.format("%s sendkey back and wait.", TITLE));
        
        if (delay == 0) {
            delay = 1;
        }
        solo.goBack();
        wait(solo, delay);
    }
    
    public static void waitForActivityReady(Solo solo) {
        waitForActivityReady(solo, 0);
    }
    
    public static void waitForActivityReady(Solo solo, int timeout) {
        if (timeout == 0) {
            timeout = 5;  // default timeout
        }
        solo.waitForActivity(solo.getCurrentActivity().toString(), timeout);
    }
    
    public static void waitForViewDisappear(Solo solo, String searchText, int timeout) {
        final TextView textField = solo.getText(searchText, true);

        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return textField.getVisibility() == View.INVISIBLE;
            }
        }, timeout);
    }
    
// -----------------------------------------------
// methods related to find UI element
// -----------------------------------------------
    
    public static View findUIElementById(Activity act, int id) {
        return findUIElementById(act, id, 0);
    }
    
    public static View findUIElementById(Activity act, int id, int timeout) {
        View v;
        
        if (timeout == 0) {
            timeout = 3;
        }
        
        for (int i = 0; i <= timeout; i++) {
            v = act.findViewById(id);
            if (v != null) {
                return v;
            } else {
                try {
                    Thread.sleep(1000);  // Interval for find UI control
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
        throw new RuntimeException(String.format("The UI element is NOT found after %ds", timeout));
    }
    
    public static View findChildElementById(View view, int id) {
        return findChildElementById(view, id, 0);
    }
    
    public static View findChildElementById(View view, int id, int timeout) {
        View child;
        
        if (timeout == 0) {
            timeout = 3;
        }
        
        for (int i = 0; i <= timeout; i++) {
            child = view.findViewById(id);
            if (child != null) {
                return child;
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
        throw new RuntimeException(String.format("The UI element is NOT found after %ds", timeout));
    }
    
// -----------------------------------------------
// methods related to do action on UI
// -----------------------------------------------
    
    public static void launchFunMaster(Solo solo, Instrumentation inst) {
        launchFunMaster(solo, inst, MainActivity.class);
    }
    
    public static void launchFunMaster(Solo solo, Instrumentation inst, Class<? extends Activity> clazz) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(inst.getTargetContext(), clazz.getName());
        inst.startActivitySync(intent);
        waitForActivityReady(solo);
    }
    
    public static void restartFunMaster(Solo solo, Instrumentation inst) {
        restartFunMaster(solo, inst, MainActivity.class);
    }
    
    public static void restartFunMaster(Solo solo, Instrumentation inst, Class<? extends BaseActivity> clazz) {
        if(solo.waitForActivity(clazz)) {
            solo.finishOpenedActivities();
            wait(solo, 2);
        } 
        launchFunMaster(solo, inst);
    }
    
    public static void clearTextFromEditText(Solo solo, EditText editText) {
        solo.clearEditText(editText);
    }
    
    public static void appendTextFromEditText(Solo solo, EditText editText, String text) {
        solo.enterText(editText, editText.getText() + text);
    }

    public static void takeSnapshot(Solo solo, String name) {
        if (TestUtils.isSDCardAvaliable()) {
            solo.takeScreenshot(name);
        } else {
            Log.e(LOG_TAG, String.format("%s sdcard is not available, and solo.takeScreenshot() NOT exec.", TITLE));
        }
    }
    
// -----------------------------------------------
// methods related to activity
// -----------------------------------------------
    public static Activity startAndGetActivity(Instrumentation inst, Class<? extends Activity> clazz) {
        Intent intent = new Intent(inst.getTargetContext(), clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
        ActivityMonitor monitor = inst.addMonitor(clazz.getName(), null, false);
        inst.getTargetContext().startActivity(intent);
        return inst.waitForMonitor(monitor);
    }
    
    
}
