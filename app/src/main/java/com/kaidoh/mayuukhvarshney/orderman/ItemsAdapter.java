package com.kaidoh.mayuukhvarshney.orderman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
/**
 * Created by mayuukhvarshney on 31/05/16.
 */
public class ItemsAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private int mItemHeight = 0;
    private int mNumColumns = 0;
    private RelativeLayout.LayoutParams mImageViewLayoutParams;
    private int Len;
    //private int []ICONS=new int[4];
    // private String[] CONTENT=new String[4];
    ArrayList<Integer> ICONS;
    ArrayList<MenuItems> CONTENT;
    protected Context mContext;
    public ItemsAdapter(Context context,ArrayList<Integer> img,ArrayList<MenuItems> txt) {
        mContext=context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageViewLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,mItemHeight);
        this.ICONS=img;
        this.CONTENT=txt;
    }


    public int getCount() {
        return CONTENT.size();
    }

    // set numcols
    public void setNumColumns(int numColumns) {
        mNumColumns = numColumns;
    }

    public int getNumColumns() {
        return mNumColumns;
    }

    // set photo item height
    public void setItemHeight(int height) {
        if (height == mItemHeight) {
            return;
        }
        mItemHeight = height;
        mImageViewLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, mItemHeight);
        notifyDataSetChanged();

    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View view, ViewGroup parent) {

        if (view == null)
            view = mInflater.inflate(R.layout.food_menu_items, null);

        ImageView cover = (ImageView) view.findViewById(R.id.cover);
        TextView title = (TextView) view.findViewById(R.id.foodtitle);
        TextView capcity= (TextView) view.findViewById(R.id.price);

        cover.setLayoutParams(mImageViewLayoutParams);

// Check the height matches our calculated column width
        if (cover.getLayoutParams().height != mItemHeight) {
            cover.setLayoutParams(mImageViewLayoutParams);
        }
        //cover.setImageResource(ICONS[position % ICONS.length]);

        Picasso.with(mContext).load(ICONS.get(position % ICONS.size())).into(cover);
      //  cover.setImageBitmap(ICONS.get(position%ICONS.size()));

        //cover.setImageBitmap(ICONS.get(position%ICONS.size()));
        title.setText(CONTENT.get(position%CONTENT.size()).getItemName());
        capcity.setText(CONTENT.get(position%CONTENT.size()).getPrice().toString());


        return view;
    }
}
