package com.example.praneet.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<ToDo> todos;
    private String uid;

    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    RecyclerViewAdapter(ArrayList<ToDo> todos, String uid) {
        this.todos = todos;
        this.uid = uid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("users").child(uid);

        ToDo todo = todos.get(position);

        holder.title.setText(todo.getTitle());
        holder.subtitle.setText(todo.getSubtitle());
        holder.checkBox.setChecked(todo.isChecked());

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "onClick: Clicked on " + item_title.get(holder.getAdapterPosition()));

                CheckBox check;
                int pos = holder.getAdapterPosition();

                check = v.findViewById(R.id.checkBox);

                if (!check.isChecked())
                {
                    todos.get(pos).setChecked(true);
                    check.setChecked(true);
                    dbRef.child(Integer.toString(pos + 1)).child("checked").setValue(true);
                    Collections.swap(todos, pos, todos.size() - 1);
                    notifyItemMoved(pos, todos.size() - 1);
                }
                else {
                    todos.get(pos).setChecked(true);
                    check.setChecked(false);
                    dbRef.child(Integer.toString(pos + 1)).child("checked").setValue(false);
                    Collections.swap(todos, pos, 0);
                    notifyItemMoved(pos, 0);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView title, subtitle;

        LinearLayout parent_layout;

        public ViewHolder(View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox);
            title = itemView.findViewById(R.id.list_title);
            subtitle = itemView.findViewById(R.id.subtitle);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }

    }

}
