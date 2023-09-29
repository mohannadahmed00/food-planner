package com.giraffe.foodplannerapplication.features.signup.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.giraffe.foodplannerapplication.features.signup.presenter.SignUpPresenter;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.ApiClient;
import com.giraffe.foodplannerapplication.util.LoadingDialog;
import com.giraffe.foodplannerapplication.util.NetworkConnection;

import java.util.regex.Pattern;

public class SignUpFragment extends Fragment implements SignUpView {
    private final static String TAG = "SignUpFragment";

    private EditText edtEmail, edtPassword, edtConfirmPassword;
    private TextView tvEmailError, tvPasswordError, tvConfirmPasswordError, tvLogin;

    private ImageView ivPassEye, ivConfirmPassEye;

    private boolean isShownPass, isShownConfirmPass;
    private Button btnCreate;

    private SignUpPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignUpPresenter(this, Repo.getInstance(
                ApiClient.getInstance(),
                ConcreteLocalSource.getInstance(getContext())
        ));
        isShownPass = false;
        isShownConfirmPass = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inflateViews(view);
        initClicks();
    }

    @Override
    public void inflateViews(View view) {
        edtEmail = view.findViewById(R.id.edt_email);
        edtPassword = view.findViewById(R.id.edt_password);
        edtConfirmPassword = view.findViewById(R.id.edt_confirm_password);
        tvEmailError = view.findViewById(R.id.tv_email_error);
        tvPasswordError = view.findViewById(R.id.tv_password_error);
        ivPassEye = view.findViewById(R.id.iv_eye_pass);
        tvConfirmPasswordError = view.findViewById(R.id.tv_confirm_password_error);
        ivConfirmPassEye = view.findViewById(R.id.iv_eye_confirm_pass);
        btnCreate = view.findViewById(R.id.btn_create_account);
        tvLogin = view.findViewById(R.id.tv_login);
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
        ivConfirmPassEye.setOnClickListener(v -> {
            if (!isShownConfirmPass) {
                edtConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivConfirmPassEye.setImageResource(R.drawable.ic_close_eye);
            } else {
                edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ivConfirmPassEye.setImageResource(R.drawable.ic_open_eye);
            }
            edtConfirmPassword.setSelection(edtConfirmPassword.getText().length());
            isShownConfirmPass = !isShownConfirmPass;
        });
        btnCreate.setOnClickListener(v -> {
            if (isConnected()) {
                if (isValidData()) {
                    String email = edtEmail.getText().toString().trim();
                    String password = edtPassword.getText().toString().trim();
                    showDialog();
                    presenter.createAccount(email, password);
                }
            }
        });
        tvLogin.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
    }

    @Override
    public void onCreateAccount(Boolean isRegistered) {
        dismissDialog();
        if (isRegistered) {
            Navigation.findNavController(requireView()).navigateUp();
        } else {
            Toast.makeText(getContext(), R.string.the_email_address_is_already_in_use_by_another_account, Toast.LENGTH_SHORT).show();
        }
    }

    boolean isValidData() {
        boolean isValidFlag = true;
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

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


        if (confirmPassword.isEmpty()) {
            setErrorMsg(tvConfirmPasswordError, getString(R.string.required));
            isValidFlag = false;
        } else if (!confirmPassword.equals(password)) {
            setErrorMsg(tvConfirmPasswordError, getString(R.string.passwords_do_not_match));
            isValidFlag = false;
        } else {
            tvConfirmPasswordError.setVisibility(View.INVISIBLE);
        }

        return isValidFlag;
    }

    boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailPattern);
        return pattern.matcher(email).matches();
    }

    String isValidPassword(String password) {
        StringBuilder strBuilder = new StringBuilder();
        String capital = ".*[A-Z].*";
        String number = ".*\\d.*";
        String special = ".*[^A-Za-z0-9].*";
        if (!Pattern.matches(capital, password)) {
            strBuilder.append("password must contain at least one capital letter\n");
        }
        if (!Pattern.matches(number, password)) {
            strBuilder.append("password must contain at least one digit\n");
        }
        if (Pattern.matches(special, password)) {
            strBuilder.append("special characters are not allowed in the password\n");
        }
        if (password.length() < 8) {
            strBuilder.append("password must be at least 8 characters long\n");
        }
        return strBuilder.toString();
    }

    void setErrorMsg(TextView tvError, String msg) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(msg);
    }

    public void showDialog() {
        LoadingDialog.getInstance(getParentFragmentManager()).showLoading();
    }

    public void dismissDialog() {
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