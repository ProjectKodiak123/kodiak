package com.bhanuka.kodiak;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import de.hdodenhof.circleimageview.CircleImageView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;


public class Home extends AppCompatActivity implements View.OnClickListener{

    ViewFlipper viewFlipper;
    CircleImageView category;
    CircleImageView subscrideb;
    CircleImageView popularBarnds;
    CircleImageView huntMe;
    CircleImageView viewOnMap;
    CircleImageView visitWebSite;

    Intent intent;

    private int LOCATION_ACCESS_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        advertiserImageSlider();
        setBtnOnClickListner();

    }

    public void setBtnOnClickListner(){
        category = (CircleImageView)findViewById(R.id.categoryBtn);
        subscrideb = (CircleImageView)findViewById(R.id.subscribedBtn);
        popularBarnds = (CircleImageView)findViewById(R.id.popularBrandsBtn);
        huntMe = (CircleImageView)findViewById(R.id.huntMeBtn);
        viewOnMap = (CircleImageView)findViewById(R.id.viewOnMapBtn);
        visitWebSite = (CircleImageView)findViewById(R.id.visitWebsiteBtn);

        category.setOnClickListener(this);
        subscrideb.setOnClickListener(this);
        popularBarnds.setOnClickListener(this);
        huntMe.setOnClickListener(this);
        viewOnMap.setOnClickListener(this);
        visitWebSite.setOnClickListener(this);

    }

    public void advertiserImageSlider(){
        int image[] = {R.drawable.abans,R.drawable.kfc,R.drawable.dominos,R.drawable.gflock};

        viewFlipper=(ViewFlipper)findViewById(R.id.flipper);

        for(int i=0;i<image.length;++i){
            flipperImages(image[i]);
        }
    }

    public void flipperImages(int image){
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this , android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    public void onClick(View view){
        int id=view.getId();
        switch (id){
            case R.id.categoryBtn:
                Toast.makeText(this,"category",Toast.LENGTH_SHORT).show();
                break;
            case R.id.subscribedBtn:
                Toast.makeText(this,"subscribed",Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewOnMapBtn:
                //startMapActivity();
                intent = new Intent(this,MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.popularBrandsBtn:
                intent=new Intent(this,PopularBrands.class);
                startActivity(intent);
                break;
            case R.id.huntMeBtn:
                Toast.makeText(this,"hunt me",Toast.LENGTH_SHORT).show();
                break;
            case R.id.visitWebsiteBtn:
                Toast.makeText(this,"visit web site",Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public void startMapActivity(){
        Toast.makeText(this,"Inside start activity",Toast.LENGTH_SHORT).show();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            //If permission granted
            Toast.makeText(this,"Inside start activity",Toast.LENGTH_SHORT).show();
            intent = new Intent(this,MapsActivity.class);
            startActivity(intent);
        }
        else{
            //If permission not granted
            requestAccessPerminsion();
        }


    }

    private void requestAccessPerminsion() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            //shows dialog to user why we need this permission
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Permission needed to track and guid you to closest location on google map")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Home.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},LOCATION_ACCESS_PERMISSION );
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        else{
            //Request permission
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_ACCESS_PERMISSION );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //use to check whether the permission is granted or not
        if(requestCode == LOCATION_ACCESS_PERMISSION){
            //was granted or not
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission granted",Toast.LENGTH_SHORT).show();
                intent = new Intent(this,MapsActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this,"Permission not granted",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to close Offer Hunt");

        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(1);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.show();
    }
}
