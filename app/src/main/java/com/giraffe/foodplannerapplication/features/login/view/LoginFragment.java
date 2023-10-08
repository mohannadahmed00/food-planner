package com.giraffe.foodplannerapplication.features.login.view;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.database.ConcreteLocalSource;
import com.giraffe.foodplannerapplication.features.login.presenter.LoginPresenter;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.ApiClient;
import com.giraffe.foodplannerapplication.util.LoadingDialog;
import com.giraffe.foodplannerapplication.util.NetworkConnection;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;

public class LoginFragment extends Fragment implements LoginView {
    public final static String TAG = "LoginFragment";
    private EditText edtEmail, edtPassword;
    private TextView tvSkip, tvEmailError, tvSignUp;
    private ImageView ivPassEye;
    private boolean isShownPass;
    private Button btnLogin;
    private LoginPresenter presenter;

    private FirebaseAuth mAuth;

    public static int RC_SIGN_IN = 98;

    private Button btnGoogle;

    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;


    private void signInWithGoogle() {
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener((OnSuccessListener<BeginSignInResult>) result -> {
                    try {
                        startIntentSenderForResult(result.getPendingIntent().getIntentSender(), RC_SIGN_IN, null, 0, 0, 0, null);
                    } catch (IntentSender.SendIntentException e) {
                        Log.e(TAG, "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                    }
                })
                .addOnFailureListener(e -> Log.d(TAG, e.getMessage()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            try {
                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                String idToken = credential.getGoogleIdToken();
                if (idToken != null) {
                    presenter.loginWithGoogle(idToken);
                }
            } catch (ApiException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oneTapClient = Identity.getSignInClient(requireContext());
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.default_web_client_id))
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .build();
        presenter = new LoginPresenter(
                this,
                Repo.getInstance(
                        ApiClient.getInstance(),
                        ConcreteLocalSource.getInstance(getContext())
                )
        );
        isShownPass = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inflateViews(view);
        initClicks();


    }

    @Override
    public void inflateViews(View view) {
        tvSkip = view.findViewById(R.id.tv_skip);
        edtEmail = view.findViewById(R.id.edt_email);
        edtPassword = view.findViewById(R.id.edt_password);
        tvEmailError = view.findViewById(R.id.tv_email_error);
        ivPassEye = view.findViewById(R.id.iv_eye);
        btnLogin = view.findViewById(R.id.btn_login);
        tvSignUp = view.findViewById(R.id.tv_sign_up);
        btnGoogle = view.findViewById(R.id.btn_google);
    }

    @Override
    public void initClicks() {
        tvSkip.setOnClickListener(v -> Navigation.findNavController(v).setGraph(R.navigation.main_graph));
        ivPassEye.setOnClickListener(v -> {
            if (!isShownPass) {
                edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivPassEye.setImageResource(R.drawable.ic_close_eye);
            } else {
                edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ivPassEye.setImageResource(R.drawable.ic_open_eye);
            }
            edtPassword.setSelection(edtPassword.getText().length());
            isShownPass = !isShownPass;
        });
        btnLogin.setOnClickListener(v -> {
            if (isConnected()) {
                if (isValidData()) {
                    String email = edtEmail.getText().toString().trim();
                    String password = edtPassword.getText().toString().trim();
                    showDialog();
                    presenter.login(email, password);
                }
            }
        });
        btnGoogle.setOnClickListener(v -> {
            if (isConnected()) {
                signInWithGoogle();
            }
        });
        tvSignUp.setOnClickListener(v -> Navigation.findNavController(v).navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment()));
    }

    @Override
    public void onLogin(Completable completable) {
        dismissDialog();
        completable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Navigation.findNavController(requireView()).navigate(LoginFragmentDirections.actionLoginFragmentToMainGraph()),
                        throwable -> {
                            Log.e(TAG, throwable.getMessage());
                            Toast.makeText(getContext(), R.string.please_enter_a_valid_username_and_password, Toast.LENGTH_SHORT).show();
                        }
                );
    }

    @Override
    public void onGoogleLogin(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Navigation.findNavController(requireView()).navigate(LoginFragmentDirections.actionLoginFragmentToMainGraph()),
                        throwable -> {
                            Log.e(TAG, throwable.getMessage());
                            Toast.makeText(getContext(), R.string.please_enter_a_valid_username_and_password, Toast.LENGTH_SHORT).show();
                        }
                );
    }

    private boolean isValidData() {
        boolean isValidFlag = true;
        String email = edtEmail.getText().toString().trim();

        if (email.isEmpty()) {
            setErrorMsg(tvEmailError, getString(R.string.required));
            isValidFlag = false;
        } else if (!isValidEmail(email)) {
            setErrorMsg(tvEmailError, getString(R.string.enter_a_valid_mail));
            isValidFlag = false;
        } else {
            tvEmailError.setVisibility(View.INVISIBLE);
        }

        return isValidFlag;
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailPattern);
        return pattern.matcher(email).matches();
    }


    private void setErrorMsg(TextView tvError, String msg) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(msg);
    }

    private void showDialog() {
        LoadingDialog.getInstance(getParentFragmentManager()).showLoading();
    }

    private void dismissDialog() {
        LoadingDialog.getInstance(getParentFragmentManager()).dismissLoading();
    }

    private boolean isConnected() {
        if (NetworkConnection.isConnected(requireContext())) {
            return true;
        } else {
            Toast.makeText(getContext(), R.string.check_your_internet_connection_and_try_again, Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}