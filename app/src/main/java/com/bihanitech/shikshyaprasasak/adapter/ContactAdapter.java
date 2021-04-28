package com.bihanitech.shikshyaprasasak.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.itemModels.ContactsItem;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.contactActivity.ContactView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {


    private final List<ContactsItem> contactsItemList;
    private final ContactView contactView;

    public ContactAdapter(List<ContactsItem> contactsItemList, ContactView contactView) {
        this.contactsItemList = contactsItemList;
        this.contactView = contactView;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);

        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, final int position) {
        holder.tvName.setText(contactsItemList.get(position).getName());
        holder.tvContact.setText((contactsItemList.get(position).getContacts()==null)?"N / A":contactsItemList.get(position).getContacts());
        holder.cvContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactView.proceedToCall(contactsItemList.get(position).getContacts());
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactsItemList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvName)
        TextView tvName;

        @BindView(R.id.tvContact)
        TextView tvContact;

        @BindView(R.id.cvContacts)
        ConstraintLayout cvContacts;

        public ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
