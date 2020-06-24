package com.vaapglkns.todolist.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.DrawableCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaapglkns.todolist.R;
import com.vaapglkns.todolist.model.TodoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static void setPref(Context c, String pref, String val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putString(pref, val);
        e.commit();

    }

    public static String getPref(Context c, String pref, String val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString(pref,
                val);
    }

    public static void setPref(Context c, String pref, boolean val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putBoolean(pref, val);
        e.commit();

    }

    public static boolean getPref(Context c, String pref, boolean val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(
                pref, val);
    }

    public static void delPref(Context c, String pref) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.remove(pref);
        e.commit();
    }

    public static void setPref(Context c, String pref, int val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putInt(pref, val);
        e.commit();

    }

    public static int getPref(Context c, String pref, int val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getInt(pref,
                val);
    }

    public static void setPref(Context c, String pref, long val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putLong(pref, val);
        e.commit();
    }

    public static long getPref(Context c, String pref, long val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getLong(pref,
                val);
    }

    public static void setPref(Context c, String file, String pref, String val) {
        SharedPreferences settings = c.getSharedPreferences(file,
                Context.MODE_PRIVATE);
        Editor e = settings.edit();
        e.putString(pref, val);
        e.commit();

    }

    public static String getPref(Context c, String file, String pref, String val) {
        return c.getSharedPreferences(file, Context.MODE_PRIVATE).getString(
                pref, val);
    }

    public static boolean validateEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    // region FOR VALIDATE WEBSITE URL
    public static boolean isValidWebsiteURL(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return Patterns.WEB_URL.matcher(target).matches();
        }
    }
    //endregion

    public static boolean validate(String target, String pattern) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            Pattern r = Pattern.compile(pattern);
            return r.matcher(target).matches();
        }
    }

    public static boolean isAlphaNumeric(String target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            Pattern r = Pattern.compile("^[a-zA-Z0-9]*$");
            return r.matcher(target)
                    .matches();
        }
    }

    public static boolean isNumeric(String target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            Pattern r = Pattern.compile("^[0-9]*$");
            return r.matcher(target)
                    .matches();
        }
    }

    public static int getDeviceWidth(Context context) {
        try {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return metrics.widthPixels;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        return 480;
    }

    public static int getDeviceHeight(Context context) {
        try {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return metrics.heightPixels;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        return 800;
    }

//    public static boolean isInternetConnected(Context mContext) {
//        boolean outcome = false;
//
//        try {
//            if (mContext != null) {
//                ConnectivityManager cm = (ConnectivityManager) mContext
//                        .getSystemService(Context.CONNECTIVITY_SERVICE);
//
//                NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
//
//                for (NetworkInfo tempNetworkInfo : networkInfos) {
//                    if (tempNetworkInfo.isConnected()) {
//                        outcome = true;
//                        break;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return outcome;
//    }

    public static String getDeviceId(Context c) {
        String aid;
        try {
            aid = "";
            aid = Settings.Secure.getString(c.getContentResolver(),
                    "android_id");

            if (aid == null) {
                aid = "No DeviceId";
            } else if (aid.length() <= 0) {
                aid = "No DeviceId";
            }

        } catch (Exception e) {
            Utils.sendExceptionReport(e);
            aid = "No DeviceId";
        }

        return aid;

    }

    public static float random(float min, float max) {
        return (float) (min + (Math.random() * ((max - min) + 1)));
    }

    public static int random(int min, int max) {
        return Math.round((float) (min + (Math.random() * ((max - min) + 1))));
    }

    public static boolean hasFlashFeature(Context context) {
        return context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH);
    }

    public static boolean hasCameraFeature(Context context) {
        return context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }

    public static void hideKeyBoard(Context c, View v) {
        InputMethodManager imm = (InputMethodManager) c
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void showKeyBoard(Context c, View v) {
        v.requestFocus();
        v.setFocusableInTouchMode(true);

        InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }

    public static Typeface getBold(Context c) {
        try {
            return Typeface.createFromAsset(c.getAssets(),
                    "font/roboto_condensed_bold.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Typeface getNormal(Context c) {
        try {
            return Typeface.createFromAsset(c.getAssets(),
                    "font/roboto_condensed_regular.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean passwordValidator(String password) {
        Pattern pattern;
        Matcher matcher;
//        String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{6,15}$";


        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static String formatNo(String str) {
        String number = removeComma(nullSafe(str));
        if (!TextUtils.isEmpty(number)) {

            String finalStr = formatToComma(number);

//            if (!finalStr.startsWith("$"))
//                finalStr = "$" + finalStr;
            return finalStr;
        }

        return number;

    }

    public static String formatNo$(String str) {
        String number = removeComma(nullSafe(str));
        if (!TextUtils.isEmpty(number)) {

            String finalStr = formatToComma(number);

            if (!finalStr.startsWith("$"))
                finalStr = "$" + finalStr;
            return finalStr;
        }

        return number;

    }

    public static String formatToComma(String str) {
        String number = removeComma(nullSafe(str));
        if (!TextUtils.isEmpty(number)) {

            String finalStr;
            if (number.contains(".")) {
                number = truncateUptoTwoDecimal(number);
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                finalStr = decimalFormat.format(new BigDecimal(number));
            } else {
                finalStr = number;
            }

            finalStr = NumberFormat.getNumberInstance(Locale.US).format(Double.valueOf(finalStr));
            return finalStr;
        }

        return number;
    }

    public static String truncateUptoTwoDecimal(String doubleValue) {
        String value = String.valueOf(doubleValue);
        if (value != null) {
            String result = value;
            int decimalIndex = result.indexOf(".");
            if (decimalIndex != -1) {
                String decimalString = result.substring(decimalIndex + 1);
                if (decimalString.length() > 2) {
                    result = value.substring(0, decimalIndex + 3);
                } else if (decimalString.length() == 1) {
//                    result = String.format(Locale.ENGLISH, "%.2f",
//                            Double.parseDouble(value));
                }
            }
            return result;
        }

        return value;
    }

    public static String removeComma(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                str = str.replaceAll(",", "");
                try {
                    NumberFormat format = NumberFormat.getCurrencyInstance();
                    Number number = format.parse(str);
                    return number.toString();
                } catch (ParseException e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Debug.e("removeComma", "" + str);
        return str;

    }

    public static void sendExceptionReport(Exception e) {
        e.printStackTrace();

        try {
            // Writer result = new StringWriter();
            // PrintWriter printWriter = new PrintWriter(result);
            // e.printStackTrace(printWriter);
            // String stacktrace = result.toString();
            // new CustomExceptionHandler(c, URLs.URL_STACKTRACE)
            // .sendToServer(stacktrace);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }


    public static String getAndroidId(Context c) {
        String aid;
        try {
            aid = "";
            aid = Settings.Secure.getString(c.getContentResolver(),
                    "android_id");

            if (aid == null) {
                aid = "No DeviceId";
            } else if (aid.length() <= 0) {
                aid = "No DeviceId";
            }
        } catch (Exception e) {
            e.printStackTrace();
            aid = "No DeviceId";
        }

        Debug.e("", "aid : " + aid);

        return aid;

    }

    public static int getAppVersionCode(Context c) {
        try {
            return c.getPackageManager().getPackageInfo(c.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }
        return 0;

    }

    public static String getPhoneModel(Context c) {

        try {
            return Build.MODEL;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        return "";
    }

    public static String getPhoneBrand(Context c) {

        try {
            return Build.BRAND;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        return "";
    }

    public static String getOsVersion(Context c) {

        try {
            return Build.VERSION.RELEASE;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        return "";
    }

    public static int getOsAPILevel(Context c) {

        try {
            return Build.VERSION.SDK_INT;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        return 0;
    }

    public static String parseCalendarFormat(Calendar c, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                Locale.getDefault());
        return sdf.format(c.getTime());
    }

    public static String parseTime(long time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                Locale.getDefault());
        return sdf.format(new Date(time));
    }

    public static Date parseTime(String time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                Locale.getDefault());
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static String parseTime(String time, String fromPattern,
                                   String toPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromPattern,
                Locale.getDefault());
        try {
            Date d = sdf.parse(time);
            sdf = new SimpleDateFormat(toPattern, Locale.getDefault());
            return sdf.format(d);
        } catch (Exception e) {
            Log.i("parseTime", "" + e.getMessage());
        }

        return "";
    }

    public static Date parseTimeUTCtoDefault(String time, String pattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                Locale.getDefault());
        try {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date d = sdf.parse(time);
            sdf = new SimpleDateFormat(pattern, Locale.getDefault());
            sdf.setTimeZone(Calendar.getInstance().getTimeZone());
            return sdf.parse(sdf.format(d));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static Date parseTimeUTCtoDefault(long time) {

        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            cal.setTimeInMillis(time);
            Date d = cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.setTimeZone(Calendar.getInstance().getTimeZone());
            return sdf.parse(sdf.format(d));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static String parseTimeUTCtoDefault(String time, String fromPattern,
                                               String toPattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(fromPattern,
                Locale.getDefault());
        try {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date d = sdf.parse(time);
            sdf = new SimpleDateFormat(toPattern, Locale.getDefault());
            sdf.setTimeZone(Calendar.getInstance().getTimeZone());
            return sdf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static long daysBetween(Date startDate, Date endDate) {
        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(endDate);

        long daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }

    public static Calendar getDatePart(Date date) {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

        return cal;                                  // return the date part
    }

    public static String getUserToken(Context context) {
        return Utils.getPref(context, Constant.sUSER_TOKEN, "");
    }

    public static String nullSafe(String content) {
        if (content == null) {
            return "";
        }

        if (content.equalsIgnoreCase("null")) {
            return "";
        }

        return content;
    }

    public static String nullSafe(String content, String defaultStr) {
        if (content == null) {
            return defaultStr;
        }

        if (TextUtils.isEmpty(content)) {
            return defaultStr;
        }

        if (content.equalsIgnoreCase("null")) {
            return defaultStr;
        }

        return content;
    }

    public static String nullSafeDash(String content) {
        if (TextUtils.isEmpty(content)) {
            return "-";
        }

        if (content.equalsIgnoreCase("null")) {
            return "-";
        }

        return content;
    }

    public static String nullSafe(int content, String defaultStr) {
        if (content == 0) {
            return defaultStr;
        }

        return "" + content;
    }

    public static boolean isSDcardMounted() {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)
                && !state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            return true;
        }

        return false;
    }

    public static boolean isGPSProviderEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean isNetworkProviderEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        return locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static boolean isLocationProviderEnabled(Context context) {
        return (isGPSProviderEnabled(context) || isNetworkProviderEnabled(context));
    }

    public static boolean isLocationProviderRequired(Context context) {
        String lang = getPref(context, Constant.sUSER_LONGITUDE, "");
        String lat = getPref(context, Constant.sUSER_LATITUDE, "");

        if (lat.length() > 0 && lang.length() > 0) {
            return false;
        }
        return true;
    }

    public static boolean isUserLoggedIn(Context c) {
        return (getUid(c).length() > 0) ? true : false;
    }

    public static String getUid(Context c) {
        return Utils.getPref(c, Constant.sUSER_ID, "");
    }

    public static ArrayList<TodoItem> getTodoItems(Context c) {
        String response = Utils.getPref(c, Constant.sTO_DO_LIST_INFO, "");

        if (!TextUtils.isEmpty(response)) {
            try {
                return new Gson().fromJson(new JSONArray(response).toString(), new TypeToken<ArrayList<TodoItem>>() {
                }.getType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void clearLoginCredentials(Activity c) {
        Utils.delPref(c, Constant.sUSER_ID);
        Utils.delPref(c, Constant.sSESSION_ID);
        Utils.delPref(c, Constant.sTO_DO_LIST_INFO);
        Utils.delPref(c, Constant.sUSER_LATITUDE);
        Utils.delPref(c, Constant.sUSER_LONGITUDE);

        NotificationManager nMgr = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();
    }

    public static ArrayList<String> asList(String str) {
        ArrayList<String> items = new ArrayList<String>(Arrays.asList(str.split("\\s*,\\s*")));
        return items;
    }

    public static String implode(ArrayList<String> data) {
        try {
            String devices = "";
            for (String iterable_element : data) {
                devices = devices + "," + iterable_element;
            }

            if (devices.length() > 0 && devices.startsWith(",")) {
                devices = devices.substring(1);
            }

            return devices;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Create a File for saving an image or video
     *
     * @return Returns file reference
     */
    public static File getOutputMediaFile() {
        try {
            // To be safe, you should check that the SDCard is mounted
            // using Environment.getExternalStorageState() before doing this.
            File mediaStorageDir;
            if (isSDcardMounted()) {
                mediaStorageDir = new File(Constant.FOLDER_RIDEINN_PATH);
            } else {
                mediaStorageDir = new File(Environment.getRootDirectory(), Constant.FOLDER_NAME);
            }

            // This location works best if you want the created images to be
            // shared
            // between applications and persist after your app has been
            // uninstalled.

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }

            // Create a media file name
            File mediaFile = new File(mediaStorageDir.getPath() + File.separator + new Date().getTime() + ".jpg");
            mediaFile.createNewFile();

            return mediaFile;
        } catch (Exception e) {
            return null;
        }
    }

    public static void scanMediaForFile(Context context, String filePath) {
        resetExternalStorageMedia(context, filePath);
        notifyMediaScannerService(context, filePath);
    }

    public static boolean resetExternalStorageMedia(Context context,
                                                    String filePath) {
        try {
            if (Environment.isExternalStorageEmulated())
                return (false);
            Uri uri = Uri.parse("file://" + new File(filePath));
            Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, uri);

            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }

        return (true);
    }

    public static void notifyMediaScannerService(Context context,
                                                 String filePath) {

        MediaScannerConnection.scanFile(context, new String[]{filePath},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Debug.i("ExternalStorage", "Scanned " + path + ":");
                        Debug.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

    public static void cancellAllNotication(Context context) {

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static String toInitCap(String param) {
        try {
            if (param != null && param.length() > 0) {
                char[] charArray = param.toCharArray(); // convert into char
                // array
                charArray[0] = Character.toUpperCase(charArray[0]); // set
                // capital
                // letter to
                // first
                // position
                return new String(charArray); // return desired output
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return param;
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Debug.e("LOOK", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static String getExtenstion(String urlPath) {
        if (urlPath.contains(".")) {
            String extension = urlPath.substring(urlPath.lastIndexOf(".") + 1);
            return extension;
        }

        return "";
    }

    public static String getFileName(String urlPath) {
        if (urlPath.contains(".")) {
            return urlPath.substring(urlPath.lastIndexOf("/") + 1);
        }

        return "";
    }

    public static float dpToPx(Context context, int val) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, val, r.getDisplayMetrics());
    }

    public static float spToPx(Context context, int val) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, val, r.getDisplayMetrics());
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        Debug.e("type", "" + type);
        return type;
    }

    public static boolean isJPEGorPNG(String url) {
        try {
            String type = getMimeType(url);
            String ext = type.substring(type.lastIndexOf("/") + 1);
            if (ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("png")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

        return false;
    }

    public static double getFileSize(String url) {
        File file = new File(url);

        // Get length of file in bytes
        double fileSizeInBytes = file.length();
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        double fileSizeInKB = fileSizeInBytes / 1024;
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        double fileSizeInMB = fileSizeInKB / 1024;

        Debug.e("fileSizeInMB", "" + fileSizeInMB);
        return fileSizeInMB;
    }

    public static String getAsteriskName(String str) {
        int n = 4;

        str = Utils.nullSafe(str);
        StringBuilder fStr = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            if (str.length() > n) {
                fStr.append(str.substring(0, n));
                for (int i = 0; i < str.length() - n; i++) {
                    fStr.append("*");
                }

                return fStr.toString();
            } else {
                fStr.append(str.substring(0, str.length() - 1));
            }
        }
        return str;
    }

    //region FOR SET RATING BAR COLOR
    public static void setRatingBarColor(Context context, RatingBar ratingBar) {
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        // Filled stars
        setRatingStarColor(stars.getDrawable(2), ContextCompat.getColor(context, R.color.colorRating));
        // Half filled stars
        setRatingStarColor(stars.getDrawable(1), ContextCompat.getColor(context, R.color.colorRating));
        // Empty stars
        setRatingStarColor(stars.getDrawable(0), ContextCompat.getColor(context, R.color.color_gray));
    }
    //endregion

    //region FOR SET RATING STAR COLOR
    public static void setRatingStarColor(Drawable drawable, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DrawableCompat.setTint(drawable, color);
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }
    //endregion

    //region FOR GET KEY HASHES
    public static void getKeyHashes(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    //endregion

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarGradient(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.primary_gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    public String getYoutubeThumbnailUrlFromVideoUrl(String videoUrl) {
        return "http://img.youtube.com/vi/" + getYoutubeVideoIdFromUrl(videoUrl) + "/0.jpg";
    }

    public String getYoutubeVideoIdFromUrl(String inUrl) {
        inUrl = inUrl.replace("&feature=youtu.be", "");
        if (inUrl.toLowerCase().contains("youtu.be")) {
            return inUrl.substring(inUrl.lastIndexOf("/") + 1);
        }
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(inUrl);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }

    public String extractYoutubeId(String url) throws MalformedURLException {
//        val videoId = Global.extractYoutubeId("http://www.youtube.com/watch?v=t7UxjpUaL3Y")
//        val youtubveThumbNailUrl = "http://img.youtube.com/vi/$videoId/0.jpg"

        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    //region FOR GET STATUS BAR HEIGHT
    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    //endregion

    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            int value = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                value += Utils.toDP(6, context);
            }

            return value;
        } else {
            return Utils.toDP(48, context);
        }
    }


    public static int toDP(int px, Context context) {
        try {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
        } catch (Exception e) {
            return px;
        }
    }

    public static int toPx(int dp, Context context) {
        Resources r = context.getResources();
        try {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        } catch (Exception e) {
            return dp;
        }
    }

//    public static int getNavBarHeight(Context context) {
//        int result = 0;
//        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            result = context.getResources().getDimensionPixelSize(resourceId);
//        } else if (hasNavBar(context)) {
//            Utils.toDP(48, context);
//        }
//
//        return result;
//    }


    //region FOR CHANGE BITMAP COLOR
    public Bitmap changeBitmapColor(Bitmap bitmap, int color) {
        Bitmap bmpRedScale = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bmpRedScale);
        Paint paint = new Paint();

        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0f, 0f, paint);

        return bmpRedScale;
    }
    //endregion

    //region FOR CHANGE DATE-FORMAT WITH SUFFIX
    public String changeDateFormatWithSuffix(String value, String yourFormat, String requiredFormat) {
        String result = "";
        try {
            SimpleDateFormat formatterOld = new SimpleDateFormat(yourFormat, Locale.getDefault());
            formatterOld.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date;
            date = formatterOld.parse(value);
            SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());
            String day = dayFormat.format(date);
            SimpleDateFormat formatterNew = new SimpleDateFormat("d'" + getDaySuffix(Integer.parseInt(day)) + "' MMMM',' yyyy", Locale.getDefault());
            if (date != null) {
                result = formatterNew.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return value;
        }
        return result;
    }
    //endregion

    //region FOR CHANGE DATE-FORMAT
    public static String changeDateFormat(String value, String yourFormat, String requiredFormat) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(yourFormat);
        SimpleDateFormat outputFormat = new SimpleDateFormat(requiredFormat);

        String resultString = "";
        try {
            Date date = inputFormat.parse(value);
            resultString = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultString;
    }
    //endregion

    //region FOR GET DAY SUFFIX
    String getDaySuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }
    //endregion

    //region FOR START COUNT DOWN TIMER FOR HOUR-MINUTE-SECOND
    public static void startCountDownTimerForHourMinuteSecond(final TextView tvCountDownTimer, Long millisInFuture, Long countDownInterval) {
        CountDownTimer countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished), TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                tvCountDownTimer.setText(hms);
            }

            @Override
            public void onFinish() {
                tvCountDownTimer.setText("TIME'S UP!!"); //On finish change timer text
            }
        }.start();
    }
    //endregion

    //region FOR START COUNT DOWN TIMER FOR DAY HOUR-MINUTE-SECOND
    public static void startCountDownTimerForDayHourMinuteSecond(final TextView tvCountDownTimer, Long millisInFuture, Long countDownInterval) {
        CountDownTimer countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                String dhms = String.format("%02d:%02d:%02d:%02d", TimeUnit.MILLISECONDS.toDays(millisUntilFinished), TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished)), TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                tvCountDownTimer.setText(dhms);
            }

            @Override
            public void onFinish() {
                tvCountDownTimer.setText("TIME'S UP!!"); //On finish change timer text
            }
        }.start();
    }
    //endregion

    //region FOR START COUNT DOWN TIMER FOR MINUTE SECOND
    public static void startCountDownTimerForMinuteSecond(final TextView tvCountDownTimer, Long millisInFuture, Long countDownInterval) {
        CountDownTimer countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                String ms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                tvCountDownTimer.setText(ms);
            }

            @Override
            public void onFinish() {
                tvCountDownTimer.setText("TIME'S UP!!"); //On finish change timer text
            }
        }.start();
    }
    //endregion

    //region FOR START COUNT DOWN TIMER FOR SECOND
    public static void startCountDownTimerForSecond(final TextView tvCountDownTimer, Long millisInFuture, Long countDownInterval) {
        CountDownTimer countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                String s = String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))) + " sec";
                tvCountDownTimer.setText(s);
            }

            @Override
            public void onFinish() {
                tvCountDownTimer.setText("TIME'S UP!!"); //ON FINISH CHANGE TIMER TEXT
            }
        }.start();
    }
    //endregion

    public static Long getDateTimeDifferenceInMilli(String strDateFormat, String strStartDate, String strEndDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strDateFormat);
        Date startDate = simpleDateFormat.parse(strStartDate);
        Date endDate = simpleDateFormat.parse(strEndDate);

        Long difference = endDate.getTime() - startDate.getTime();
        if (difference < 0) {
            difference = Long.valueOf(0);
        }

        return difference;
    }

    public static String getCurrentDate(String requiredFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(requiredFormat);
        return sdf.format(new Date());
    }


    public static String getIncrementDayDate(String date, String dateFormat, int days) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(dateFormat);
        Date myDate = null;
        try {
            myDate = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        Date newDate = calendar.getTime();
        return new SimpleDateFormat("dd/MM/yyyy").format(newDate);
    }

    //region FOR GET CURRENT DATE-TIME IN TIME FORMAT
    public static String getCurrentDateTimeInTimeFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        return dateFormat.format(new Date());
    }
    //endregion


    //TRUE IF ON MOBILE DATA
    //FALSE OTHERWISE
//    public static boolean getConnectionStatus(Context context) {
//        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        try {
//            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//            if (null != activeNetwork) {
//                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
//                    return false;
//
//                if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
//                    return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    private static long getCurrentTime() {
        return new Date().getTime();
    }

    public static boolean isColorDark(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness >= 0.30;
    }

    public static boolean isAndroidN() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N || Build.VERSION.CODENAME.equals("N");
    }

    public static boolean isAndroidO() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O || Build.VERSION.CODENAME.equals("O");
    }

    public static boolean isAndroidP() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1 || Build.VERSION.CODENAME.equals("P");
    }

    //region FOR COLLAPSE VIEW
    public static void collapse(final View v, ImageView ivArrow) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
        Utils.rotateAnimationTo0(ivArrow, (int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
    }
    //endregion

    //region FOR EXPAND VIEW
    public static void expand(final View v, ImageView ivArrow, final boolean isReview) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
                if (isReview) {
//                    nestedScrollView.scrollTo(0, btnSendMessage.getBottom());
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
        Utils.rotateAnimationTo180(ivArrow, (int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
    }
    //endregion

    public static void rotateAnimationTo180(View view, int duration) {
        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(duration);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setFillEnabled(true);
        rotate.setFillAfter(true);
        view.startAnimation(rotate);
        view.requestLayout();
    }

    public static void rotateAnimationTo0(View view, int duration) {
        RotateAnimation rotate = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(duration);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setFillEnabled(true);
        rotate.setFillAfter(true);
        view.startAnimation(rotate);
        view.requestLayout();
    }

//    public static void shareApp(Activity activity) {
//        String shareText = "Hey guys, Try out this amazing " + activity.getResources().getString(R.string.app_name) + getAppShareUri().toString();
//        ShareCompat.IntentBuilder
//                .from(activity)
//                .setText(shareText)
//                .setType("text/plain")
//                .setChooserTitle("Share " + activity.getResources().getString(R.string.app_name))
//                .startChooser();
//    }

//    public static Uri getAppShareUri() {
//        return Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
//    }

    public static boolean deleteFile(Context context, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("file Deleted :" + filePath);

                updateGallery(context);
                return true;
            } else {
                System.out.println("file not Deleted :" + filePath);
            }
        }
        return false;
    }

    public static void updateGallery(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MediaScannerConnection.scanFile(context, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
                }
            });
        } else {
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }

    public static void addImageToGallery(String filePath, Context context) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    static void shareImage(Context context, String deleteFilePath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, context.getPackageName() + ".provider", new File(deleteFilePath)));
        context.startActivity(Intent.createChooser(intent, "Share with..."));
    }

    static void openGallery(Context context, String openFilePath) {
        File file = new File(openFilePath);
        Uri path = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(path, "image/*");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void visitFacebookPage(Context context, String fbUrl) {
//        val fbUrl = "https://www.facebook.com/Reconeco-Outdoors-2042158022499980/"
        try {
            context.startActivity(newFacebookIntent(context.getPackageManager(), fbUrl));
        } catch (Exception e) {
            openWeb(context, fbUrl);
        }
    }

    public static void visitInstagramPage(Context context, String instaUrl) {
//        val instaUrl = "http://instagram.com/_u/reconeco_outdoors"
        Uri uri = Uri.parse(instaUrl);
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
        likeIng.setPackage("com.instagram.android");
        try {
            context.startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(instaUrl)));
        }
    }

    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static void visitYoutubePage(Context context, String youtubeUrl) {
        openWeb(context, youtubeUrl);
    }

    public static void visitSpotifyPage(Context context, String spotify) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(spotify));
        intent.putExtra(Intent.EXTRA_REFERRER, Uri.parse("android-app://" + context.getPackageName()));
        context.startActivity(intent);
    }

    public static void visitTwitterPage(Context context, String twitterId) {
        Intent intent;
        try {
            //GET THE TWITTER APP IF POSSIBLE
            context.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=$twitterId"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (java.lang.Exception e) {
            //NO TWITTER APP, REVERT TO BROWSER
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/$twitterId"));
        }
        context.startActivity(intent);
    }

    public static void openWeb(Context context, String url) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    //FOR REMOVE DUPLICATE VALUE FROM ARRAY-LIST
