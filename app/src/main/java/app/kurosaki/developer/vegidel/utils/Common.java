package app.kurosaki.developer.vegidel.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.interfaces.Constants;
import app.kurosaki.developer.vegidel.model.CartData;
import app.kurosaki.developer.vegidel.model.Variant;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Common implements Constants {

    public static Bitmap Photo;
    public static ArrayList<Variant> litrevariants=new ArrayList<>();
    public static ArrayList<Variant> weightvariants=new ArrayList<>();
    public static ArrayList<Variant> dozenvariants=new ArrayList<>();

    public static ArrayList<Variant> getLitrevariants() {
        return litrevariants;
    }

    public static void setLitrevariants() {
        litrevariants.clear();
        litrevariants.add(new Variant("1 litre","90"));
        litrevariants.add(new Variant("1/2 litre","45"));
    }

    public static ArrayList<Variant> getWeightvariants() {
        return weightvariants;
    }

    public static void setWeightvariants() {
        weightvariants.clear();
        weightvariants.add(new Variant("1 litre","90"));
        weightvariants.add(new Variant("1/2 litre","45"));
    }

    public static ArrayList<Variant> getDozenvariants() {
        return dozenvariants;
    }

    public static void setDozenvariants() {
        dozenvariants.clear();
        dozenvariants.add(new Variant("1 dozen","90"));
        dozenvariants.add(new Variant("1/2 dozen","45"));
    }

    public static boolean validateEditText(String text) {
        return !TextUtils.isEmpty(text) && text.trim().length() > 0;
    }

    public static Bitmap takeScreenShot(@NotNull View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static boolean isValidGender(@NotNull String text) {
        if (text.toLowerCase().equals("male") ||
                text.toLowerCase().equals("female") ||
                text.toLowerCase().equals("transmale") ||
                text.toLowerCase().equals("transfemale") ||
                text.toLowerCase().equals("queer") ||
                text.toLowerCase().equals("something else") ||
                text.toLowerCase().equals("decline to answer")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public static boolean isValidPassword1(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 8;
    }

    public static String deviceId(@NotNull Context ctx) {
        return Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static void setToolbarWithBackAndTitle(Context ctx, String title, Boolean value, int backResource) {
        Toolbar toolbar = ((AppCompatActivity) ctx).findViewById(R.id.toolbar);
        ((AppCompatActivity) ctx).setSupportActionBar(toolbar);
        TextView title_tv = toolbar.findViewById(R.id.title_tv);
        ActionBar actionBar = ((AppCompatActivity) ctx).getSupportActionBar();
        if (actionBar != null) {
            if (backResource != 0)
                toolbar.setNavigationIcon(backResource);
            actionBar.setDisplayShowHomeEnabled(value);
            actionBar.setDisplayShowTitleEnabled(false);
            title_tv.setText(title);
        }
    }

    @NotNull
    public static String getDate(String inputFormat, String outputFormat, String selectedDate) {
        SimpleDateFormat parseDateFormat = new SimpleDateFormat(inputFormat, Locale.getDefault());
        Date date = null;
        try {
            date = parseDateFormat.parse(selectedDate);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        SimpleDateFormat requiredDateFormat = new SimpleDateFormat(outputFormat, Locale.getDefault());
        return requiredDateFormat.format(date);
    }




    @NotNull
    public static RequestBody getRequestBodyOfString(String string) {
        return RequestBody.create(string, MediaType.parse("multipart/form-data"));
    }


    /**
     * convert to bitmap to file
     *
     * @param bitmap bitmap to convert
     * @return file image file
     */
    @NotNull
    public static File BitmapToFile(Bitmap bitmap, @NotNull Context context) {
        String timeStamp = new SimpleDateFormat(DATE).format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File filesDir = context.getFilesDir();
        File imageFile = new File(filesDir, imageFileName);
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e("common", "Error writing bitmap", e);
        }

        return imageFile;
    }

    /**
     * method to set swipe refresh layout to activity or fragment
     *
     * @param swipeRefreshLayout swipe refresh object
     * @param context            context
     */
    public static void setSwipeRefreshLayout(@NotNull SwipeRefreshLayout swipeRefreshLayout, Context context) {
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context, R.color.White));
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(context, R.color.colorPrimary));
    }

    @NotNull
    private static String getFileSizeMegaBytes(@NotNull File file) {
        return (double) file.length() / (1024 * 1024) + " mb";
    }


    public static Bitmap ExifBitmap(Bitmap bitmap, String photoPath) throws IOException {
        ExifInterface ei = new ExifInterface(photoPath);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch (orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }
        return rotatedBitmap;
    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @NotNull
    public static String getCountryCode(@NotNull Context mContext) {
        return mContext.getResources().getConfiguration().locale.getCountry();
    }

    @NotNull
    public static SpannableStringBuilder underline(int color, String string, int start, int end) {

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        /*str.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new ForegroundColorSpan(Color.WHITE), 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new ForegroundColorSpan(Color.WHITE), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new ForegroundColorSpan(Color.WHITE), end, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/
        SpannableString ss = new SpannableString(string);
        UnderlineSpan underlineSpan = new UnderlineSpan() {
            @Override
            public void updateDrawState(@NotNull TextPaint ds) {
                ds.setColor(color);
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        ss.setSpan(underlineSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.append(ss);
        return stringBuilder;
    }

    @NotNull
    public static SpannableStringBuilder foregroundcolor(int color, String string, int start, int end) {

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        SpannableString ss = new SpannableString(string);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        ss.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.append(ss);
        return stringBuilder;
    }

    @NotNull
    public static SpannableStringBuilder clickabletext(Context context, Class object, String string, int start, int end) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        SpannableString ss = new SpannableString(string);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                context.startActivity(new Intent(context, object));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.append(ss);
        return stringBuilder;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @NotNull
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        assert type != null;
        Log.e("Type", "" + type.substring(0, type.lastIndexOf("/")));
        return type.substring(0, type.lastIndexOf("/"));
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap createDrawableFromView(Context context, @NotNull View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        // view.draw(canvas);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = view.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        view.draw(canvas);
        return bitmap;
    }

    public static ArrayList<CartData> getCart(@NotNull SharedPref sp) {
        String userString = sp.getString(CART);
        if (validateEditText(userString)) {
            Gson gson = new Gson();
            Type baseType = new TypeToken<ArrayList<CartData>>() {
            }.getType();

            return gson.fromJson(userString, baseType);
        } else
            return new ArrayList<>();
    }
}
