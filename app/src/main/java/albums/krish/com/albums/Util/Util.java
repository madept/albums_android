package albums.krish.com.albums.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *  This class provides utility and common methods to be used accross application
 */

public class Util {

    private static ProgressDialog progressDialog;
    public static void showProgressDialog(Context context) {
        try {


            if (progressDialog == null) {
                progressDialog = new ProgressDialog(context,
                        ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
                progressDialog.setTitle("Loading...");
                progressDialog.setCancelable(false);
            }
            progressDialog.show();
        } catch (Exception ex) {
            System.out.println();

        }
    }

    public static void dismissProgressDialog() {
        try {
            if (progressDialog == null) {
                return;
            }
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception ex) {
            System.out.println();
        }
    }

    public static boolean isNetworkAvailable(Activity argActivity) {
        if (argActivity == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = null;
        try {
            activeNetworkInfo = ((ConnectivityManager) argActivity
                    .getSystemService(Context.CONNECTIVITY_SERVICE))
                    .getActiveNetworkInfo();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (activeNetworkInfo != null) {
            return true;
        }
        return false;
    }

    public static String getPersistanceData(Context ctx, String fileName){
        String data ="";
        try{
            File f = ctx.getFilesDir();
            File dataFile = new File(f.getAbsolutePath() + "/" + fileName);


            if (dataFile.exists()) {



                BufferedReader in = new BufferedReader(new FileReader(dataFile));
                StringBuffer buff = new StringBuffer();
                String dt = null;
                while ((dt=in.readLine()) != null){
                    buff.append(dt);
                }
                data = buff.toString();
                in.close();

            }
        }catch(Exception e){

        }
        return data;
    }

    public static void savePersistanceData(Context ctx, String fileName,String data){
        try{
            File f = ctx.getFilesDir();
            File dataFile = new File(f.getAbsolutePath() + "/" + fileName);
            if (dataFile.exists()) {
                dataFile.delete();
            }
            dataFile.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(dataFile));
            out.write(data);
            out.flush();
            out.close();

            // }
        }catch(Exception e){

        }
    }
}
