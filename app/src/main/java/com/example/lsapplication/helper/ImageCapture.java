package com.example.lsapplication.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lsapplication.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import com.karumi.dexter.listener.PermissionRequest;


public class ImageCapture extends AppCompatActivity {
    private static final String TAG = ImageCapture.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;
    public static final String REQUIRED_WIDTH = "width";
    public static final String REQUIRED_HEIGHT = "height";
    int requiredWidth, requiredHeight;
    boolean setRequiredWidthHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Clearing older images from cache directory
        // don't call this line if you want to choose multiple images in the same activity
        // call this once the bitmap(s) usage is over
        ImagePickerActivity.clearCache(this);
        setRequiredWidthHeight = getIntent().hasExtra(REQUIRED_WIDTH) && getIntent().hasExtra(REQUIRED_HEIGHT);
        requiredWidth = getIntent().getIntExtra(REQUIRED_WIDTH, 1000);
        requiredHeight = getIntent().getIntExtra(REQUIRED_HEIGHT, 1000);

        start();
    }

    private void loadProfile(String url) {
//        Log.d(TAG, "Image cache path: " + url);
//
//        GlideApp.with(this).load(url)
//                .into(imgProfile);
//        imgProfile.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }

    void start() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(ImageCapture.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        addWidthHeightExtras(intent);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(ImageCapture.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        addWidthHeightExtras(intent);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void addWidthHeightExtras(Intent intent) {
        if (setRequiredWidthHeight) {
            // setting aspect ratio
            intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
            intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, requiredWidth); // 16x9, 1x1, 3:4, 3:2
            intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, requiredHeight);


            // setting maximum bitmap width and height
            intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        }
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, requiredWidth);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, requiredHeight);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    File result = new File(StorageUtils.getAppDir() + "/Doc" + new Random().nextInt(100) + ".jpg");
                    try {
                        StorageUtils.copyFile(new File(uri.getPath()), result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (result != null && result.exists()) {
                        setResult(RESULT_OK, new Intent().putExtra("url", uri.toString()).putExtra("file", result));
                    } else {
                        setResult(RESULT_CANCELED);
                    }
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == RESULT_CANCELED) {
                setResult(RESULT_CANCELED);
                finish();
            }
        }
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ImageCapture.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());


        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getApplication().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}

