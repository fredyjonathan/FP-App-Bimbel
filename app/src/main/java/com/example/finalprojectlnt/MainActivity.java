package com.example.finalprojectlnt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TextView register;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        EditText logemail = (EditText) findViewById(R.id.email2);
        EditText logpass = (EditText) findViewById(R.id.password2);

        Button signup = findViewById(R.id.btn_register);
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent activity_registration = new Intent(MainActivity.this, Register.class);
                startActivity(activity_registration);
            }
        });

        Button loginhome = findViewById(R.id.btn_login);
        loginhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email2 = logemail.getEditableText().toString().trim();
                String pass2 = logpass.getEditableText().toString().trim();

                if(email2.isEmpty()){
                    logemail.setError("Email is Required!");
                    logemail.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email2).matches()){
                    logemail.setError("Invalid email");
                    logemail.requestFocus();
                    return;
                }

                if(pass2.isEmpty()){
                    logpass.setError("Password is Required!");
                    logpass.requestFocus();
                    return;
                }

                if(pass2.length() < 6){
                    logpass.setError("Min Password should be 5 characters!");
                    logpass.requestFocus();
                    return;
                }

                SharedPreferences countref = getSharedPreferences("mref", Context.MODE_PRIVATE);
                SharedPreferences.Editor mycount = countref.edit();
                mycount.putInt("cnum", 0);
                mycount.commit();

                mAuth.signInWithEmailAndPassword(email2, pass2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent activityhome = new Intent(MainActivity.this, homepage.class);
                            startActivity(activityhome);
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Failed to login, Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}