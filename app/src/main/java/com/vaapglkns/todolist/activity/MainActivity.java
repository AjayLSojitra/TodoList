package com.vaapglkns.todolist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.vaapglkns.todolist.R;
import com.vaapglkns.todolist.adapter.AdapterTodoList;
import com.vaapglkns.todolist.model.TodoItem;
import com.vaapglkns.todolist.utils.Constant;
import com.vaapglkns.todolist.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final int ASS_TO_DT_ITEM_ACTIVITY_RESULT = 801;

    private AdapterTodoList adapterTodoList;
    private ArrayList<TodoItem> todoItems = new ArrayList<>();

    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.edSearch)
    EditText edSearch;
    @BindView(R.id.ivClose)
    ImageView ivClose;

    @BindView(R.id.rvTodoList)
    RecyclerView rvTodoList;

    @BindView(R.id.tvAddNewTodo)
    TextView tvAddNewTodo;
    @BindView(R.id.tvError)
    TextView tvError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeWidget();
        setWidgetOperations();
        setData();
        onCreateActions();
    }

    //region FOR INITIALIZE WIDGET
    private void initializeWidget() {
        rvTodoList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTodoList.setLayoutAnimation(Utils.getRowFadeSpeedAnimation(getActivity()));
        adapterTodoList = new AdapterTodoList(getActivity());
        rvTodoList.setAdapter(adapterTodoList);
        adapterTodoList.setEventListener(new AdapterTodoList.EventListener() {
            @Override
            public void onItemViewClick(int position) {
                adapterTodoList.setChecked(adapterTodoList.getItem(position).getId());

                if (adapterTodoList.getItem(position).isChecked()){
                    TodoItem newItem = adapterTodoList.getItem(position);
                    todoItems.remove(adapterTodoList.getItem(position));
                    todoItems.add(adapterTodoList.items.size() - 1, newItem);
                    adapterTodoList.addAll(todoItems);
                }

                //FOR UPDATE TO-DO ITEMS
                Utils.setPref(getActivity(), Constant.sTO_DO_LIST_INFO, new Gson().toJson(todoItems));
            }

            @Override
            public void onImageViewClick(int position) {
                showBigImagePopup(-1, adapterTodoList.getItem(position).getImagePath(), "", new File(adapterTodoList.getItem(position).getImagePath()).getName());
            }
        });
    }
    //endregion

    //region FOR SET WIDGET OPERATIONS
    private void setWidgetOperations() {
        tvAddNewTodo.setOnClickListener(this);
        ivClose.setOnClickListener(this);

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivClose.setVisibility(View.VISIBLE);
                    filterCategory(s);
                } else {
                    if (todoItems.size() > 0) {
                        adapterTodoList.addAll(todoItems);
                        showError(false);
                    } else {
                        showError(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    //endregion

    //region FOR SET DATA
    private void setData() {
        setTitleText("Todo List");

        if (Utils.getTodoItems(getActivity()) != null){
            todoItems = new ArrayList<>();
            todoItems.addAll(Utils.getTodoItems(getActivity()));
        }
        adapterTodoList.addAll(todoItems);

        if (todoItems.size() <= 0){
            showError(true);
            addNewTodoItem();
        }else {
            showError(false);
        }
    }
    //endregion

    //region FOR ON-CREATE ACTIONS
    private void onCreateActions() {
        tvAddNewTodo.setVisibility(View.VISIBLE);
    }
    //endregion

    //region FOR ADD NEW TO-DO ITEM
    private void addNewTodoItem() {
        startNewActivityForResult(new Intent(getActivity(), AddTodoItemActivity.class), ASS_TO_DT_ITEM_ACTIVITY_RESULT);
    }
    //endregion

    //region FOR REFRESH DATA
    private void refreshData() {
        setData();
    }
    //endregion

    //region FOR SHOW ERROR
    private void showError(boolean isError) {
        if (isError) {
            tvError.setVisibility(View.VISIBLE);
        }else {
            tvError.setVisibility(View.GONE);
        }
    }
    //endregion

    //region FOR FILTER CATEGORY
    private void filterCategory(CharSequence query) {
        String lowerCaseQuery = query.toString().toLowerCase();

        List<TodoItem> filteredList = new ArrayList();
        for (TodoItem category : todoItems) {
            if (category.getTitle().toLowerCase().contains(lowerCaseQuery)) {
                filteredList.add(category);
            }
        }
        adapterTodoList.addAll(filteredList);
        if (filteredList.size() > 0) {
            showError(false);
        } else {
            showError(true);
        }
    }
    //endregion

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddNewTodo:
                addNewTodoItem();
                break;

                case R.id.ivClose:
                    //FOR HIDE KEYBOARD AND CLOSE SEARCH
                    ivClose.setVisibility(View.GONE);
                    edSearch.setText("");
                    Utils.hideKeyBoard(getActivity(), edSearch);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ASS_TO_DT_ITEM_ACTIVITY_RESULT:
                if (resultCode == RESULT_OK) {
                    refreshData();
                }
                break;
        }
    }
}
