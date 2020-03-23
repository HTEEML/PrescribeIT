package com.jarifjak.prescribeit.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.jarifjak.prescribeit.R;
import com.jarifjak.prescribeit.model.MedicalHistory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class ViewMedicalHistoryAdapter extends ArrayAdapter<MedicalHistory> {

    private Context context;
    private int resource;
    private List<MedicalHistory> medicalHistories;


    public ViewMedicalHistoryAdapter(@NonNull Context context, int resource, @NonNull List<MedicalHistory> medicalHistories) {

        super(context, resource, medicalHistories);

        this.context = context;
        this.resource = resource;
        this.medicalHistories = medicalHistories;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(resource, parent, false);

        }

        AppCompatTextView doctorNameTV = convertView.findViewById(R.id.historyNameView);
        AppCompatTextView detailsTV = convertView.findViewById(R.id.historyDetailsView);
        AppCompatTextView dateTV = convertView.findViewById(R.id.historyDateView);
        AppCompatImageView pictureIV = convertView.findViewById(R.id.historyImageView);

        doctorNameTV.setText(medicalHistories.get(position).getDoctorName());
        detailsTV.setText(medicalHistories.get(position).getDetails());
        dateTV.setText(medicalHistories.get(position).getDate());
        pictureIV.setImageBitmap(BitmapFactory.decodeFile(medicalHistories.get(position).getImagePath()));

        return convertView;
    }

    private Bitmap getFile(String filePath) {

        File file = new File(getContext().getExternalFilesDir(null).getAbsolutePath(), filePath);

        try {

            FileInputStream fileInputStream = new FileInputStream(file);
            //Bitmap bitmap = BitmapFactory.decodeFile(fileInputStream);

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }

        return null;
    }
}
