package com.example.lenovo.logem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userName,userPassword,cuserPassword,userEmail,userPhone;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        firebaseAuth=FirebaseAuth.getInstance();

    regButton.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         if (validate()) {
                                             //Upload  this data to database
                                             String user_Email = userEmail.getText().toString().trim();
                                             String user_Password = userPassword.getText().toString().trim();
                                             if(isEmailValid(user_Email)) {
                                                 firebaseAuth.createUserWithEmailAndPassword(user_Email, user_Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                     @Override
                                                     public void onComplete(@NonNull Task<AuthResult> task) {

                                                         if (!task.isSuccessful()) {
                                                             Toast.makeText(RegistrationActivity.this, "Registration Unsuccessfull", Toast.LENGTH_SHORT).show();
                                                         } else {
                                                             //startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                                             //Toast.makeText(RegistrationActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                                                         sendEmailVerification();
                                                         }
                                                     }
                                                 });
                                             }
                                         }
                                     }
                                 });
    userLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
        }
    });

    }

    public static boolean isEmailValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    private void setupUIViews(){
        userName=(EditText)findViewById(R.id.etUserName);
        userPassword=(EditText)findViewById(R.id.etUserPassword);
        userEmail=(EditText)findViewById(R.id.etUserEmail);
        regButton=(Button)findViewById(R.id.btnRegister);
        userLogin=(TextView)findViewById(R.id.tvUserLogin);
        cuserPassword=(EditText)findViewById(R.id.etcUserPassword);
        userPhone=(EditText)findViewById(R.id.etUserPhone);
    }

    private Boolean validate(){
        Boolean result=true;
        String cpassword=cuserPassword.getText().toString();
        String name = userName.getText().toString();
        String password=userPassword.getText().toString();
        String email=userEmail.getText().toString();
        String phone=userPhone.getText().toString();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this,"Please enter all the details",Toast.LENGTH_SHORT).show();
        }

        else if(!password.equals(cpassword)) {
            Toast.makeText(RegistrationActivity.this, "Password Not matching", Toast.LENGTH_SHORT).show();
            result = false;
        }

     return result;
    }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                 if(task.isSuccessful()){
                     Toast.makeText(RegistrationActivity.this, "Succesfully Regsitered,Verification mail sent", Toast.LENGTH_SHORT).show();
                     firebaseAuth.signOut();
                     finish();
                     startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                 }else{
                     Toast.makeText(RegistrationActivity.this, "Verification mail not sent", Toast.LENGTH_SHORT).show();
                 }
                }
            });
        }
    }

}
