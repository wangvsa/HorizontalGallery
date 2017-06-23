package io.github.wangvsa.horizontalgallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.github.wangvsa.horizontalgallerylib.HorizontalGallery;
import io.github.wangvsa.horizontalgallerylib.HorizontalGalleryAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 1001;
    private File photoFile = null;

    private HorizontalGallery gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<String> resIds = new ArrayList<>();
        resIds.add("drawable://"+R.drawable.pic1);
        resIds.add("drawable://"+R.drawable.pic2);
        resIds.add("drawable://"+R.drawable.pic3);
        resIds.add("drawable://"+R.drawable.pic4);
        resIds.add("drawable://"+R.drawable.pic5);
        resIds.add("drawable://"+R.drawable.pic6);
        resIds.add("drawable://"+R.drawable.pic7);
        resIds.add("drawable://"+R.drawable.pic8);
        resIds.add("drawable://"+R.drawable.pic9);


        gallery = (HorizontalGallery) findViewById(R.id.horizontal_gallery);
        HorizontalGalleryAdapter adapter = new HorizontalGalleryAdapter(this, resIds);
        gallery.setHorizontalGalleryAdapter(adapter);


        View cameraButton = findViewById(R.id.horizontal_gallery_sample_camera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            String uri = Uri.fromFile(photoFile).toString();
            gallery.addImage(uri);
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "io.github.wangvsa.horizontalgallery.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
}
