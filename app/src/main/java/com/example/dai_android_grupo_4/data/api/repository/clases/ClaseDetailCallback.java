package com.example.dai_android_grupo_4.data.api.repository.clases;

import com.example.dai_android_grupo_4.model.ClaseDetail;

public interface ClaseDetailCallback {
    void onSuccess(ClaseDetail claseDetail);
    void onError(Throwable throwable);
}
