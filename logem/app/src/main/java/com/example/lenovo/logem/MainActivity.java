package com.example.lenovo.logem;

import android.app.ProgressDialog; 
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private EditText Name,Password;
    private TextView Info;
    private Button Login;
    private int counter=3;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressBar Pbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.btnLogin);
        userRegistration = (TextView) findViewById(R.id.tvRegister);
        Info.setText("No. of attempts remaining: 3");
        Pbar = (ProgressBar)findViewById(R.id.progressBar);
        Pbar.setVisibility(View.GONE);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pbar.setVisibility(View.VISIBLE);
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });


    }
    private void validate(final String userName, String userPassword){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Unsuccessfull", Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No. of attempts remaining: "+ String.valueOf(counter));
                    if(counter==0){
                        Login.setEnabled(false);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Hello " + userName, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                    startActivity(intent);
                    //checkEmailVerification();
                }
            }
        });
    }
//private void  checkEmailVerification() {
    //FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    //Boolean emailflag=firebaseUser.isEmailVerified();

    //if(emailflag) {
      //  Pbar.setVisibility(View.VISIBLE);
        //startActivity(new Intent(MainActivity.this,SecondActivity.class));
    //}
    //else{
      //  Toast.makeText(this, "Verify your Email", Toast.LENGTH_SHORT).show();
        //firebaseAuth.signOut();
    //}
    //}


}



