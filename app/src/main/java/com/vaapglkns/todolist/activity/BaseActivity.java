package com.vaapglkns.todolist.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.vaapglkns.todolist.R;
import com.vaapglkns.todolist.utils.ExitStrategy;

import java.io.File;


public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
    }

    //region FOR START NEW ACTIVITY
    public void startNewActivity(Intent i) {
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    //endregion

    //region FOR START NEW ACTIVITY
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void startNewActivity(Intent i, Bundle bundle) {
        i.putExtras(bundle);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    //endregion

    //region START NEW ACTIVITY FOR RESULT
    public void startNewActivityForResult(Intent i, int requestCode) {
        startActivityForResult(i, requestCode);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    //endregion

    //region FOR START NEW ACTIVITY
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void startNewActivity(Intent i, Bundle bundle, int requestCode) {
        i.putExtras(bundle);
        startActivityForResult(i, requestCode);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    //endregion

    private void finishActivity() {
        if ((getActivity() instanceof MainActivity)) {

        } else {
            getActivity().finish();
        }
    }

    public BaseActivity getActivity() {
        return this;
    }

    private TextView tvTitleText;
    private ImageView ivBack;

    public void setTitleText(String text) {
        try {
            if (tvTitleText == null)
                tvTitleText = (TextView) findViewById(R.id.tvTitleText);
            tvTitleText.setText(text);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //region FOR INIT BACK PRESS
    public void initBackPress(Boolean b) {
        if (ivBack == null)
            ivBack = findViewById(R.id.ivBack);
        if (b) {
            ivBack.setVisibility(View.VISIBLE);
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            ivBack.setVisibility(View.GONE);
        }
    }
    //endregion

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean handled = super.onKeyDown(keyCode, event);

        //EAT THE LONG PRESS EVENT SO THE KEYBOARD DOESN'T COME UP.
        if (keyCode == KeyEvent.KEYCODE_MENU && event.isLongPress()) {
            return true;
        }

        return handled;
    }

    Toast toast;

    public void showToast(final String text, final int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast.setText(text);
                toast.setDuration(duration);
                toast.show();
            }
        });
    }

    public void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast.setText(text);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (getActivity() instanceof MainActivity) {
            if (ExitStrategy.canExit()) {
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                super.onBackPressed();
            } else {
                ExitStrategy.startExitDelay(2000);
                showToast(getString(R.string.exit_msg));
            }
        } else {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            super.onBackPressed();
        }
    }

    public Fragment getViewPagerFragment(int viewPagerId, int position) {
        Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + viewPagerId + ":" + position);
        return fragment;
    }

    public Fragment getFragment(int frameLayoutId) {
        Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentById(frameLayoutId);
        return fragment;
    }

    //region FOR REPLACE FRAGMENT
    public void replaceFragment(int id, Fragment fragment, boolean isForBackStack) {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment);
        if (isForBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }
    //endregion

    //region FOR SHOW BIG IMAGE POPUP
    public void showBigImagePopup(int image, String imagePath, String imageURL, String title) {
        final LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.row_big_image, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
//        dialog.window.attributes.windowAnimations = R.style.FadIn_FadOutAnimation
        dialog.setView(alertLayout);

        ImageView ivRowZoomImage = alertLayout.findViewById(R.id.ivRowZoomImage);
        ImageView ivRowClose = alertLayout.findViewById(R.id.ivRowClose);
        TextView tvRowBigImageTitle = alertLayout.findViewById(R.id.tvRowBigImageTitle);

        if (!TextUtils.isEmpty(title)){
            tvRowBigImageTitle.setText(title);
        }

        if (image != -1) {
            ivRowZoomImage.setImageResource(image);
        }

        if (!TextUtils.isEmpty(imagePath)) {
            ivRowZoomImage.setImageURI(Uri.fromFile(new File(imagePath)));
        }

//        if (!TextUtils.isEmpty(imageURL)) {
//            Glide.with(this)
//                    .asBitmap()
//                    .load(imageURL)
//                    .into(new CustomTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                            ivRowZoomImage.setImageBitmap(resource);
//                        }
//
//                        @Override
//                        public void onLoadCleared(@Nullable Drawable placeholder) {
//                        }
//                    });
//        }

        ivRowClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    //endregion
}