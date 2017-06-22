package io.github.wangvsa.horizontalgallerylib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wangchen on 6/22/17.
 */

public class HorizontalGalleryAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Integer> mDatas;

    public HorizontalGalleryAdapter(Context context, List<Integer> mDatas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
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
        viewHolder.mImg.setImageResource(mDatas.get(position));
        viewHolder.mText.setText("some info ");

        return convertView;
    }

    private class ViewHolder {
        ImageView mImg;
        TextView mText;
    }
}
