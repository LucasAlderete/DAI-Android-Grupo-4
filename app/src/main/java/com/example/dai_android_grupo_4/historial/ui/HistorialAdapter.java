package com.example.dai_android_grupo_4.historial.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.historial.model.AsistenciaResponse;

import java.util.ArrayList;
import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {

    private List<AsistenciaResponse> asistencias = new ArrayList<>();

    public void setAsistencias(List<AsistenciaResponse> asistencias) {
        this.asistencias = asistencias;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historial, parent, false);
        return new HistorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialViewHolder holder, int position) {
        AsistenciaResponse asistencia = asistencias.get(position);
        holder.bind(asistencia);
    }

    @Override
    public int getItemCount() {
        return asistencias.size();
    }

    static class HistorialViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvClaseNombre;
        private final TextView tvFechaAsistencia;
        private final TextView tvCalificacion;
        private final TextView tvDuracion;

        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClaseNombre = itemView.findViewById(R.id.tv_clase_nombre);
            tvFechaAsistencia = itemView.findViewById(R.id.tv_fecha_asistencia);
            tvCalificacion = itemView.findViewById(R.id.tv_calificacion);
            tvDuracion = itemView.findViewById(R.id.tv_duracion);
        }

        public void bind(AsistenciaResponse asistencia) {
            tvClaseNombre.setText(asistencia.getClase().getNombre());
            tvFechaAsistencia.setText("Fecha: " + formatDate(asistencia.getFechaAsistencia()));
            tvDuracion.setText("Duración: " + asistencia.getDuracionMinutos() + " min");

            if (asistencia.getCalificacion() != null) {
                tvCalificacion.setText("⭐ " + asistencia.getCalificacion() + "/5");
            } else {
                tvCalificacion.setText("Sin calificar");
            }
        }

        private String formatDate(String dateString) {
            // Simplificado - solo mostrar fecha sin hora
            if (dateString != null && dateString.length() >= 10) {
                return dateString.substring(0, 10);
            }
            return dateString;
        }
    }
}