package com.example.shreya.birthdaywishes;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity  {



    private int REQ_CODE_GALLERY=1000;

    ImageView galleryimage;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         galleryimage=(ImageView)findViewById(R.id.galleryimage);

        //DATA FROM INCOMING INTENT FROM OTHER APP
        Uri uri=getIntent().getData();
        if(uri!=null){
            //display toast or do something
            //everytime your app gets started from a link in website..a uri gets stored in intent
        }

    }
    public void link(View view){

        //goto a WEBSITE
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri=Uri.parse("https://www.facebook.com");
        intent.setData(uri);
        startActivity(intent);

       // OR to make it more compact
     /*   Uri uri=Uri.parse("https://www.facebook.com");
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);*/

    }

    public void mapLink(View view){

        //Opening an app instead of website
        Uri uri=Uri.parse("https://maps.google.com/maps?q=22.2765656,114.123456");
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
        //as long as the recieving app recognizes the URI pattern it will be able to handle the intent

    }
    public void mapGeo(View view){

        Uri uri=Uri.parse("geo:22.2765656,114.123456");
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
    //how do we know what URI pattern are registered...
    //google has a page for that called Common Intents

    public void Dial(View view){

        //uri for dial ... tel:phonenumber
        Uri uri=Uri.parse("tel:9090909090");
        Intent intent=new Intent(Intent.ACTION_DIAL,uri);
        startActivity(intent);
    }
    public void PickImage(View view){
        //pick image from gallery
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT); //we need content from gallery of type image
        intent.setType("image/*");
        intent.putExtra("return-data",true);
        startActivityForResult(intent,REQ_CODE_GALLERY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQ_CODE_GALLERY && resultCode== Activity.RESULT_OK){
            imageUri=data.getData(); // it returns image uri

            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                galleryimage.setImageBitmap(bitmap);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void sendText(View view){
            Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,"how u doing");
        intent.setType("text/*");
        startActivity(intent);

    }
    public void sendImage(View view){

        //assuming that our Imageuri has some data
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM,imageUri);
        intent.setType(getContentResolver().getType(imageUri)); //determine what type of image it is-jpeg or png
        startActivity(intent);

    }
    public void sendEmail(View view){

        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc882");
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"recipient@example.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT,"greetings");
        intent.putExtra(Intent.EXTRA_TEXT,"yo yo");

       /* if(intent.resolveActivity(getPackageManager())!=null){
            //app is available to handle our intent
            startActivity(intent);
        }else{
            //error message
        }*/

        //BETTER APPROACH is to wrap our intent inside createChooser

        startActivity(Intent.createChooser(intent,"Title for chooser dialog"));

    }

}
