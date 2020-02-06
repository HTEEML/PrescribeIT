package com.jarifjak.prescribeit.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jarifjak.prescribeit.R;
import com.jarifjak.prescribeit.activity.AddMedicalFileActivity;
import com.jarifjak.prescribeit.adapter.DashboardAdapter;
import com.jarifjak.prescribeit.model.DashboardObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MedicalHistoryFragment extends Fragment implements DashboardAdapter.MyListener {


    @BindView(R.id.medical_file_recycler_view)
    RecyclerView recyclerView;

    private Context context;

    private int[] icons =  {R.drawable.ic_add_medical_files, R.drawable.ic_view_medical_file};
    private int[] drawbles = {R.drawable.card_box, R.drawable.card_box_purple};
    private String[] titles = {"Add Medical File", "View Medical History"};

    public MedicalHistoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_medical_history, container, false);
        ButterKnife.bind(this, view);

        initialize();

        return view;
    }

    public void initialize() {

        List<DashboardObject> objects = new ArrayList<>();
        DashboardAdapter adapter = new DashboardAdapter(context, objects);

        new Thread(() -> {

            for (int i = 0; i < 2; i++) {

                objects.add(new DashboardObject(icons[i], drawbles[i], titles[i]));
            }

            GridLayoutManager manager = new GridLayoutManager(context, 2);

            getActivity().runOnUiThread(() -> {

                adapter.setOnCardListener(this);
                recyclerView.setLayoutManager(manager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);

            });

        }).start();

    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCardClick(int position) {

        if (position == 0) {

            startActivity(new Intent(getActivity(), AddMedicalFileActivity.class));
        }
    }
}
