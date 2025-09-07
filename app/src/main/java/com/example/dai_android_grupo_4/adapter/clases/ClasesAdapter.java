package com.example.dai_android_grupo_4.adapter.clases;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.model.Clase;

import java.util.ArrayList;
import java.util.List;

public class ClasesAdapter extends RecyclerView.Adapter<ClasesAdapter.ClaseViewHolder> {

    private List<Clase> clases = new ArrayList<>();

    @NonNull
    @Override
    public ClaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clase_item, parent, false);
        return new ClaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaseViewHolder holder, int position) {
        Clase clase = clases.get(position);
        holder.tvDisciplina.setText(clase.getDisciplina().getNombre());
        holder.tvFechaHora.setText(clase.getFechaHora()); // Mejorar formato de fecha más adelante
        holder.tvInstructor.setText("Instructor: " + clase.getNombreInstructor());
        holder.tvSede.setText("Sede: " + clase.getSede().getNombre());
    }

    @Override
    public int getItemCount() {
        return clases.size();
    }

    public void setClases(List<Clase> nuevasClases) {
        this.clases = nuevasClases;
        notifyDataSetChanged(); // Notifica al RecyclerView que los datos han cambiado
    }

    static class ClaseViewHolder extends RecyclerView.ViewHolder {
        TextView tvDisciplina;
        TextView tvFechaHora;
        TextView tvInstructor;
        TextView tvSede;

        public ClaseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDisciplina = itemView.findViewById(R.id.tvDisciplina);
            tvFechaHora = itemView.findViewById(R.id.tvFechaHora);
            tvInstructor = itemView.findViewById(R.id.tvInstructor);
            tvSede = itemView.findViewById(R.id.tvSede);
        }
    }
}
