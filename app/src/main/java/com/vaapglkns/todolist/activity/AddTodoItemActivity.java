package com.vaapglkns.todolist.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vaapglkns.todolist.R;
import com.vaapglkns.todolist.adapter.SpinnerAdapter;
import com.vaapglkns.todolist.model.Spinner;
import com.vaapglkns.todolist.model.TodoItem;
import com.vaapglkns.todolist.utils.Constant;
import com.vaapglkns.todolist.utils.Debug;
import com.vaapglkns.todolist.utils.FileUtils;
import com.vaapglkns.todolist.utils.UriHelper;
import com.vaapglkns.todolist.utils.Utils;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTodoItemActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ivTodoItem)
    ImageView ivTodoItem;

    @BindView(R.id.edTitle)
    EditText edTitle;
    @BindView(R.id.edDesc)
    EditText edDesc;

    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnDone)
    Button btnDone;

    //FOR UPLOAD IMAGE
    public static final int REQ_PICK_IMAGE = 4569;
    public static final int REQ_CAPTURE_IMAGE = 4470;
    public static final int REQUEST_PERMISSION = 45671;
    private File fileUri;
    private String selectedImagePath = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo_item);
        ButterKnife.bind(this);
        initBackPress(true);
        setWidgetOperations();
        setData();
    }

    //region FOR SET WIDGET OPERATIONS
    private void setWidgetOperations() {
        ivTodoItem.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnDone.setOnClickListener(this);
    }
    //endregion

    //region FOR SET DATA
    private void setData() {
        setTitleText("New Todo Item");
    }
    //endregion

    //region FOR CHECK PERMISSION
    private void checkPermission() {
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!hasPermissions(this, permissions)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (int a = 0; a < permissions.length; a++) {
                    if (checkSelfPermission(permissions[a]) != PackageManager.PERMISSION_GRANTED) {

                    }
                }
                requestPermissions(permissions, REQUEST_PERMISSION);
            }
        } else {
            showPictureChooser();
        }
    }
    //endregion

    //region HAS PERMISSION
    boolean hasPermissions(Context context, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int a = 0; a < permissions.length; a++) {
                if (ActivityCompat.checkSelfPermission(context, permissions[a]) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    //endregion

    //region FOR SHOW PICTURE CHOOSER
    private void showPictureChooser() {
        final Dialog a = new Dialog(getActivity());
        Window w = a.getWindow();
        a.requestWindowFeature(Window.FEATURE_NO_TITLE);
        a.setContentView(R.layout.spinner);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        TextView lblselect = (TextView) w.findViewById(R.id.dialogtitle);
        lblselect.setTypeface(Utils.getBold(getActivity()));
        lblselect.setText("Choose");

        SpinnerAdapter adapter = new SpinnerAdapter(getActivity());
        ListView lv = (ListView) w.findViewById(R.id.lvSpinner);
        lv.setAdapter(adapter);
        adapter.add(new Spinner("1", getString(R.string.choose_gallery)));
        adapter.add(new Spinner("2", getString(R.string.choose_camera)));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterview, View view, int position, long l) {
                a.dismiss();
                if (position == 0) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);

                    try {
                        startActivityForResult(Intent.createChooser(intent, "Select"), REQ_PICK_IMAGE);
                    } catch (Exception ex) {
                        showToast("Please install a File Manager.", Toast.LENGTH_SHORT);
                    }
                } else if (position == 1) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    intent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
                    fileUri = new File(Utils.getOutputMediaFile().getAbsolutePath());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", fileUri));
                    try {
                        startActivityForResult(Intent.createChooser(intent, "Select"), REQ_CAPTURE_IMAGE);
                    } catch (Exception ex) {
                        showToast("Can't open your Camera.", Toast.LENGTH_SHORT);
                    }
                }
            }
        });
        a.show();
    }
    //endregion

    //region FOR START CROP ACTIVITY
    public void startCropActivity(Uri tmp_fileUri) {
        CropImage.activity(tmp_fileUri)
                .setGuidelines(CropImageView.Guidelines.OFF)
                .setAllowRotation(true)
                .setFixAspectRatio(true)
                .start(this);
    }
    //endregion

    //region FOR SHOW PERMISSION ALERT DIALOG
    public void showPermissionAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Important")
                .setMessage(getActivity().getResources().getString(R.string.permission_msg))
                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    //endregion

    //region FOR VALIDATE TO-DO ITEM
    private boolean validateTodoItem(String title, String desc) {
        if (!TextUtils.isEmpty(selectedImagePath)){
            if (!TextUtils.isEmpty(title)){
                edTitle.setError(null);
                if (!TextUtils.isEmpty(desc)){
                    edDesc.setError(null);
                    return true;
                }else {
                    edDesc.setError("Please enter Description!");
                    Utils.showKeyBoard(getActivity(), edDesc);
                }
            }else {
                edTitle.setError("Please enter Title!");
                Utils.showKeyBoard(getActivity(), edTitle);
            }
        }else {
            showToast("Please select image!");
        }

        return false;
    }
    //endregion

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isPermissionGranted = true;
        if (requestCode == REQUEST_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                for (int a = 0; a < permissions.length; a++) {
                    if (checkSelfPermission(permissions[a]) != PackageManager.PERMISSION_GRANTED) {
                        isPermissionGranted = false;
                    }
                }
                if (!isPermissionGranted) {
                    showPermissionAlertDialog();
                } else {
                    showPictureChooser();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Debug.e("", "requestCode# " + requestCode + " resultCode# " + resultCode);

            if (requestCode == REQ_CAPTURE_IMAGE) {
                if (resultCode == RESULT_OK) {
                    try {
                        if (fileUri == null || !fileUri.exists()) {
                            Uri tmp_fileUri = data.getData();
                            Debug.e("", "tmp_fileUri : " + tmp_fileUri.getPath());
                            String selectedImagePath = UriHelper.getPath(getActivity(), tmp_fileUri);
                            fileUri = new File(selectedImagePath);
                        } else {
                        }

                        if (fileUri != null && fileUri.exists()) {
                            if (Utils.isJPEGorPNG(fileUri.getAbsolutePath())) {
                                startCropActivity(Uri.fromFile(fileUri));
                            } else {
                                showToast("Select PNG or JPEG file only", Toast.LENGTH_SHORT);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == REQ_PICK_IMAGE) {
                if (resultCode == RESULT_OK) {
                    Uri tmp_fileUri = data.getData();
                    Debug.e("", "tmp_fileUri : " + tmp_fileUri.getPath());
                    String selectedImagePath = UriHelper.getPath(getActivity(), tmp_fileUri);
                    fileUri = new File(selectedImagePath);
                    if (Utils.isJPEGorPNG(fileUri.getAbsolutePath())) {
                        startCropActivity(tmp_fileUri);
                    } else {
                        showToast("Select PNG or JPEG file only", Toast.LENGTH_SHORT);
                    }
                }
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    selectedImagePath = UriHelper.getPath(getActivity(), resultUri);
                    fileUri = new File(selectedImagePath);

                    Debug.e("", "fileUri : " + fileUri.getAbsolutePath());
                    ivTodoItem.setImageURI(Utils.getUri(fileUri));
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivTodoItem:
                checkPermission();
                break;

            case R.id.btnDone:
                String title = edTitle.getText().toString();
                String desc = edDesc.getText().toString();

                if(validateTodoItem(title, desc)){
                    ArrayList<TodoItem> todoItems = new ArrayList<>();
                    if (Utils.getTodoItems(getActivity()) != null){
                        todoItems.addAll(Utils.getTodoItems(getActivity()));
                    }
                    todoItems.add(new TodoItem(""  + todoItems.size(), title, desc, selectedImagePath, false));

                    Utils.setPref(getActivity(), Constant.sTO_DO_LIST_INFO, new Gson().toJson(todoItems));
                    setResult(RESULT_OK);
                    onBackPressed();
                }
                break;

            case R.id.btnCancel:
                onBackPressed();
                break;
        }
    }
}
