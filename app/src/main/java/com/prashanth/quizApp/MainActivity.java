package com.prashanth.quizApp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        TextView signup = findViewById(R.id.signup);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        String userType;

        login.setOnClickListener(view -> {
            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading...");
//            progressDialog.setCancelable(false);
            progressDialog.show();

            Thread thread = new Thread(() -> {
                String em = email.getText().toString();
                String pass = password.getText().toString();

                if(em.isEmpty()){
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Konichiwa, Enter your mail BAKA (╬▔皿▔)╯", Toast.LENGTH_SHORT).show();
                    });
                    progressDialog.dismiss();
                    return;
                }

                if(pass.isEmpty()){
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Kul ja sim sim nehi he ye, password enter kar", Toast.LENGTH_SHORT).show();
                    });
                    progressDialog.dismiss();
                    return;
                }



                auth.signInWithEmailAndPassword(em, pass).addOnCompleteListener(MainActivity.this,
                        (OnCompleteListener<AuthResult>) task -> {
                    if(task.isSuccessful()){
                        FirebaseUser user1 = auth.getCurrentUser();
                        runOnUiThread(() -> {

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference usersRef = databaseReference.child("Users").child(user1.getUid());

                            String userid = user1.getUid().toString();
//                            Toast.makeText(MainActivity.this, userid, Toast.LENGTH_SHORT).show();

                            usersRef.child("User Type").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String userRole = dataSnapshot.getValue(String.class);
                                    if(userRole != null) {
                                        if (userRole.equals("Teacher")){
                                            Intent i = new Intent(MainActivity.this, HomeTeacher.class);
                                            i.putExtra("User UID", user1.getUid());
                                            startActivity(i);
                                            finish();
                                        }
                                        else if(userRole.equals("Student")){
                                            Intent i = new Intent(MainActivity.this, HomeStudent.class);
                                            i.putExtra("User UID", user1.getUid());
                                            startActivity(i);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(MainActivity.this, "Sriharikota we have a Problemmmmmm", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    // Handle possible errors.
                                    Toast.makeText(MainActivity.this, "Problemmmmmm", Toast.LENGTH_SHORT).show();
                                }
                            });
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "There is no forget password so bye bye (〜￣▽￣)〜", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            });
            thread.start();
        });

        signup.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Signup.class);
            startActivity(i);
            finish();
        });

    }
}