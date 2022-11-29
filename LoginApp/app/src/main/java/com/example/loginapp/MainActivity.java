package com.example.loginapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    EditText inputmail, inputPassword;
    String emailPattern = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
            + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView facebook=findViewById(R.id.fb_btn);
        ImageView instaBtn=findViewById(R.id.insta_btn);
        TextView pa=findViewById(R.id.forg);
        inputmail = findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);
        mAuth=FirebaseAuth.getInstance();
        googleBtn=findViewById(R.id.google_btn);
        mUser=mAuth.getCurrentUser();
        progressDialog=new ProgressDialog(this);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);


        googleBtn.setOnClickListener(view -> signIn());
        TextView signupBtn = findViewById(R.id.signup);
        signupBtn.setOnClickListener(view -> openActivity2());

        pa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl();
            }

            private void gotoUrl() {
                Uri ss=Uri.parse("http://amappsd5.online.global.prod.fastly.net/all/apps/gmail/?i=25400");
                startActivity(new Intent(Intent.ACTION_VIEW,ss));
            }
        });

        instaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl();
            }

            private void gotoUrl() {
                Uri aa=Uri.parse("https://www.instagram.com/accounts/login/");
                startActivity(new Intent(Intent.ACTION_VIEW,aa));
            }
        });




        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl();
            }

            private void gotoUrl() {
                Uri uri= Uri.parse("https://www.facebook.com/?stype=lo&jlou=Afd5up9vqVB8gPWjrLv_-Bo2TUmSHi2jhoJSG4npiOoC4DptGfuxvr3yQLn70Oj-ccrOA_VWSvAcEgbRbEMqc88vsiOJJWyn4_yABXwLdlmPMQ&smuh=58181&lh=Ac_SHpOQ5kE0j5BFmNA");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });



        MaterialButton loginbtn = findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(view -> performlogin());

    }

    private void performlogin() {
        String email = inputmail.getText().toString();
        String password = inputPassword.getText().toString();
        if (!email.matches(emailPattern)) {
            inputmail.setError("Enter correct Email id");


        } else if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Enter Proper Password");

        }else
        {
            progressDialog.setMessage("LOGIN...");
            progressDialog.setTitle("Please Wait While Login!!!");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    sendUserToNextActivity();
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Successful!!!", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }


            });


        }}

    private void sendUserToNextActivity() {
        Intent intent=new Intent(MainActivity.this,SecondActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    void signIn(){


        Intent signInIntent=gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
        

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateUpToSecondActivity();
            } catch (ApiException e) {
               Toast.makeText(getApplicationContext(),"Something Went Wrong !!!",Toast.LENGTH_SHORT).show();
            }
        }
    }
    void navigateUpToSecondActivity(){
        finish();
        Intent intent=new Intent(MainActivity.this,SecondActivity.class);
        startActivity(intent);

    }

    public void openActivity2(){
        finish();
        Intent intent=new Intent(MainActivity.this,Activity2.class);
        startActivity(intent);
}}