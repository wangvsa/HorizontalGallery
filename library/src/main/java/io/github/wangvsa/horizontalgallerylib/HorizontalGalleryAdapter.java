package io.github.wangvsa.horizontalgallerylib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by wangchen on 6/22/17.
 */

public class HorizontalGalleryAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mDatas;        // Uri, as the accceptable uris of Universal Image Loader

    public HorizontalGalleryAdapter(Context context, List<String> mDatas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;

        // Init Image Loader
        HGApplication.initImageLoader(context.getApplicationContext());
    }

    public int getCount()  {
        return mDatas.size();
    }

    public Object getItem(int position) {
        return mDatas.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.horizontal_gallery_item, parent, false);
            viewHolder.mImg = (ImageView) convertView.findViewById(R.id.horizontal_gallery_item_image);
            viewHolder.mText = (TextView) convertView.findViewById(R.id.horizontal_gallery_item_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(position < mDatas.size()) {
            ImageLoader.getInstance().displayImage(mDatas.get(position), viewHolder.mImg);
            viewHolder.mText.setText("some info");
        }

        return convertView;
    }

    private class ViewHolder {
        ImageView mImg;
        TextView mText;
    }




    public void remove(int i) {
        this.mDatas.remove(i);
    }
}
