package com.jarifjak.prescribeit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.jarifjak.prescribeit.R;
import com.jarifjak.prescribeit.adapter.ViewDoctorAdapter;
import com.jarifjak.prescribeit.database.DatabaseManager;
import com.jarifjak.prescribeit.model.Doctor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewDoctorActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.doctorListView)
    ListView listView;
    @BindView(R.id.emptyListTV)
    AppCompatTextView emptyListTV;

    private DatabaseManager databaseManager;
    private ViewDoctorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor);
        ButterKnife.bind(this);

        initialize();
    }

    private void initialize() {

        new Thread(new Runnable() {

            @Override
            public void run() {

                databaseManager = new DatabaseManager(ViewDoctorActivity.this);

                List<Doctor> doctors = databaseManager.getAllDoctors();

                if (doctors.isEmpty()) {

                    emptyListTV.setText("No doctor added in the list!");
                    emptyListTV.setVisibility(View.VISIBLE);

                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        adapter = new ViewDoctorAdapter(ViewDoctorActivity.this, R.layout.view_doctor_model, doctors);

                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(ViewDoctorActivity.this);
                    }
                });
            }

        }).start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }
}
