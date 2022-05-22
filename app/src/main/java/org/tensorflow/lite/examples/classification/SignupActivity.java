package org.tensorflow.lite.examples.classification;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView already,register;
    private EditText nameid,ageid,emailid,passwordid;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

      already = (TextView)findViewById(R.id.sglogin) ;
        already.setOnClickListener(this);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        nameid = (EditText)findViewById(R.id.rgname);
        ageid = (EditText)findViewById(R.id.rgage);
        emailid = (EditText)findViewById(R.id.sgemail);
        passwordid = (EditText)findViewById(R.id.sgpass);



    }

    @Override
    public void onClick(View v) {

        switch ( v.getId()){
            case R.id.sglogin:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case  R.id.register:
                sglogin();
                break;
        }

    }

    private void sglogin() {
        String email = emailid.getText().toString().trim();
        String password = passwordid.getText().toString().trim();
        String age = ageid.getText().toString().trim();
        String name = nameid.getText().toString().trim();


        if(age.isEmpty()) {
            ageid.setError("Age is required");
            ageid.requestFocus();
            return;
        }
        if(name.isEmpty()){
            nameid.setError("Full name is required");
            nameid.requestFocus();
            return;
        }

        if(email.isEmpty()){
            emailid.setError("Email is required");
            emailid.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailid.setError("Please provide valid Email!");
            emailid.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordid.setError("Password is required");
            passwordid.requestFocus();
            return;
        }
        if(password.length()<6){
            passwordid.setError("Min password length be 6 character");
            passwordid.requestFocus();
            return;
        }

       mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful())
               {
                   UserProfile userProfile = new UserProfile(name,age,email);

                   FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                           .setValue(userProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {

                           if(task.isSuccessful()){
                               Toast.makeText(SignupActivity.this, "User has been registered successfully", Toast.LENGTH_LONG).show();

                           }
                           else
                           {
                               Toast.makeText(SignupActivity.this,"Failed to register",Toast.LENGTH_LONG).show();
                           }
                       }
                   });
               }else{
                   Toast.makeText(SignupActivity.this,"Failed to register",Toast.LENGTH_LONG).show();
               }
           }
       });
}
}
