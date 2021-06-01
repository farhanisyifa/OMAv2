package com.example.oma_v13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText registerFullName, registerEmail, registerPassword, registerConfPass;
    Button registerBtn, gotoLogin;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerFullName = findViewById(R.id.registerFullName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfPass = findViewById(R.id.registerConfPass);
        registerBtn = findViewById(R.id.registerBtn);
        gotoLogin = findViewById(R.id.gotoLogin);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //extract data from the form

                String fullName = registerFullName.getText().toString();
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String confPass = registerConfPass.getText().toString();

                if (fullName.isEmpty()) {
                    registerFullName.setError("Full Name is Required");
                    return;
                }
                if (email.isEmpty()) {
                    registerEmail.setError("Email Adress is Required");
                    return;
                }
                if (password.isEmpty()) {
                    registerPassword.setError("Password is Required");
                    return;
                }
                if (confPass.isEmpty()) {
                    registerConfPass.setError("Please Confirm Your Password");
                    return;
                }
                if(!password.equals(confPass)){
                    registerConfPass.setError("Password do not Match");
                    return;
                }

                //data is validated
                //register user using firebase

                Toast.makeText(Register.this, "Data Validated", Toast.LENGTH_SHORT).show();
                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        userId = fAuth.getCurrentUser().getUid();
                        DocumentReference df = fstore.collection("Users").document(userId);
                        Map<String,Object> userInfo = new HashMap<>();
                        userInfo.put("FullName", registerFullName.getText().toString());
                        userInfo.put("UserEmail", registerEmail.getText().toString());

                        //sepcify if the user is admin
                        userInfo.put("isUser", "1");
                        df.set(userInfo);

                        //send user to next page
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}