package com.example.roomdb_mvvm

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class BookAdapter(val context: Context) : RecyclerView.Adapter<BookAdapter.MyViewHolder>() {
    private var bookList = emptyList<Book>()

    fun setData(bookList : List<Book>)
    {
        this.bookList=bookList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        private lateinit var clickedItem : Book
        init {
            itemView.setOnClickListener()
            {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    clickedItem = bookList[position]
                }
                val intent = Intent(context,UpdateBookActivity::class.java)
                intent.putExtra("title",clickedItem.bookTitle)
                intent.putExtra("author",clickedItem.authorName)
                intent.putExtra("pgno",clickedItem.nofPages)
                intent.putExtra("id",clickedItem.bookId)
                context.startActivity(intent)



            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view,parent,false))
    }

    override fun getItemCount(): Int {
        return bookList.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = bookList[position]
        holder.itemView.findViewById<TextView>(R.id.idView).text=currentItem.bookId.toString()
        holder.itemView.findViewById<TextView>(R.id.bookTitleView).text=currentItem.bookTitle
        holder.itemView.findViewById<TextView>(R.id.authorTextView).text=currentItem.authorName
        holder.itemView.findViewById<TextView>(R.id.pgTextView).text=currentItem.nofPages

        /*holder.itemView.findViewById<RecyclerView>(R.bookId.bookListView).setOnClickListener()
        {
            val intent = Intent(context,UpdateBookActivity::class.java)
            context.startActivity(intent)
        }*/
    }
}