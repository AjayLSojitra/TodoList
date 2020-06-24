package com.vaapglkns.todolist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vaapglkns.todolist.R;
import com.vaapglkns.todolist.model.TodoItem;
import com.vaapglkns.todolist.utils.Utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VAA on 7/7/2017.
 */

public class AdapterTodoList extends RecyclerView.Adapter<AdapterTodoList.MyViewHolder> {
    public List<TodoItem> items = new ArrayList<>();
    private EventListener mEventListener;
    private Context context;

    public AdapterTodoList(Context c) {
        this.context = c;
    }

    public void addAll(List<TodoItem> mData) {
        items = new ArrayList<>();
        items.addAll(mData);
        notifyDataSetChanged();
    }

    public void setChecked(String id) {
        for (int v=0 ; v<items.size(); v++) {
            if (id.equals(items.get(v).getId())){
                items.get(v).setChecked(!items.get(v).isChecked());
            }
        }

        notifyDataSetChanged();
    }

    public TodoItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.row_to_list_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        TodoItem item = items.get(position);

        holder.ivRowTodoItem.setImageURI(Utils.getUri(new File(Utils.nullSafe(item.getImagePath()))));
        holder.tvRowTodoTitle.setText(Utils.nullSafe(item.getTitle()));
        holder.tvRowTodoDesc.setText(Utils.nullSafe(item.getDesc()));
        holder.cbRowTodo.setChecked(item.isChecked());

        if (item.isChecked()){
            holder.rowContainer.setBackgroundColor(context.getResources().getColor(R.color.gray_light__));
        }else {
            holder.rowContainer.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        holder.cbRowTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventListener != null) {
                    mEventListener.onItemViewClick(position);
                }
            }
        });

        holder.ivRowTodoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventListener != null) {
                    mEventListener.onImageViewClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rowContainer)
        LinearLayout rowContainer;

        @BindView(R.id.ivRowTodoItem)
        ImageView ivRowTodoItem;

        @BindView(R.id.tvRowTodoTitle)
        TextView tvRowTodoTitle;
        @BindView(R.id.tvRowTodoDesc)
        TextView tvRowTodoDesc;

        @BindView(R.id.cbRowTodo)
        CheckBox cbRowTodo;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface EventListener {
        void onItemViewClick(int position);
        void onImageViewClick(int position);
    }

    public void setEventListener(EventListener eventListener) {
        this.mEventListener = eventListener;
    }
}