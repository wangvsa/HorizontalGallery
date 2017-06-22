package io.github.wangvsa.horizontalgallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import io.github.wangvsa.horizontalgallerylib.HorizontalGallery;
import io.github.wangvsa.horizontalgallerylib.HorizontalGalleryAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<Integer> resIds = new ArrayList<>();
        resIds.add(R.drawable.pic1);
        resIds.add(R.drawable.pic2);
        resIds.add(R.drawable.pic3);
        resIds.add(R.drawable.pic4);
        resIds.add(R.drawable.pic5);
        resIds.add(R.drawable.pic6);
        resIds.add(R.drawable.pic7);
        resIds.add(R.drawable.pic8);
        resIds.add(R.drawable.pic9);


        HorizontalGallery gallery = (HorizontalGallery) findViewById(R.id.horizontal_gallery);
        HorizontalGalleryAdapter adapter = new HorizontalGalleryAdapter(this, resIds);
        gallery.initDatas(adapter);

    }
}
