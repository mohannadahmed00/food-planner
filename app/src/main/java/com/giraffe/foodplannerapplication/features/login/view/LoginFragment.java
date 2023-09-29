package com.giraffe.foodplannerapplication.features.login.view;

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
import com.giraffe.foodplannerapplication.network.RemoteSource;
import com.giraffe.foodplannerapplication.util.LoadingDialog;
import com.giraffe.foodplannerapplication.util.NetworkConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginFragment extends Fragment implements LoginView {
    public final static String TAG = "LoginFragment";
    private EditText edtEmail, edtPassword;
    private TextView tvEmailError, tvPasswordError, tvSignUp;
    private ImageView ivPassEye;
    private boolean isShownPass;
    private Button btnLogin;
    private LoginPresenter presenter;

    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mView = view;
        inflateViews(view);
        initClicks();


    }

    @Override
    public void inflateViews(View view) {
        edtEmail = view.findViewById(R.id.edt_email);
        edtPassword = view.findViewById(R.id.edt_password);
        tvEmailError = view.findViewById(R.id.tv_email_error);
        tvPasswordError = view.findViewById(R.id.tv_password_error);
        ivPassEye = view.findViewById(R.id.iv_eye);
        btnLogin = view.findViewById(R.id.btn_login);
        tvSignUp = view.findViewById(R.id.tv_sign_up);
    }

    @Override
    public void initClicks() {
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
        tvSignUp.setOnClickListener(v -> Navigation.findNavController(v).navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment()));
    }

    @Override
    public void onLogin(Boolean isLoggedIn) {
        dismissDialog();
        if (isLoggedIn) {
            Navigation.findNavController(mView).navigate(LoginFragmentDirections.actionLoginFragmentToMainGraph());
        } else {
            Toast.makeText(getContext(), R.string.please_enter_a_valid_username_and_password, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidData() {
        boolean isValidFlag = true;
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty()) {
            setErrorMsg(tvEmailError, getString(R.string.required));
            isValidFlag = false;
        } else if (!isValidEmail(email)) {
            setErrorMsg(tvEmailError, getString(R.string.enter_a_valid_mail));
            isValidFlag = false;
        } else {
            tvEmailError.setVisibility(View.INVISIBLE);
        }

        if (password.isEmpty()) {
            setErrorMsg(tvPasswordError, getString(R.string.required));
            isValidFlag = false;
        } else if (!isValidPassword(password).equals("")) {
            String passwordErrorMsg = isValidPassword(password);
            setErrorMsg(tvPasswordError, passwordErrorMsg);
            isValidFlag = false;
        } else {
            tvPasswordError.setVisibility(View.INVISIBLE);
        }

        return isValidFlag;
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailPattern);
        return pattern.matcher(email).matches();
    }

    private String isValidPassword(String password) {
        StringBuilder strBuilder = new StringBuilder();
        String capital = ".*[A-Z].*";
        String number = ".*\\d.*";
        String special = ".*[^A-Za-z0-9].*";
        if (!Pattern.matches(capital, password)) {
            strBuilder.append(getString(R.string.password_must_contain_at_least_one_capital_letter)).append("\n");
        }
        if (!Pattern.matches(number, password)) {
            strBuilder.append(getString(R.string.password_must_contain_at_least_one_digit)).append("\n");
        }
        if (Pattern.matches(special, password)) {
            strBuilder.append(getString(R.string.special_characters_are_not_allowed_in_the_password)).append("\n");
        }
        if (password.length() < 8) {
            strBuilder.append(getString(R.string.password_must_be_at_least_8_characters_long)).append("\n");
        }
        return strBuilder.toString();
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
        if (NetworkConnection.isConnected(getContext())) {
            return true;
        } else {
            Toast.makeText(getContext(), R.string.check_your_internet_connection_and_try_again, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}