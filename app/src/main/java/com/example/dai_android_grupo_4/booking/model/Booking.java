package com.example.dai_android_grupo_4.booking.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Booking implements Serializable, Parcelable {
    
    private String id;
    private String className;
    private String instructor;
    private String date;
    private String time;
    private String location;
    private String status; // CONFIRMED, CANCELED, COMPLETED, EXPIRED
    private String duration;
    private int capacity;
    private int currentBookings;
    private String description;
    private Date createdAt;
    private Date updatedAt;

    public Booking() {}

    public Booking(String id, String className, String instructor, String date, 
                   String time, String location, String status) {
        this.id = id;
        this.className = className;
        this.instructor = instructor;
        this.date = date;
        this.time = time;
        this.location = location;
        this.status = status;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getCurrentBookings() { return currentBookings; }
    public void setCurrentBookings(int currentBookings) { this.currentBookings = currentBookings; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    // Métodos de utilidad
    public boolean isConfirmed() {
        return "CONFIRMED".equals(status);
    }

    public boolean isCanceled() {
        return "CANCELED".equals(status);
    }

    public boolean isCompleted() {
        return "COMPLETED".equals(status);
    }

    public boolean isExpired() {
        return "EXPIRED".equals(status);
    }

    public boolean canBeCanceled() {
        return isConfirmed();
    }

    // Parcelable implementation
    protected Booking(Parcel in) {
        id = in.readString();
        className = in.readString();
        instructor = in.readString();
        date = in.readString();
        time = in.readString();
        location = in.readString();
        status = in.readString();
        duration = in.readString();
        capacity = in.readInt();
        currentBookings = in.readInt();
        description = in.readString();
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(className);
        dest.writeString(instructor);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(location);
        dest.writeString(status);
        dest.writeString(duration);
        dest.writeInt(capacity);
        dest.writeInt(currentBookings);
        dest.writeString(description);
    }
}
