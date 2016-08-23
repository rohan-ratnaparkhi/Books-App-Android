package com.talentica.bookshelf.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.talentica.bookshelf.BookDetailsActivity;
import com.talentica.bookshelf.R;
import com.talentica.bookshelf.model.Book;

import java.util.ArrayList;

/**
 * Created by rohanr on 8/23/16.
 */
public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.MyViewHolder> {

    Context c;
    ArrayList<Book> bookList;


    public HomeListAdapter(Context c, ArrayList<Book> bookList){
        this.c = c;
        this.bookList = bookList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.title.setText(book.getBookName());
        holder.author.setText(book.getAuthorName());
        holder.lender.setText("Lender " + book.getLenderName());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        TextView title, author, lender;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.home_list_img);
            title = (TextView) itemView.findViewById(R.id.home_list_title);
            author = (TextView) itemView.findViewById(R.id.home_list_author);
            lender = (TextView) itemView.findViewById(R.id.home_list_lender);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Intent intent = new Intent(c, BookDetailsActivity.class);
            intent.putExtra("bookId", bookList.get(pos).getBookId());
            c.startActivity(intent);
        }
    }
}
