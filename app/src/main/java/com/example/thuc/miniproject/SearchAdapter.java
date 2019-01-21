package com.example.thuc.miniproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class SearchViewHolder extends RecyclerView.ViewHolder {
    public TextView name, addres, mail, website,sdt;
    public View myView;
    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.Name);
        addres = (TextView)itemView.findViewById(R.id.Address);
        mail = (TextView)itemView.findViewById(R.id.Mail);
        website = (TextView)itemView.findViewById(R.id.Website);
        sdt = (TextView)itemView.findViewById(R.id.sdt);
        myView= itemView;
    }
};

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    @NonNull
    private Context context;
    private List<MyPlace> myPlaces;

    public SearchAdapter(Context context, List<MyPlace> myPlaces)
    {
        this.context = context;
        this.myPlaces = myPlaces;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item,parent,false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, final int position) {
        holder.name.setText(myPlaces.get(position).getName());
        holder.addres.setText(myPlaces.get(position).getAddres());
        holder.mail.setText(myPlaces.get(position).getEmail());
        holder.website.setText(myPlaces.get(position).getWebsite());
        holder.sdt.setText(myPlaces.get(position).getSdt());
        holder.myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                ((OnListItemClickListener)context).onListItemClickListener( myPlaces.get(position) );

                }catch (Exception e)
                {
                    return;
                }
            }
        });


    }

        public interface OnListItemClickListener{
            void onListItemClickListener(MyPlace myPlace);
        }


    @Override
    public int getItemCount() {
        return myPlaces.size();
    }
}
