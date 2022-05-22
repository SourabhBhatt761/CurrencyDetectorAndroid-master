package org.tensorflow.lite.examples.classification;


import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailtext;
    private Button resetbutton;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_forgot_password);

        emailtext = (EditText)findViewById(R.id.forgotemail);
        resetbutton =(Button)findViewById(R.id.forgot);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);

        auth = FirebaseAuth.getInstance();
        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });


    }

    private void resetPassword() {
        String email = emailtext.getText().toString().trim();

        if(email.isEmpty()){
            emailtext.setError("Email is required!");
            emailtext.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
           emailtext.setError("Please provide valid Email!");
           emailtext.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this,"Check your email to reset your password!",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ForgotPasswordActivity.this,"Try again! something went wrong!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
