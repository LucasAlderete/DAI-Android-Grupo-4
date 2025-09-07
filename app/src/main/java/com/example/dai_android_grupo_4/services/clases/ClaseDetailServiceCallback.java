package com.example.dai_android_grupo_4.services.clases;

import com.example.dai_android_grupo_4.model.ClaseDetail;

public interface ClaseDetailServiceCallback {
    void onSuccess(ClaseDetail claseDetail);
    void onError(String message);
}
