package com.example.mohamed.movies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mohamed on 10/2/2015.
 */
public class GridViewAdapter extends ArrayAdapter<Movie> {

    private Context mContext;
    private ArrayList<Movie> mGridData = new ArrayList<Movie>();
    private  int layoutResourceId;



    //The GridViewAdapter class constructor requires the id of the grid item layout and the list of data to operate on.
    public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<Movie> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;

    }
    //method updates the data display on GridView.
    public void setGridData(ArrayList<Movie> mGridData){
        this.mGridData=mGridData;
        notifyDataSetChanged();

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            //holder.titleTextView = (TextView) row.findViewById(R.id.grid_item_title);
            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Movie item = mGridData.get(position);
        // holder.titleTextView.setText(Html.fromHtml(item.getTitle()));
// Picasso.with(mContext).load(item.getImage()) method is used to download the image from url and display on image view.
        Picasso.with(mContext).load(item.getImage()).into(holder.imageView);
        return row;
    }

    static class ViewHolder {
        // TextView titleTextView;
        ImageView imageView;
    }
}