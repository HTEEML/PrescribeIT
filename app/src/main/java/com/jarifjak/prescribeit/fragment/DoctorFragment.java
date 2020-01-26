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
import com.jarifjak.prescribeit.activity.AddDoctorActivity;
import com.jarifjak.prescribeit.activity.MainActivity;
import com.jarifjak.prescribeit.activity.ViewDoctorActivity;
import com.jarifjak.prescribeit.adapter.DashboardAdapter;
import com.jarifjak.prescribeit.model.DashboardObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoctorFragment extends Fragment implements DashboardAdapter.MyListener {

    @BindView(R.id.doctor_recycler_view)
    RecyclerView recyclerView;

    private Context context;

    private int[] icons =  {R.drawable.ic_doctor_add, R.drawable.ic_doctor, R.drawable.ic_doctor_search};
    private int[] drawbles = {R.drawable.card_box, R.drawable.card_box_purple, R.drawable.card_box_yellow};
    private String[] titles = {"Add Doctor", "View Doctor", "Search Doctor"};


    public DoctorFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doctor, container, false);
        ButterKnife.bind(this, view);

        initialize();

        return view;
    }


    private void initialize() {

        List<DashboardObject> objects = new ArrayList<>();
        DashboardAdapter adapter = new DashboardAdapter(context, objects);

        new Thread(() -> {

            for (int i = 0; i < 3; i++) {

                objects.add(new DashboardObject(icons[i], drawbles[i], titles[i]));
            }

            GridLayoutManager manager = new GridLayoutManager(context, 2);


            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

                @Override
                public int getSpanSize(int position) {

                    return position == 0 ? 2 : 1;

                }
            });

            Objects.requireNonNull(getActivity()).runOnUiThread(() -> {

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

            startActivity(new Intent(getActivity(), AddDoctorActivity.class));

        } else if (position == 1) {

            startActivity(new Intent(getActivity(), ViewDoctorActivity.class));

        }
    }
}
