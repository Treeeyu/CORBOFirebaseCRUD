package com.example.corbofirebasecrud;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class StudentAdapter extends FirebaseRecyclerAdapter<StudentModel,StudentAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public StudentAdapter(@NonNull FirebaseRecyclerOptions<StudentModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull StudentModel model) {
        holder.fname.setText("Name: " + model.getFname());
        holder.lname.setText(model.getLname());
        holder.course.setText("Course: "+model.getCourse());
        holder.email.setText("Email: "+model.getEmail());
        holder.grade.setText("Grade: "+model.getGrade().toString());

        holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.fname.getContext()).setContentHolder(new ViewHolder(R.layout.updatepopup))
                        .setExpanded(true,1300).create();

                View v = dialogPlus.getHolderView();

                EditText fname = v.findViewById(R.id.fnameet);
                EditText lname = v.findViewById(R.id.lnameet);
                EditText course = v.findViewById(R.id.courseet);
                EditText email = v.findViewById(R.id.emailet);
                EditText grade = v.findViewById(R.id.gradeet);

                Button updatebtn = v.findViewById(R.id.updatebtn);

                fname.setText(model.getFname());
                lname.setText(model.getLname());
                course.setText(model.getCourse());
                email.setText(model.getEmail());
                grade.setText(model.getGrade().toString());

                dialogPlus.show();

                updatebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("fname",fname.getText().toString());
                        map.put("lname",lname.getText().toString());
                        map.put("email",email.getText().toString());
                        map.put("course",course.getText().toString());
                        map.put("grade",Double.parseDouble(grade.getText().toString()));

                        FirebaseDatabase.getInstance().getReference().child("students").child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(holder.fname.getContext(),"Student Details Updated", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(holder.fname.getContext(),"Student Details failed to update", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });



            }
        });
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("students").child(Objects.requireNonNull(getRef(position).getKey())).removeValue();
                Toast.makeText(holder.fname.getContext(),"Student Details Deleted", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentview,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView fname, lname, course,email,grade;

        Button editbtn, deletebtn;
        FloatingActionButton fltbtn;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            fname = (TextView) itemView.findViewById(R.id.fnametv);
            lname = (TextView) itemView.findViewById(R.id.lnametv);
            course = (TextView) itemView.findViewById(R.id.coursetv);
            email = (TextView) itemView.findViewById(R.id.emailtv);
            grade = (TextView) itemView.findViewById(R.id.gradetv);

            editbtn = (Button) itemView.findViewById(R.id.editbtn);
            deletebtn = (Button) itemView.findViewById(R.id.deletebtn);

        }
    }
}
