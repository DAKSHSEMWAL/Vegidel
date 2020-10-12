package app.kurosaki.developer.vegidel.core;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.databinding.ActivityCropImageBinding;
import app.kurosaki.developer.vegidel.utils.Common;

public class CropImage extends BaseActivity implements CropImageView.OnSetImageUriCompleteListener,
        CropImageView.OnCropImageCompleteListener {

    private CropImageView mCropImageView;
    private ProgressDialog dialog;
    ActivityCropImageBinding cropImageBinding;

    private Uri imageUri;
    private Bitmap cropped;

    int cropType = 0, width = 0, height = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cropImageBinding= DataBindingUtil.setContentView(this, R.layout.activity_crop_image);

        getData();
        setToolbar();
        initialize();
        setCropImageView();
    }

    /* get data from intent bundle */
    void getData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cropType = extras.getInt("cropType");
            imageUri = Uri.parse(extras.getString("imageUri"));
        }
    }

    /* initialize and set toolbar */
    void setToolbar() {
        Common.setToolbarWithBackAndTitle(mContext, getString(R.string.crop_image), true, R.drawable.ic_back);
        cropImageBinding.mToolbar.userprofile.setVisibility(View.GONE);
    }

    /* initialize all views and objects */
    void initialize() {
        dialog = new ProgressDialog(CropImage.this);
        dialog.setCancelable(false);
        dialog.setMessage(getString(R.string.cropping_image));
        mCropImageView = findViewById(R.id.CropImageView);

    }

    /* method to set aspect ratio and sizes */
    void setCropImageView() {
        mCropImageView.setOnSetImageUriCompleteListener(this);
        mCropImageView.setOnCropImageCompleteListener(this);
        mCropImageView.setImageUriAsync(imageUri);
        mCropImageView.setShowProgressBar(false);
        mCropImageView.setFixedAspectRatio(true);

        if (cropType == RECTANGLE_10_BY_6) {
            mCropImageView.setAspectRatio(10, 6);
            width = 1000;
            height = 600;
        } else if (cropType == SQUARE) {
            mCropImageView.setAspectRatio(10, 10);
            width = 1000;
            height = 1000;
        }
        else if(cropType == DO_NOT_CROP_PHOTO)
        {
            mCropImageView.setFixedAspectRatio(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);

        MenuItem mi_tick = menu.findItem(R.id.tick);
        mi_tick.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.tick) {
            mCropImageView.getCroppedImageAsync(width, height);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
    }

    /* async task to crop bitmap and return data */
    class CroppingImageAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Common.Photo = cropped;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setResult(RESULT_OK, new Intent());
            dialog.dismiss();
            finish();
        }
    }

    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
        handleCropResult(result);
    }

    private void handleCropResult(@NotNull CropImageView.CropResult result) {

        if (result.getError() == null) {
            Bitmap bitmap = result.getBitmap();
            if (bitmap != null) {
                cropped = bitmap;
                new CroppingImageAsync().execute();
            }
        } else {
            Log.e("AIC", "Failed to crop image", result.getError());
        }
    }
}