package com.example.corbofirebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText fname,lname,course,grade,email;

    Button addbtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        fname = (EditText)findViewById(R.id.fnameet);
        lname = (EditText)findViewById(R.id.lnameet);
        course= (EditText)findViewById(R.id.courseet);
        grade = (EditText)findViewById(R.id.gradeet);
        email = (EditText)findViewById(R.id.emailet);

        addbtn = (Button)findViewById(R.id.addbtn);
        backbtn = (Button)findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<>();
                map.put("fname",fname.getText().toString());
                map.put("lname",lname.getText().toString());
                map.put("email",email.getText().toString());
                map.put("course",course.getText().toString());
                map.put("grade",Double.parseDouble(grade.getText().toString()));

                FirebaseDatabase.getInstance().getReference().child("students").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this,"Student Added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this,"Adding Student failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}