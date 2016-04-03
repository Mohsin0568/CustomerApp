package com.templatexuv.apresh.customerapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.CustomerApplication;
import com.templatexuv.apresh.customerapp.R;
import com.templatexuv.apresh.customerapp.imagelibrary.ImageLoader;
import com.templatexuv.apresh.customerapp.model.Comment;
import com.templatexuv.apresh.customerapp.model.Seller;

import java.util.List;

/**
 * Created by arokalla on 7/10/2015.
 */
public class CommentAdapter extends ArrayAdapter {

    List<Comment> comments;
    public Context context;

    public CommentAdapter(Context context, List<Comment> comments) {
        super(context, 0);
        this.context = context;
        this.comments = comments;
    }

    public static class ProductViewHolder {
        TextView customerName;
        TextView comment;
        RatingBar ratingBar;
        TextView commentDate;
    }

    public void addComments(List<Comment> comments){
        this.comments.addAll(comments);
        notifyDataSetChanged();
    }

    @Override
    public Comment getItem(int position) {
        return this.comments.get(position);
    }

    public List<Comment> getComments(){
        return this.comments;
    }
    @Override
    public int getCount() {
        return this.comments != null ? this.comments.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductViewHolder holder;
        if (convertView == null) {
            holder = new ProductViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_comment,null);
            holder.customerName = (TextView) convertView.findViewById(R.id.customerName);
            holder.comment = (TextView) convertView.findViewById(R.id.comment);
            holder.commentDate = (TextView) convertView.findViewById(R.id.commentDate);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingbar);
            convertView.setTag(holder);
        } else {
            holder = (ProductViewHolder) convertView.getTag();
        }
        Comment comment = getItem(position);
        holder.customerName.setText(""+comment.getCustomerName());
        holder.commentDate.setText(""+comment.getCommentDate());
        holder.comment.setText(""+comment.getComment());
        holder.ratingBar.setRating(comment.getRating());
        return convertView;
    }
}

