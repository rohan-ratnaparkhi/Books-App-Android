package com.talentica.bookshelf.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.talentica.bookshelf.R;
import com.talentica.bookshelf.model.Book;

import java.util.ArrayList;

/**
 * Created by rohanr on 8/24/16.
 */
public class BooksGridAdapter extends BaseAdapter {
    ArrayList<Book> books;
    Context context;

    public BooksGridAdapter(ArrayList<Book> books, Context context) {
        this.books = books;
        this.context = context;
    }

    class ViewHolder {
//        TODO - all fields that are necessary
        ImageView bookImg;
        TextView title;
        TextView author;
        TextView lender;

        ViewHolder(View v){
            bookImg = (ImageView) v.findViewById(R.id.home_book_img);
            title = (TextView) v.findViewById(R.id.home_book_title);
            author = (TextView) v.findViewById(R.id.home_book_author);
            lender = (TextView) v.findViewById(R.id.home_book_lender);
        }
    }

    @Override
    public int getCount() {
        return this.books.size();
    }

    @Override
    public Object getItem(int position) {
        return this.books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.books.get(position).getBookId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_book, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Picasso.with(context).load(this.books.get(position).getBookImageUrl()).into(holder.bookImg);
        holder.title.setText(this.books.get(position).getBookName());
        holder.author.setText(this.books.get(position).getAuthorName());
        holder.lender.setText("Lender " + this.books.get(position).getLenderName());
//        holder.bookImg.setAdjustViewBounds(true);

        return row;
    }
}
