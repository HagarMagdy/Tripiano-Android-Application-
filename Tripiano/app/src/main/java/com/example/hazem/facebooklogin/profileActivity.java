package com.example.hazem.facebooklogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hazem.facebooklogin.views.HomeScreen;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profileActivity extends AppCompatActivity {

    private ImageView profilePic;
    private TextView profileName;
    private TextView profileEmail;
    private TextView profilePhone;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private MediaPlayer mp;
    private FirebaseAuth mAuth;
    private Button test;
    private Button alarm,signOut,login_button,main_Trips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mp = MediaPlayer.create(this, R.raw.loudalarm);
        mp.setLooping(false);
        setContentView(R.layout.activity_profile);
        profilePic = findViewById(R.id.imageView);
        profileName = findViewById(R.id.name);
        profileEmail = findViewById(R.id.email);
        profilePhone = findViewById(R.id.phone);
        signOut = findViewById(R.id.signout);
        Intent intent = getIntent();
        if(intent.getBooleanExtra("facebook",false)){
            signOut.setVisibility(View.GONE);
        }else{
            login_button.setVisibility(View.GONE);
        }


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        pref.getString("user_id","0");
        pref.getString("user_name","user");
        pref.getString("user_email",".com");
        pref.getString("user_photo_uri","");

        profileName.setText(pref.getString("user_name","user"));
        profileEmail.setText(pref.getString("user_email",".com"));
        profilePhone.setText(pref.getString("user_id","0"));
        Glide.with(profileActivity.this).load(pref.getString("user_photo_uri","")).into(profilePic);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testDB = new Intent(profileActivity.this,DataBaseActivity.class);
                startActivity(testDB);
            }
        });

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alarm = new Intent(profileActivity.this,AlarmActivity.class);
                startActivity(alarm);
            }
        });
        main_Trips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_Trips = new Intent(profileActivity.this,HomeScreen.class);
                startActivity(main_Trips);
            }
        });

    }

    public void signOut(View v){
        LoginManager.getInstance().logOut();
        editor.putBoolean("logged_in",false);
        editor.commit();
        Intent mainactivity = new Intent(profileActivity.this,MainActivity.class);
        startActivity(mainactivity);
    }
}
