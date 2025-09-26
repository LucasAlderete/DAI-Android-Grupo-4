package com.example.dai_android_grupo_4.booking.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.booking.model.Booking;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookings;
    private OnBookingClickListener listener;

    public interface OnBookingClickListener {
        void onBookingClick(Booking booking);
        void onCancelBooking(Booking booking);
    }

    public BookingAdapter(List<Booking> bookings, OnBookingClickListener listener) {
        this.bookings = bookings;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookings.get(position);
        holder.bind(booking, listener);
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public void updateBookings(List<Booking> newBookings) {
        this.bookings = newBookings;
        notifyDataSetChanged();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        
        private TextView tvClassName, tvInstructor, tvDate, tvTime, tvStatus;
        private ImageView ivStatusIcon;
        private View btnCancel;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClassName = itemView.findViewById(R.id.tv_class_name);
            tvInstructor = itemView.findViewById(R.id.tv_instructor);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvStatus = itemView.findViewById(R.id.tv_status);
            ivStatusIcon = itemView.findViewById(R.id.iv_status_icon);
            btnCancel = itemView.findViewById(R.id.btn_cancel);
        }

        public void bind(Booking booking, OnBookingClickListener listener) {
            tvClassName.setText(booking.getClassName());
            tvInstructor.setText(booking.getInstructor());
            tvDate.setText(booking.getDate());
            tvTime.setText(booking.getTime());
            tvStatus.setText(booking.getStatus());

            // Configurar icono y color según el estado
            setupStatusUI(booking);

            // Configurar click listeners
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onBookingClick(booking);
                }
            });

            btnCancel.setOnClickListener(v -> {
                if (listener != null && booking.canBeCanceled()) {
                    listener.onCancelBooking(booking);
                }
            });

            // Mostrar/ocultar botón de cancelar según el estado
            btnCancel.setVisibility(booking.canBeCanceled() ? View.VISIBLE : View.GONE);
        }

        private void setupStatusUI(Booking booking) {
            int statusColor;
            int statusIcon;

            switch (booking.getStatus()) {
                case "CONFIRMED":
                    statusColor = itemView.getContext().getResources().getColor(R.color.status_confirmed);
                    statusIcon = R.drawable.ic_check_circle;
                    break;
                case "CANCELED":
                    statusColor = itemView.getContext().getResources().getColor(R.color.status_canceled);
                    statusIcon = R.drawable.ic_cancel;
                    break;
                case "COMPLETED":
                    statusColor = itemView.getContext().getResources().getColor(R.color.status_completed);
                    statusIcon = R.drawable.ic_check_circle;
                    break;
                case "EXPIRED":
                    statusColor = itemView.getContext().getResources().getColor(R.color.status_expired);
                    statusIcon = R.drawable.ic_expired;
                    break;
                default:
                    statusColor = itemView.getContext().getResources().getColor(R.color.status_default);
                    statusIcon = R.drawable.ic_info;
            }

            tvStatus.setTextColor(statusColor);
            ivStatusIcon.setImageResource(statusIcon);
            ivStatusIcon.setColorFilter(statusColor);
        }
    }
}
