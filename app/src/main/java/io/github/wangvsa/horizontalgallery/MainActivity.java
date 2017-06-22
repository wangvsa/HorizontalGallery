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
        resIds.add(R.mipmap.ic_launcher);
        resIds.add(R.mipmap.ic_launcher_round);
        resIds.add(R.mipmap.ic_launcher);
        resIds.add(R.mipmap.ic_launcher_round);

        HorizontalGallery gallery = (HorizontalGallery) findViewById(R.id.horizontal_gallery);
        HorizontalGalleryAdapter adapter = new HorizontalGalleryAdapter(this, resIds);
        gallery.initDatas(adapter);

    }
}
