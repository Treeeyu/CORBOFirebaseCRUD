package com.example.corbofirebasecrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentAdapter studentAdapter;

    FloatingActionButton fltbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<StudentModel> options =
                new FirebaseRecyclerOptions.Builder<StudentModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("students"), StudentModel.class)
                        .build();

        studentAdapter = new StudentAdapter(options);
        recyclerView.setAdapter(studentAdapter);
        recyclerView.setItemAnimator(null);
        fltbtn = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fltbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddActivity.class));

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        studentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        studentAdapter.stopListening();
    }
}