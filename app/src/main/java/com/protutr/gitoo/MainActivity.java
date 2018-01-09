package com.protutr.gitoo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button option1,option2,option3,hint;
    ImageView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        option1=(Button)findViewById(R.id.option1);
        option2=(Button)findViewById(R.id.option2);
        option3=(Button)findViewById(R.id.option3);
        navigation=(ImageView)findViewById(R.id.image_navigation);
        hint=(Button)findViewById(R.id.hint);
         navigation.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent=new Intent(MainActivity.this,Leaderboard.class);
                 startActivity(intent);
             }
         });
        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Vibrator v=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(80);
        if(view.getId()==R.id.option2){
            correctAnswer();
        }
        else {
            wrongAnswer();
        }
    }

    private void wrongAnswer() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.wrong, null);
        builder.setView(dialogView).create();

        final AlertDialog alert = builder.show();
        Button tryagain = (Button) dialogView.findViewById(R.id.try_again);
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
                Toast.makeText(MainActivity.this, "Starting Quiz Again", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void correctAnswer() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.correct, null);
        builder.setView(dialogView).create();
        final AlertDialog alert = builder.show();
        Button cancel = (Button) dialogView.findViewById(R.id.btn_cancel);
        Button share = (Button) dialogView.findViewById(R.id.btn_share);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
                sendWhatsappdata();
                Toast.makeText(MainActivity.this, "select contact on whatsapp", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendWhatsappdata() {
        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "I Scored 100 pts on this Gitoo App";

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
