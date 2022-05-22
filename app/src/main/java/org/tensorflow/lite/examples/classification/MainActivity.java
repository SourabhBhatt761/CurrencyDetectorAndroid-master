package org.tensorflow.lite.examples.classification;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private TextView register,forgot;
    private EditText TextEmail, TextPassword;
    private Button Signin;
    public static String email;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         register= (TextView) findViewById(R.id.newuser);
         register.setOnClickListener(this);

         Signin = (Button)findViewById(R.id.login);
         Signin.setOnClickListener(this);

         TextEmail = (EditText)findViewById(R.id.lgemail);
         TextPassword = (EditText)findViewById(R.id.lgpass);
         email = TextEmail.getText().toString();

         mAuth = FirebaseAuth.getInstance();

         forgot = (TextView)findViewById(R.id.fgpass);
         forgot.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newuser:
                startActivity(new Intent(this,SignupActivity.class));
                break;
            case R.id.login:
                userLogin();
                break;
            case R.id.fgpass:
                startActivity(new Intent(this,ForgotPasswordActivity.class));
        }
    }

    private void userLogin() {
        String email = TextEmail.getText().toString().trim();
        String password= TextPassword.getText().toString().trim();
        this.email = email;
        //Log.i("mytag",this.email);
        if(email.isEmpty()){
            TextEmail.setError("Email is required");
            TextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            TextEmail.setError("Please provide valid Email!");
            TextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            TextPassword.setError("Password is required");
            TextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified())
                    {
                        Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this,HomePage.class));
                    }
                   else{
                       user.sendEmailVerification();
                       Toast.makeText(MainActivity.this,"Check your email to verify your account",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Failed to Login", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
