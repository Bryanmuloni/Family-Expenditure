package com.bryanville.familyexpenditure.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bryanville.familyexpenditure.R;
import com.bryanville.familyexpenditure.database.Expenditure;

import java.util.List;

/**
 * Created by Bryanville on 7/4/2018.
 */

public class ExpenditureAdapter extends RecyclerView.Adapter<ExpenditureAdapter.ExpenditureViewHolder> {
    List<Expenditure> expenditureList;
    Context mContext;
    ExpenditureListItemClickListener listItemClickListener;

    public ExpenditureAdapter(Context context, ExpenditureListItemClickListener listener) {
        this.mContext = context;
        this.listItemClickListener = listener;
    }

    public List<Expenditure> getExpenditure() {
        return expenditureList;
    }

    //Interface to handle clicks on RecyclerView Items
    public interface ExpenditureListItemClickListener {
        void onItemClick(int itemId);
    }

    @Override
    public ExpenditureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.expenditure_list_item, parent, false);
        return new ExpenditureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenditureViewHolder holder, int position) {
        Expenditure expenditure = expenditureList.get(position);
        String name = expenditure.getItemName();
        String amount = expenditure.getItemAmount();
        String status = expenditure.getExpenditureStatus();
        String comment = expenditure.getExpenditureComment();


        holder.itemName.setText(name);
        holder.itemAmount.setText(amount);
        holder.expStatus.setText(status);
        holder.expComment.setText(comment);
        int statusColor = 0;

        if (status == "Paid"){
            //getStatusColor(statusColor);
            statusColor = ContextCompat.getColor(mContext, R.color.colorGreen);
        }
        else if (status == "Credit"){
            //getStatusColor(statusColor);
            statusColor = ContextCompat.getColor(mContext, R.color.colorRed);
        }
        //holder.expStatus.setTextColor(statusColor);
    }



    @Override
    public int getItemCount() {
        if (expenditureList == null) return 0;
        return expenditureList.size();
    }

    public void refreshLists(List<Expenditure> list) {
        expenditureList = list;
        notifyDataSetChanged();
    }

    public class ExpenditureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView itemName;
        private TextView itemAmount;
        private TextView expStatus;
        private TextView expComment;

        public ExpenditureViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemAmount = itemView.findViewById(R.id.item_amount);
            expStatus = itemView.findViewById(R.id.exp_status);
            expComment = itemView.findViewById(R.id.exp_comment);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int itemId = expenditureList.get(getAdapterPosition()).getExpenditureId();
            listItemClickListener.onItemClick(itemId);
        }
    }
}
