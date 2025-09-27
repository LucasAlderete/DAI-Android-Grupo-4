package com.example.dai_android_grupo_4.lessons.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dai_android_grupo_4.booking.model.Booking;
import com.example.dai_android_grupo_4.booking.repository.BookingRepository;
import com.example.dai_android_grupo_4.lessons.model.Lesson;
import com.example.dai_android_grupo_4.lessons.repository.LessonRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LessonDetailViewModel extends ViewModel {

    private final LessonRepository lessonRepository;
    private final BookingRepository bookingRepository;

    private final MutableLiveData<Lesson> lessonDetail = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Booking> bookingResult = new MutableLiveData<>();
    private final MutableLiveData<String> bookingError = new MutableLiveData<>();

    @Inject
    public LessonDetailViewModel(LessonRepository lessonRepository, BookingRepository bookingRepository) {
        this.lessonRepository = lessonRepository;
        this.bookingRepository = bookingRepository;
    }

    public LiveData<Lesson> getLessonDetail() {
        return lessonDetail;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Booking> getBookingResult() {
        return bookingResult;
    }

    public LiveData<String> getBookingError() {
        return bookingError;
    }

    public void fetchLessonDetails(Long lessonId) {
        isLoading.setValue(true);
        lessonRepository.getLessonById(lessonId, new LessonRepository.LessonDetailCallback() {
            @Override
            public void onSuccess(Lesson lesson) {
                lessonDetail.setValue(lesson);
                isLoading.setValue(false);
            }

            @Override
            public void onError(Throwable throwable) {
                error.setValue("Error fetching lesson details: " + throwable.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    public void createBooking(long claseId, String token) {
        isLoading.setValue(true);
        bookingRepository.createBooking(claseId, token, new BookingRepository.SingleBookingCallback() {
            @Override
            public void onSuccess(Booking booking) {
                bookingResult.setValue(booking);
                isLoading.setValue(false);
            }

            @Override
            public void onError(String error) {
                bookingError.setValue(error);
                isLoading.setValue(false);
            }
        });
    }
}