//    val filteredList: ArrayList<OnlineTeacherCountry.Info> = ArrayList()
//    for (i in 0 until res.info.size) {
//        if (filteredList.size > 0) {
//            var isAlready = false
//            for (vaa in 0 until filteredList.size) {
//                if (res.info[i].country.equals(filteredList[vaa].country, ignoreCase = true)){
//                    isAlready = true
//                }
//            }
//            if (!isAlready) {
//                filteredList.add(res.info[i])
//            }
//        } else {
//            filteredList.add(res.info[i])
//        }

    public static void showDatePickerDialog(Context context, final TextView textView, final String dateFormat) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);
                textView.setText(parseTime(calendar.getTimeInMillis(), dateFormat));
            }
        }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    public static void showTimePickerDialog(Context context, final TextView editText, final String dateFormat) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                editText.setText(parseTime(calendar.getTimeInMillis(), dateFormat));
            }
        },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    public static void sendMessageToWhatsApp(Context context, String whatsAppNumber, String message) {
        PackageManager packageManager = context.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);

        try {
            String url = "https://api.whatsapp.com/send?phone=" + whatsAppNumber + "&text=" + URLEncoder.encode(message, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                context.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyTextToClipBoard(Context context, String label, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
    }

    //region FOR SET CLICKABLE STRING
    private static EventListener mEventListener;

    public interface EventListener {
        void onLinkClicked();
    }

    public static void setEventListener(EventListener eventListener) {
        mEventListener = eventListener;
    }

    public static void setClickableString(String clickableValue, String wholeValue, TextView yourTextView, boolean isBold, boolean isItalic, final boolean isUnderline) {
        //USAGE
        /**Utils.setClickableString("Cancel Subscription", "Cancel Subscription  Check Out The Modification Options Before Processing.", textView, false, false, true);
         Utils.setEventListener(new Utils.EventListener() {
        @Override public void onLinkClicked() {

        }
        });

         android:textColorHighlight="@color/gray_light"
         android:textColorLink="@color/colorRed"*/

        String value = wholeValue;
        SpannableString spannableString = new SpannableString(value);
        int startIndex = value.indexOf(clickableValue);
        int endIndex = startIndex + clickableValue.length();
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                if (isUnderline) {
                    ds.setUnderlineText(true); // <-- this will remove automatic underline in set span
                }
            }

            @Override
            public void onClick(View widget) {
                if (mEventListener != null) {
                    mEventListener.onLinkClicked();
                }
            }
        }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (isBold) {
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (isItalic) {
            spannableString.setSpan(new StyleSpan(Typeface.ITALIC), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        yourTextView.setText(spannableString);
        yourTextView.setMovementMethod(LinkMovementMethod.getInstance()); // <-- important, onClick in ClickableSpan won't work without this
    }
    //endregion

    //region FOR GET SPANNABLE STRING

    /**
     * SpannableStringBuilder builder = new SpannableStringBuilder();
     * builder.append(Utils.getSpannableString("Your Subscription is from ",getResources().getColor(R.color.gray_light)));
     * tvSubscriptionInterval.setText(builder, TextView.BufferType.SPANNABLE);
     */
    public static SpannableString getSpannableString(String value, int color) {
        SpannableString spannable = new SpannableString(value);
        spannable.setSpan(new ForegroundColorSpan(color), 0, value.length(), 0);
        return spannable;
    }
    //endregion

    // region FOR GET SPANNABLE STRING

    /**
     * SpannableStringBuilder builder = new SpannableStringBuilder();
     * builder.append(Utils.getSpannableString("Your Subscription is from ",getResources().getColor(R.color.gray_light), true, false, false));
     * tvSubscriptionInterval.setText(builder, TextView.BufferType.SPANNABLE);
     */
    public static SpannableString getSpannableString(String value, int color, boolean isBold, boolean isItalic, boolean isUnderline) {
        SpannableString spannable = new SpannableString(value);
        spannable.setSpan(new ForegroundColorSpan(color), 0, value.length(), 0);
        if (isBold) {
            spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (isItalic) {
            spannable.setSpan(new StyleSpan(Typeface.ITALIC), 0, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (isUnderline) {
            spannable.setSpan(new UnderlineSpan(), 0, value.length(), 0);
        }
        return spannable;
    }
    //endregion

    //region FOR CHANGE STATUS AND NAVIGATION BAR COLOR
    public static void changeStatusAndNavigationBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
            activity.getWindow().setNavigationBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        }
    }
    //endregion

//    public static RequestOptions getGlideRequestOptions(int placeholder) {
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(placeholder);
//        requestOptions.error(placeholder);
//        return requestOptions;
//    }
//
//    public static void loadGlideImage(Context context, String imageName, String imageURL, ImageView imageView){
//        if (imageName != null && !TextUtils.isEmpty(imageName)) {
//            Glide.with(context).load(imageURL)
//                    .apply(Utils.getGlideRequestOptions(R.drawable.ic_app_icon))
//                    .listener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
////                            holder.featuredImage.setVisibility(View.GONE);
//                            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_app_icon));
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
////                            holder.featuredImage.setVisibility(View.VISIBLE);
//                            return false;
//                        }
//                    })
//                    .into(imageView);
//        }else {
//            imageView.setImageResource(R.drawable.ic_app_icon);
//        }
//    }

    private void sendEmail(Context context, String subject, String message, String emailAddress) {
//        val intent = Intent(Intent.ACTION_SEND)
//        intent.putExtra(Intent.EXTRA_EMAIL, emailAddress)
//        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
//        intent.putExtra(Intent.EXTRA_TEXT, message)
//        intent.type = "message/rfc822"
//        context.startActivity(Intent.createChooser(intent, "Send Email using:"))

        Intent intent = new Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
        intent.setType("text/html");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, message);

        List<ResolveInfo> matches = context.getPackageManager().queryIntentActivities(intent, 0);
        ResolveInfo best = null;
        for (ResolveInfo info: matches) {
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail")) {
                best = info;
            }
        }
        if (best != null) {
            intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        }
        context.startActivity(intent);
    }

    public static void showError(View view, Boolean isError){
        if (isError){
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.GONE);
        }
    }

    public static void hideShowView(View view, Boolean isForShow){
        if (isForShow){
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.GONE);
        }
    }

    //region FOR MAKE CALL TO DRIVER
    //TODO Add in Manifest file:
    //<uses-permission android:name="android.permission.CALL_PHONE" />
