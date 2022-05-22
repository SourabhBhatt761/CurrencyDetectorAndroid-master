package org.tensorflow.lite.examples.classification;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity {
    private Button camera;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String UserID;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView nav=(NavigationView)findViewById(R.id.navmenu);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener((new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        }));


        camera= findViewById(R.id.camera_button);
         camera.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(HomePage.this,ClassifierActivity.class));
             }
         });
         user = FirebaseAuth.getInstance().getCurrentUser();
         reference = FirebaseDatabase.getInstance().getReference("Users");
         UserID = user.getUid();

         final TextView fullNameTextView = (TextView)findViewById(R.id.username);
         final TextView emailTextView = (TextView)findViewById(R.id.useraddress);
         final TextView ageTextView = (TextView)findViewById(R.id.userage);

         reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 UserProfile userProfile = snapshot.getValue(UserProfile.class);

                 if(userProfile != null){
                     String fullName = userProfile.fullname;
                     String fullemail = userProfile.fullemail;
                     String fullage = userProfile.fullage;

                     fullNameTextView.setText(fullName);
                     emailTextView.setText(fullemail);
                     ageTextView.setText(fullage);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
                 Toast.makeText(HomePage.this,"Something wrong happend!",Toast.LENGTH_LONG).show();

             }
         });

    }


}
