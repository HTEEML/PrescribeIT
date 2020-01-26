package com.jarifjak.prescribeit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.jarifjak.prescribeit.R;
import com.jarifjak.prescribeit.model.Doctor;

import java.util.List;

public class ViewDoctorAdapter extends ArrayAdapter<Doctor> {

    private final Context context;
    private int resource;
    private List<Doctor> doctors;


    public ViewDoctorAdapter(@NonNull Context context, int resource, @NonNull List<Doctor> doctors) {

        super(context, resource, doctors);

        this.context = context;
        this.resource = resource;
        this.doctors = doctors;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        AppCompatTextView nameTV = convertView.findViewById(R.id.nameTV);
        AppCompatTextView numberTV = convertView.findViewById(R.id.numberTV);
        AppCompatTextView detailsTV = convertView.findViewById(R.id.detailsTV);

        nameTV.setText(doctors.get(position).getFirstName() + " " + doctors.get(position).getLastName());
        numberTV.setText(doctors.get(position).getNumber());
        detailsTV.setText(doctors.get(position).getDetails());

        return convertView;
    }
}