//    public static void makeCall(Activity activity, String phoneNumber) {
//        String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
//        if (!hasPermissions(activity, permissions)) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                for (int a = 0; a < permissions.length; a++) {
//                    if (activity.checkSelfPermission(permissions[a]) != PackageManager.PERMISSION_GRANTED) {
//
//                    }
//                }
//                activity.requestPermissions(permissions, Constant.orprREQUEST_PERMISSION);
//            }
//        } else {
//            Intent intent = new Intent(Intent.ACTION_CALL);
//            intent.setData(Uri.parse("tel:" + phoneNumber));
//            activity.startActivity(intent);
//        }
//    }
    //endregion

    //region HAS PERMISSION
    public static boolean hasPermissions(Context context, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int a = 0; a < permissions.length; a++) {
                if (ActivityCompat.checkSelfPermission(context, permissions[a]) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    //region FOR GET URI FROM FILE
    public static Uri getUri(File file) {
        if (file != null) {
            return Uri.fromFile(file);
        } else {
            return  null;
        }
    }
    //endregion

    public static LayoutAnimationController getRowFadeSpeedAnimation(Context c) {
        AlphaAnimation anim = (AlphaAnimation) AnimationUtils.loadAnimation(c, R.anim.raw_fade);
        LayoutAnimationController controller = new LayoutAnimationController(anim, 0.3f);
        return controller;
    }
}