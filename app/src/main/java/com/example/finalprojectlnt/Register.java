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

import com.example.finalprojectlnt.ui.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private EditText etid, etemail, etname, etpass, etcpass;
    private Button regist;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button login = findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activity_login = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity_login);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        etid = (EditText) findViewById(R.id.idbimbel);
        etemail = (EditText) findViewById(R.id.email);
        etname = (EditText)findViewById(R.id.username);
        etpass = (EditText)findViewById(R.id.password);
        etcpass = (EditText)findViewById(R.id.confirmpassword);
        regist = (Button) findViewById(R.id.registuser);

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+[.]+[Cc]+[Oo]+[Mm]";

        Button signuphome = findViewById(R.id.registuser);
        signuphome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etid.getEditableText().toString().trim();
                String email = etemail.getEditableText().toString().trim();
                String name = etname.getEditableText().toString().trim();
                String pass = etpass.getEditableText().toString().trim();
                String cpass = etcpass.getEditableText().toString().trim();

                if(id.isEmpty()){
                    etid.setError("Id is empty!");
                    etid.requestFocus();
                    return;
                }

                if(email.isEmpty()){
                    etemail.setError("Email is empty!");
                    etemail.requestFocus();
                    return;
                }

                if(!email.matches(regex)){
                    etemail.setError("Invalid email, must end with '.com'");
                    etemail.requestFocus();
                    return;
                }

                if(name.isEmpty()){
                    etname.setError("Username is empty!");
                    etname.requestFocus();
                    return;
                }

                if(name.length() < 5){
                    etname.setError("Min Username should be 5 characters!");
                    etname.requestFocus();
                    return;
                }

                if(pass.isEmpty()){
                    etpass.setError("Password is empty!");
                    etpass.requestFocus();
                    return;
                }

                if(pass.length() < 6){
                    etpass.setError("Min Password should be 5 characters!");
                    etpass.requestFocus();
                    return;
                }

                if(cpass.isEmpty()){
                    etcpass.setError("Confirm password is empty!");
                    etcpass.requestFocus();
                    return;
                }

                if(!cpass.equals(pass)){
                    etcpass.setError("Enter with the same password!");
                    etcpass.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(Register.this, "Register Success!", Toast.LENGTH_LONG).show();

                            User usersdetail = new User(id, name, email, pass);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseRef = database.getReference("Users");
                            FirebaseUser users = mAuth.getCurrentUser();

                            databaseRef.child(users.getUid()).setValue(usersdetail);

                            startActivity(new Intent(Register.this, MainActivity.class));
                            finish();

                        }
                        else{
                            String erString = task.getException().toString();
                            String etString = "Exception:";
                            String error = erString.substring(erString.indexOf(etString) + etString.length() + 1, erString.length());
                            Toast.makeText(Register.this, "Error: "+error +", Please Try Again", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });

    }
    @Override
    public void onClick(View v) {

    }
}