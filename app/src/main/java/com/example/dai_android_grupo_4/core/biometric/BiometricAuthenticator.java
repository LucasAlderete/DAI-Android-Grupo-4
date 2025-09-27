package com.example.dai_android_grupo_4.core.biometric;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class BiometricAuthenticator {
    private static final String TAG = "BiometricAuthenticator";

    private final Context context;
    private final BiometricPrompt biometricPrompt;
    private final BiometricPrompt.PromptInfo promptInfo;

    public interface AuthenticationCallback {
        void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result);
        void onAuthenticationError(int errorCode, @NonNull CharSequence errString);
        void onAuthenticationFailed();
    }

    public BiometricAuthenticator(Context context, AuthenticationCallback callback) {
        this.context = context;

        Executor executor = ContextCompat.getMainExecutor(context);
        biometricPrompt = new BiometricPrompt(
                (androidx.fragment.app.FragmentActivity) context,
                executor,
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        Log.e(TAG, "Authentication error: " + errString);
                        callback.onAuthenticationError(errorCode, errString);
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Log.d(TAG, "Authentication succeeded!");
                        callback.onAuthenticationSucceeded(result);
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Log.e(TAG, "Authentication failed");
                        callback.onAuthenticationFailed();
                    }
                });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación biométrica")
                .setSubtitle("Usa tu huella digital o credenciales del dispositivo")
                .setAllowedAuthenticators(
                        BiometricManager.Authenticators.BIOMETRIC_STRONG |
                                BiometricManager.Authenticators.DEVICE_CREDENTIAL
                )
                .build();
    }

    public void authenticate() {
        BiometricManager biometricManager = BiometricManager.from(context);
        int canAuth = biometricManager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG |
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL
        );

        switch (canAuth) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d(TAG, "Biometric authentication is available.");
                biometricPrompt.authenticate(promptInfo);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.e(TAG, "No biometric hardware available.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.e(TAG, "Biometric hardware currently unavailable.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Log.e(TAG, "El usuario no tiene biometría configurada. Usar login manual.");
                break;
            default:
                Log.e(TAG, "Biometric authentication not available.");
                break;
        }
    }
}
