package com.example.locationsaver;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditAddressDialog extends AppCompatDialogFragment {

    private EditAddressDialogListener listener;
    private EditText newAddressEditText;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //Dialog creation
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.listview_edit, null);
        //Dialog setup
        builder.setView(view)
                .setTitle("Edit address")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.deleteText(true);
            }
        })      .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newAddress = newAddressEditText.getText().toString();
                listener.applyText(newAddress);
            }
        });

        newAddressEditText = view.findViewById(R.id.setTextEditText);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (EditAddressDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " Must implement EditAddressDialogListener");
        }
    }

    public interface EditAddressDialogListener{
        void applyText(String address);
        void deleteText(boolean delete);
    }

}
