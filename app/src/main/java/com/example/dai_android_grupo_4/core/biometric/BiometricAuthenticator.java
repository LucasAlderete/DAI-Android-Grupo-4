package com.example.dai_android_grupo_4.core.biometric;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class BiometricAuthenticator {
    private static final String TAG = "BiometricAuthenticator";
    
    private final Context context;
    private final Executor executor;
    private final BiometricPrompt biometricPrompt;
    private final BiometricPrompt.PromptInfo promptInfo;
    
    public interface AuthenticationCallback {
        void onAuthenticationSuccess();
        void onAuthenticationError(int errorCode, String errorMessage);
    }
    
    public BiometricAuthenticator(AppCompatActivity activity, AuthenticationCallback callback) {
        this.context = activity;
        this.executor = ContextCompat.getMainExecutor(activity);
        
        biometricPrompt = new BiometricPrompt(activity, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.e(TAG, "Authentication error: " + errString);
                callback.onAuthenticationError(errorCode, errString.toString());
            }
            
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Log.d(TAG, "Authentication succeeded!");
                callback.onAuthenticationSuccess();
            }
            
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.e(TAG, "Authentication failed");
            }
        });
        
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación")
                .setSubtitle("Inicie sesión usando su huella digital o credencial del dispositivo")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG | 
                                         BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build();
    }
    
    public void authenticate() {
        BiometricManager biometricManager = BiometricManager.from(context);
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG | 
                                                BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d(TAG, "App can authenticate using biometrics.");
                biometricPrompt.authenticate(promptInfo);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.e(TAG, "No biometric features available on this device.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.e(TAG, "Biometric features are currently unavailable.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Log.e(TAG, "The user hasn't associated any biometric credentials with their account.");
                // Prompt the user to set up a lock screen credential
                final Intent enrollIntent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                context.startActivity(enrollIntent);
                break;
            default:
                Log.e(TAG, "Unknown error.");
                break;
        }
    }
}
