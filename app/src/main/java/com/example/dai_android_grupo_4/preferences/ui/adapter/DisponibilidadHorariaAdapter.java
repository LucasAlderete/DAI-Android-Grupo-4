package com.example.dai_android_grupo_4.preferences.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.preferences.model.DisponibilidadHoraria;

import java.util.ArrayList;
import java.util.List;

public class DisponibilidadHorariaAdapter extends RecyclerView.Adapter<DisponibilidadHorariaAdapter.ViewHolder> {

    private List<DisponibilidadHoraria> items = new ArrayList<>();
    private OnRemoveClickListener onRemoveClickListener;

    public interface OnRemoveClickListener {
        void onRemove(DisponibilidadHoraria disponibilidad);
    }

    public DisponibilidadHorariaAdapter(OnRemoveClickListener listener) {
        this.onRemoveClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preference_chip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DisponibilidadHoraria item = items.get(position);

        String texto = item.getDiaSemana().toSpanish() + " " +
                item.getHoraInicio().substring(0, 5) + " - " +
                item.getHoraFin().substring(0, 5);

        holder.tvName.setText(texto);

        holder.btnRemove.setOnClickListener(v -> {
            if (onRemoveClickListener != null) {
                onRemoveClickListener.onRemove(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<DisponibilidadHoraria> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageButton btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvPreferenceName);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
