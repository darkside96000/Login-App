package com.example.loginapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity2 extends AppCompatActivity {
    Button reg;
    TextView all;
    EditText inputmail, inputPassword, inputconfirmpassword;
    String emailPattern = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
            + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        inputmail=findViewById(R.id.email);
        inputPassword=findViewById(R.id.pass1);
        reg=findViewById(R.id.Sub_btn);
        progressDialog=new ProgressDialog(this);
        inputconfirmpassword=findViewById(R.id.password1);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        all=findViewById(R.id.aas);
        all.setOnClickListener(view -> {

            finish();
            startActivity(new Intent(Activity2.this,MainActivity.class));
        });


        reg.setOnClickListener(view -> PerformAuth());


    }

    private void PerformAuth() {
        String email = inputmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmpassword= inputconfirmpassword.getText().toString();
        if (!email.matches(emailPattern)) {
            inputmail.setError("Enter correct Email id");


        } else if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Enter Proper Password");

        }else if (!password.equals(confirmpassword)){
            inputconfirmpassword.setError("Password Not Matched");
        }else
        {
            progressDialog.setMessage("Registration...");
            progressDialog.setTitle("Please Wait While Registration..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    sendUserToNextActivity();
                    progressDialog.dismiss();
                    Toast.makeText(Activity2.this, "Registration Successful!!!", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(Activity2.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }

            });

        }
    }

    private void sendUserToNextActivity() {

        Intent intent=new Intent(Activity2.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}