//package com.prashanth.quizApp;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class Signup extends AppCompatActivity {
//
//    private FirebaseAuth auth;
//    private DatabaseReference database;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//
//        auth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance().getReference();
//
//        ProgressDialog progressDialog = new ProgressDialog(Signup.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//        EditText first_name = findViewById(R.id.first_name);
//        EditText last_name = findViewById(R.id.last_name);
//        EditText email = findViewById(R.id.email);
//        EditText password = findViewById(R.id.password);
//        EditText confirm_password = findViewById(R.id.confirm_password);
//        Button signup = findViewById(R.id.signup);
//        TextView login = findViewById(R.id.login);
//
//        signup.setOnClickListener(view -> {
//
//            Thread thread = new Thread(() -> {
//                String pass = password.getText().toString();
//                String confirmPass = confirm_password.getText().toString();
//                String em = email.getText().toString();
//                String firstName = first_name.getText().toString();
//                String lastName = last_name.getText().toString();
//
////                if (firstName.isEmpty()) {
////                    runOnUiThread(() -> {
////                        Toast.makeText(Signup.this, "Rey, you don't have a First Name aa what? O_O", Toast.LENGTH_SHORT).show();
////                    });
////                    return;
////                }
////
////                if (lastName.isEmpty()) {
////                    runOnUiThread(() -> {
////                        Toast.makeText(Signup.this, "Orey, check your aadhar card and enter last name ㄟ(≧◇≦)ㄏ", Toast.LENGTH_SHORT).show();
////                    });
////                    return;
////                }
//
////                String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
////                Pattern pattern = Pattern.compile(emailRegex);
////                Matcher matcher = pattern.matcher(em);
//
////                if (!matcher.matches()) {
////                    runOnUiThread(() -> {
////                        Toast.makeText(Signup.this, "Enter proper email", Toast.LENGTH_SHORT).show();
////                    });
////                    return;
////                }
//
//                if (!pass.equals(confirmPass)) {
//                    runOnUiThread(() -> {
//                        confirm_password.setError("Password doesn't match");
//                        progressDialog.dismiss();
//                    });
//                    return;
//                }
//
//
//                auth.createUserWithEmailAndPassword(em, pass).addOnCompleteListener(Signup.this, (OnCompleteListener<AuthResult>) task -> {
//                    if (task.isSuccessful()) {
//                        FirebaseUser user = auth.getCurrentUser();
//                        DatabaseReference ref = database.child("Users").child(user.getUid());
//                        ref.child("First Name").setValue(firstName);
//                        ref.child("Last Name").setValue(lastName);
//                        runOnUiThread(() -> {
//                            progressDialog.dismiss();
//                            Intent i = new Intent(Signup.this, Home.class);
//                            i.putExtra("User UID", user.getUid());
//                            startActivity(i);
//                            finish();
//                        });
//                    } else {
//                        Toast.makeText(Signup.this, "Operation Failed", Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                    }
//                });
//            });
//            thread.start();
//        });
//
//        login.setOnClickListener(view -> {
//            Intent i = new Intent(Signup.this, MainActivity.class);
//            startActivity(i);
//            finish();
//        });
//
//    }
//}


package com.prashanth.quizApp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        EditText first_name = findViewById(R.id.first_name);
        EditText last_name = findViewById(R.id.last_name);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText confirm_password = findViewById(R.id.confirm_password);
        RadioGroup userTypeGroup = findViewById(R.id.user_type); // RadioGroup for user type
        Button signup = findViewById(R.id.signup);
        TextView login = findViewById(R.id.login);

        signup.setOnClickListener(view -> {
            ProgressDialog progressDialog = new ProgressDialog(Signup.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Thread thread = new Thread(() -> {
                String pass = password.getText().toString();
                String confirmPass = confirm_password.getText().toString();
                String em = email.getText().toString();
                String firstName = first_name.getText().toString();
                String lastName = last_name.getText().toString();

                if (firstName.isEmpty()) {
                    runOnUiThread(() -> {
                        Toast.makeText(Signup.this, "Rey, you don't have a First Name aa what? O_O", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });
                    return;
                }

                if (lastName.isEmpty()) {
                    runOnUiThread(() -> {
                        Toast.makeText(Signup.this, "Orey, check your aadhar card and enter last name ㄟ(≧◇≦)ㄏ", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });
                    return;
                }

                String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
                Pattern pattern = Pattern.compile(emailRegex);
                Matcher matcher = pattern.matcher(em);
                if (!matcher.matches()) {
                    runOnUiThread(() -> {
                        Toast.makeText(Signup.this, "Enter proper email", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });
                    return;
                }

                if(pass.length() < 8){
                    runOnUiThread(() -> {
                        Toast.makeText(Signup.this, "Password length must be greater than 8", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });
                    return;
                }


                int selectedId = userTypeGroup.getCheckedRadioButtonId();
                String userType;
                if (selectedId == R.id.student_radio) {
                    userType = "Student";
                } else if (selectedId == R.id.teacher_radio) {
                    userType = "Teacher";
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(Signup.this, "Please select Student or Teacher", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });
                    return;
                }

                if (!pass.equals(confirmPass)) {
                    runOnUiThread(() -> {
                        confirm_password.setError("Password doesn't match");
                        progressDialog.dismiss();
                    });
                    return;
                }

                auth.createUserWithEmailAndPassword(em, pass).addOnCompleteListener(Signup.this, (OnCompleteListener<AuthResult>) task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        DatabaseReference ref = database.child("Users").child(user.getUid());
                        ref.child("First Name").setValue(firstName);
                        ref.child("Last Name").setValue(lastName);
                        ref.child("User Type").setValue(userType);
                        ref.child("Mail").setValue(em);
                        Toast.makeText(Signup.this, "Created User Successfully", Toast.LENGTH_SHORT).show();

                        runOnUiThread(() -> {
                            progressDialog.dismiss();
                            Intent i;
                            if(userType.equals("Teacher")){
                                i = new Intent(Signup.this, HomeTeacher.class);
                            }
                            else{
                                i = new Intent(Signup.this, HomeStudent.class);
                            }
                            i.putExtra("User UID", user.getUid());
                            startActivity(i);
                            finish();
                        });
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(Signup.this, "Operation Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        });
                    }
                });
            });
            thread.start();
        });

        login.setOnClickListener(view -> {
            Intent i = new Intent(Signup.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }
}
