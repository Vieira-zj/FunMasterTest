package tv.fun.master.testutils;

import static tv.fun.master.testutils.TestGlobalVars.LOG_TAG;
import static tv.fun.master.testutils.TestGlobalVars.TITLE;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

public class TestUtils {
    public final static String NEWLINE = "\r\n";
    private final static String PROCESSNAME = "tv.fun.master";
    
    public static boolean isSDCardAvaliable() {
        Log.d(LOG_TAG, String.format("%s verify sdcard available.", TITLE));
        
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        
    }
    
    public static void systemWait(int sec) {
        SystemClock.sleep(sec * 1000);
    }

    public static void execShellCmd(final String command) {
        Log.d(LOG_TAG, String.format("%s exec shell command -> %s", TITLE, command));
        
        try {
            Runtime.getRuntime().exec(command);
//          process.waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void execShellAdminCmd(final String command) {
        Log.d(LOG_TAG, String.format("%s exec shell admin command -> %s" , TITLE, command));
        
        try {
            Process process = Runtime.getRuntime().exec("su");
            PrintWriter pw = new PrintWriter(process.getOutputStream());
            pw.println(command);
            pw.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void startLogcat(final String path) {
        Log.d(LOG_TAG, String.format("%s start logcat -> %s", TITLE, path));

        final String cmd = "logcat -c && logcat -v time > " + path;
        execShellAdminCmd(cmd);
    }
    
    public static void stopLogcat(final String path) {
        Log.d(LOG_TAG, String.format("%s stop logcat -> %s", TITLE, path));
        
        // no command for stop logcat to log file, use copy and remove instead 
        String cmd = "cp " + path + " " + path + ".log";
        execShellAdminCmd(cmd);
        
        try {
            Thread.sleep(2 * 1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        cmd = "rm " + path;
        execShellAdminCmd(cmd);
    }
    
    public static String getSavedFilePath(Activity act) {
        Log.d(LOG_TAG, String.format("%s get saved path.", TITLE));
        
        try {
            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                return android.os.Environment.getExternalStorageDirectory().toString();
            } else {
                return act.getFilesDir().getPath();
            }
        } catch (NullPointerException ex) {
            Log.d(LOG_TAG, TITLE + ex.getMessage());
            return "null";
        }
    }
    
    public static int getProcessId(Context context) {
        int pid = 0;
        
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> run = am.getRunningAppProcesses();
//        PackageManager pm = context.getPackageManager();

        for (RunningAppProcessInfo runningProcess : run) {
            if ((runningProcess.processName != null) && runningProcess.processName.equals(PROCESSNAME)) {
                pid = runningProcess.pid;
                break;
            }
        }
        Log.d(LOG_TAG, String.format("%s get process id -> %s", TITLE, String.valueOf(pid)));

        return pid;
    }
    
    public static String getDeviceType() {
        return android.os.Build.MODEL;
    }
    
    public static String getSDKVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
    
    public static String getDateTime() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int mins = c.get(Calendar.MINUTE);
        
        return String.format("%d%d%d%d%d", year, month, day, hour, mins);
    }

    public static class PrivateUtil {
        
        public static Object getFieldValue(final Object obj, final String fieldName) {
            Field field = null;
            
            try {
                field = obj.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(String.format("No such method: %s.%s", obj.getClass(), fieldName));
            } catch (Exception e) {
                throw new RuntimeException(String.format("Exception when get private field: %s", field.getName()));
            }
        }
        
        public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
            Field field = null;
            
            try {
                field = obj.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(obj, value);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(String.format("No such method: %s.%s", obj.getClass(), fieldName));
            } catch (Exception e) {
                throw new RuntimeException(String.format("Exception when set value for private field: %s", field.getName()));
            }
        }
        
        public static Method getMethod(final Class<?> clazz, final String methodName, final Class<?>[] classes) 
                throws NoSuchMethodException {
            Method method = null;
//            Method[] methods;  // for debug
            
            try {
//                methods = clazz.getDeclaredMethods();  // for debug
//                for (int i = 0; i < methods.length; i++) {
//                    if ("getCommands".equals(methods[i].getName())) {
//                        method = methods[i];
//                    }
//                }
                
                method = clazz.getDeclaredMethod(methodName, classes);
            } catch (NoSuchMethodException e) {
                throw e;
            }
            
            return method;
        }
        
        public static Object invoke(
                final Object obj, final String methodName, final Class<?>[] classes, final Object[] objects) { 
            
            try {
                Method method = getMethod(obj.getClass(), methodName, classes);
                method.setAccessible(true);
                return method.invoke(obj, objects);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(String.format("No such method: %s.%s", obj.getClass().toString(), methodName));
            }
            catch (Exception e) {
                throw new RuntimeException(String.format("Exception when invoke private method: %s", methodName));
            }
        }
        
        public static Object invoke(final Object obj, final String methodName, final Class<?>[] classes) { 
            return invoke(obj, methodName, classes, new Object[] {});
        }
        
        public static Object invoke(final Object obj, final String methodName) {
            return invoke(obj, methodName, new Class<?>[] {}, new Object[] {});
        }
    }
}
