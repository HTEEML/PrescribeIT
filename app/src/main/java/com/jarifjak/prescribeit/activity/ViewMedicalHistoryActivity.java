package com.jarifjak.prescribeit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.jarifjak.prescribeit.R;
import com.jarifjak.prescribeit.adapter.ViewMedicalHistoryAdapter;
import com.jarifjak.prescribeit.database.DatabaseManager;
import com.jarifjak.prescribeit.model.MedicalHistory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewMedicalHistoryActivity extends AppCompatActivity {

    @BindView(R.id.medicalHistoryListView)
    ListView listView;
    @BindView(R.id.emptyListTV)
    AppCompatTextView emptyListTV;

    private DatabaseManager databaseManager;
    private List<MedicalHistory> medicalHistories;
    private ViewMedicalHistoryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medical_history);
        ButterKnife.bind(this);

        initialize();
    }

    public void initialize() {

        new Thread(new Runnable() {

            @Override
            public void run() {

                databaseManager = new DatabaseManager(ViewMedicalHistoryActivity.this);
                medicalHistories = databaseManager.getAllMedicalHistory();

                if (medicalHistories.isEmpty()) {

                    emptyListTV.setText("No medical history found!");
                    emptyListTV.setVisibility(View.VISIBLE);
                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        adapter = new ViewMedicalHistoryAdapter(ViewMedicalHistoryActivity.this, R.layout.medical_history_model, medicalHistories);
                        listView.setAdapter(adapter);
                    }

                });
            }
            
        }).start();
    }
}
