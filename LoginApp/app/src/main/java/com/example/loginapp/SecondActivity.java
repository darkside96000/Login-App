

package com.example.loginapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class SecondActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button signoutBtn;

    TextView a,b,c,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        a = findViewById(R.id.a);
        b = findViewById(R.id.b);
        c = findViewById(R.id.c);
        d = findViewById(R.id.d);
        a.setOnClickListener(view -> {

            Uri uri = Uri.parse("http://amappsd5.online.global.prod.fastly.net/all/apps/face-mobile/?i=25400");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));

        });
    b.setOnClickListener(view -> {
        Uri uri = Uri.parse("http://amappsd5.online.global.prod.fastly.net/all/apps/Instagram/?i=25400");
        startActivity(new Intent(Intent.ACTION_VIEW, uri));

    });






        signoutBtn=findViewById(R.id.signout);

        gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);


        signoutBtn.setOnClickListener(view -> signOut());
    }
    void signOut(){
        gsc.signOut().addOnCompleteListener(task -> {
            finish();
            startActivity(new Intent(SecondActivity.this,MainActivity.class));


        });
    }
}