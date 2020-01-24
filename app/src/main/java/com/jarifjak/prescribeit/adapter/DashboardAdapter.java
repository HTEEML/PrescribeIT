package com.jarifjak.prescribeit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jarifjak.prescribeit.R;
import com.jarifjak.prescribeit.model.DashboardObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private Context context;
    private List<DashboardObject> objects;
    private MyListener listener;

    public DashboardAdapter(Context context, List<DashboardObject> objects) {

        this.context = context;
        this.objects = objects;
    }


    public interface MyListener{
        void onCardClick(int position);
    }

    public void setOnCardListener(MyListener listener) {

        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.cardIcon.setImageResource(objects.get(position).getObjectIcon());
        holder.cardTitle.setText(objects.get(position).getObjectTitle());
        holder.cardRoot.setBackground(context.getResources().getDrawable(objects.get(position).getObjectBackground()));

    }

    @Override
    public int getItemCount() {

        return objects == null ? 0 : objects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_icon)
        AppCompatImageView cardIcon;
        @BindView(R.id.card_title)
        AppCompatTextView cardTitle;
        @BindView(R.id.card_bottom)
        LinearLayout cardBottom;
        @BindView(R.id.card_root)
        RelativeLayout cardRoot;

        ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.card_root)
        public void onViewClicked() {

            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {

                listener.onCardClick(position);
            }

        }
    }
}
