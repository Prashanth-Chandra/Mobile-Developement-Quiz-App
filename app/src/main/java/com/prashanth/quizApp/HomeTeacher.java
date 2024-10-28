package com.prashanth.quizApp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeTeacher extends AppCompatActivity {

    private String userUID;
    private String firstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_teacher);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        ProgressDialog progressDialog = new ProgressDialog(HomeTeacher.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Bundle b = getIntent().getExtras();
        userUID = b.getString("User UID");

        TextView name = findViewById(R.id.name);
        Button createQuiz = findViewById(R.id.createQuiz);
        RelativeLayout your_quizzes = findViewById(R.id.your_quizzes);
        RelativeLayout all_quizzes = findViewById(R.id.all_quizzes);
        EditText quiz_title = findViewById(R.id.quiz_title);
        ImageView signout = findViewById(R.id.signout);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(HomeTeacher.this, "Logged in as Teacher", Toast.LENGTH_SHORT).show();

                DataSnapshot usersRef = snapshot.child("Users").child(userUID);
                firstName = usersRef.child("First Name").getValue().toString();

                name.setText("Welcome "+firstName+"!");

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeTeacher.this, "Can't connect", Toast.LENGTH_SHORT).show();
            }
        };
        database.addValueEventListener(listener);

        signout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(HomeTeacher.this, MainActivity.class);
            startActivity(i);
            finish();
        });

        createQuiz.setOnClickListener(v -> {
            if (quiz_title.getText().toString().equals("")) {
                quiz_title.setError("Quiz title cannot be empty");
                return;
            }
            Intent i = new Intent(HomeTeacher.this, ExamEditor.class);
            i.putExtra("Quiz Title", quiz_title.getText().toString());
            quiz_title.setText("");
            startActivity(i);
        });

        your_quizzes.setOnClickListener(v -> {
            Intent i = new Intent(HomeTeacher.this, ListQuizzes.class);
            i.putExtra("Operation", "List Created Quizzes");
            startActivity(i);
        });

        all_quizzes.setOnClickListener(v -> {
            Intent i = new Intent(HomeTeacher.this, AllQuizzes.class);
            i.putExtra("Operation", "List Created Quizzes");
            startActivity(i);
        });

    }
}