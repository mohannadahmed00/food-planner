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

import com.giraffe.foodplannerapplication.R;

import java.util.regex.Pattern;

public class LoginFragment extends Fragment {
    private final static String TAG = "LoginFragment";

    private EditText edtEmail, edtPassword;
    private TextView tvEmailError, tvPasswordError, tvSignUp;
    private ImageView ivPassEye;
    private boolean isShownPass;
    private Button btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtEmail = view.findViewById(R.id.edt_email);
        edtPassword = view.findViewById(R.id.edt_password);
        tvEmailError = view.findViewById(R.id.tv_email_error);
        tvPasswordError = view.findViewById(R.id.tv_password_error);
        ivPassEye = view.findViewById(R.id.iv_eye);
        btnLogin = view.findViewById(R.id.btn_login);
        tvSignUp = view.findViewById(R.id.tv_sign_up);
        isShownPass = false;
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
            if (isValidData()) {
                Log.i(TAG, "valid data");
                //firebase auth
            }
        });
        tvSignUp.setOnClickListener(v -> Navigation.findNavController(v).navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment()));
    }

    boolean isValidData() {
        boolean isValidFlag = true;
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        Log.i(TAG, email);
        Log.i(TAG, password);

        if (email.isEmpty()) {
            Log.i(TAG, getString(R.string.required));
            setErrorMsg(tvEmailError, getString(R.string.required));
            isValidFlag = false;
        } else if (!isValidEmail(email)) {
            Log.i(TAG, getString(R.string.enter_a_valid_mail));
            setErrorMsg(tvEmailError, getString(R.string.enter_a_valid_mail));
            isValidFlag = false;
        } else {
            tvEmailError.setVisibility(View.INVISIBLE);
        }


        if (password.isEmpty()) {
            Log.i(TAG, getString(R.string.required));
            setErrorMsg(tvPasswordError, getString(R.string.required));
            isValidFlag = false;
        } else if (!isValidPassword(password).equals("")) {
            String passwordErrorMsg = isValidPassword(password);
            Log.i(TAG, passwordErrorMsg);
            setErrorMsg(tvPasswordError, passwordErrorMsg);
            isValidFlag = false;
        } else {
            tvPasswordError.setVisibility(View.INVISIBLE);
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
            Log.i(TAG, getString(R.string.password_must_contain_at_least_one_capital_letter));
            strBuilder.append(getString(R.string.password_must_contain_at_least_one_capital_letter)).append("\n");
        }
        if (!Pattern.matches(number, password)) {
            Log.i(TAG, getString(R.string.password_must_contain_at_least_one_digit));
            strBuilder.append(getString(R.string.password_must_contain_at_least_one_digit)).append("\n");
        }
        if (Pattern.matches(special, password)) {
            Log.i(TAG, getString(R.string.special_characters_are_not_allowed_in_the_password));
            strBuilder.append(getString(R.string.special_characters_are_not_allowed_in_the_password)).append("\n");
        }
        if (password.length() < 8) {
            Log.i(TAG, getString(R.string.password_must_be_at_least_8_characters_long));
            strBuilder.append(getString(R.string.password_must_be_at_least_8_characters_long)).append("\n");
        }
        return strBuilder.toString();
    }

    void setErrorMsg(TextView tvError, String msg) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(msg);
    }
}