package com.giraffe.foodplannerapplication.features.signup.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.giraffe.foodplannerapplication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {

    private EditText edtEmail, edtPassword, edtConfirmPassword;
    private TextView tvEmailError, tvPasswordError, tvConfirmPasswordError;
    private Button btnCreate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtEmail = view.findViewById(R.id.edt_email);
        edtPassword = view.findViewById(R.id.edt_password);
        edtConfirmPassword = view.findViewById(R.id.edt_confirm_password);
        tvEmailError = view.findViewById(R.id.tv_email_error);
        tvPasswordError = view.findViewById(R.id.tv_password_error);
        tvConfirmPasswordError = view.findViewById(R.id.tv_confirm_password_error);
        btnCreate = view.findViewById(R.id.btn_create_account);
        btnCreate.setOnClickListener(v -> {
            if (isValidData()){
                //firebase auth
            }
        });
    }

    boolean isValidData() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        if (email.isEmpty()) {
            setErrorMsg(tvEmailError, getString(R.string.required));
            return false;
        }
        if (!isValidEmail(email)) {
            setErrorMsg(tvPasswordError, getString(R.string.enter_a_valid_mail));
            return false;
        }
        tvEmailError.setVisibility(View.INVISIBLE);

        if (password.isEmpty()) {
            setErrorMsg(tvPasswordError, getString(R.string.required));
            return false;
        }
        String passwordErrorMsg = isValidPassword(password);
        if (!passwordErrorMsg.equals("")) {
            setErrorMsg(tvPasswordError, passwordErrorMsg);
            return false;
        }
        tvPasswordError.setVisibility(View.INVISIBLE);

        if (confirmPassword.isEmpty()) {
            setErrorMsg(tvConfirmPasswordError, getString(R.string.required));
            return false;
        }
        if (!confirmPassword.equals(password)) {
            setErrorMsg(tvPasswordError, getString(R.string.passwords_do_not_match));
            return false;
        }
        tvConfirmPasswordError.setVisibility(View.INVISIBLE);

        return true;
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
}