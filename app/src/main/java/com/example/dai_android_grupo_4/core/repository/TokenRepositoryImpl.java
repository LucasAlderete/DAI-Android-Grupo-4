package com.example.dai_android_grupo_4.core.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.dai_android_grupo_4.data.api.AuthApiService;
import com.example.dai_android_grupo_4.data.api.model.TokenValidationResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.security.GeneralSecurityException;
import retrofit2.Call;

@Singleton
public class TokenRepositoryImpl implements TokenRepository {
    
    private static final String TAG = "TokenRepositoryImpl";
    private static final String SHARED_PREFS_FILE_NAME = "auth_encrypted_prefs";
    private static final String KEY_TOKEN = "auth_token";
    
    private final SharedPreferences encryptedSharedPreferences;
    private final AuthApiService authApiService;

    @Inject
    public TokenRepositoryImpl(Context context, AuthApiService authApiService) {
        this.encryptedSharedPreferences = createEncryptedSharedPreferences(context);
        this.authApiService = authApiService;
    }
    
    private SharedPreferences createEncryptedSharedPreferences(Context context) {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
            
            return EncryptedSharedPreferences.create(
                    context,
                    SHARED_PREFS_FILE_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            Log.e(TAG, "Error al crear EncryptedSharedPreferences", e);
            return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE);
        }
    }
    
    @Override
    public void saveToken(String token) {
        encryptedSharedPreferences.edit().putString(KEY_TOKEN, token).apply();
    }
    
    @Override
    public String getToken() {
        return encryptedSharedPreferences.getString(KEY_TOKEN, null);
    }
    
    @Override
    public void clearToken() {
        encryptedSharedPreferences.edit().remove(KEY_TOKEN).apply();
    }
    
    @Override
    public boolean hasToken() {
        return encryptedSharedPreferences.contains(KEY_TOKEN);
    }

    @Override
    public Call<TokenValidationResponse> validateToken() {
        String token = getToken();
        if (token == null) {
            return null;
        }
        return authApiService.validateToken("Bearer " + token);
    }
}
