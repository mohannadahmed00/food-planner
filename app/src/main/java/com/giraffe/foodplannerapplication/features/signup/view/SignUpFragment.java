package com.giraffe.foodplannerapplication.features.signup.view;

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

public class SignUpFragment extends Fragment {
    private final static String TAG = "SignUpFragment";

    private EditText edtEmail, edtPassword, edtConfirmPassword;
    private TextView tvEmailError, tvPasswordError, tvConfirmPasswordError,tvLogin;

    private ImageView ivPassEye,ivConfirmPassEye;

    private boolean isShownPass,isShownConfirmPass;
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
        ivPassEye = view.findViewById(R.id.iv_eye_pass);
        tvConfirmPasswordError = view.findViewById(R.id.tv_confirm_password_error);
        ivConfirmPassEye = view.findViewById(R.id.iv_eye_confirm_pass);
        btnCreate = view.findViewById(R.id.btn_create_account);
        tvLogin = view.findViewById(R.id.tv_login);
        isShownPass = false;
        isShownConfirmPass = false;
        ivPassEye.setOnClickListener(v->{
            if(!isShownPass){
                edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivPassEye.setImageResource(R.drawable.ic_close_eye);
            } else{
                edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ivPassEye.setImageResource(R.drawable.ic_open_eye);
            }
            edtPassword.setSelection(edtPassword.getText().length());
            isShownPass = !isShownPass;
        });
        ivConfirmPassEye.setOnClickListener(v->{
            if(!isShownConfirmPass){
                edtConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivConfirmPassEye.setImageResource(R.drawable.ic_close_eye);
            } else{
                edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ivConfirmPassEye.setImageResource(R.drawable.ic_open_eye);
            }
            edtConfirmPassword.setSelection(edtConfirmPassword.getText().length());
            isShownConfirmPass = !isShownConfirmPass;
        });

        btnCreate.setOnClickListener(v -> {
            if (isValidData()){
                Log.i(TAG,"valid data");
                //firebase auth
            }
        });
        tvLogin.setOnClickListener(v-> Navigation.findNavController(v).navigateUp());
    }

    boolean isValidData() {
        boolean isValidFlag = true;
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        if (email.isEmpty()) {
            Log.i(TAG,getString(R.string.required));
            setErrorMsg(tvEmailError, getString(R.string.required));
            isValidFlag =  false;
        }else if (!isValidEmail(email)) {
            Log.i(TAG,getString(R.string.enter_a_valid_mail));
            setErrorMsg(tvEmailError, getString(R.string.enter_a_valid_mail));
            isValidFlag =  false;
        }else {
            tvEmailError.setVisibility(View.INVISIBLE);
        }


        if (password.isEmpty()) {
            Log.i(TAG,getString(R.string.required));
            setErrorMsg(tvPasswordError, getString(R.string.required));
            isValidFlag =  false;
        }else if (!isValidPassword(password).equals("")) {
            String passwordErrorMsg = isValidPassword(password);
            Log.i(TAG,passwordErrorMsg);
            setErrorMsg(tvPasswordError, passwordErrorMsg);
            isValidFlag =  false;
        }else {
            tvPasswordError.setVisibility(View.INVISIBLE);
        }


        if (confirmPassword.isEmpty()) {
            setErrorMsg(tvConfirmPasswordError, getString(R.string.required));
            isValidFlag = false;
        }else if (!confirmPassword.equals(password)) {
            setErrorMsg(tvConfirmPasswordError, getString(R.string.passwords_do_not_match));
            isValidFlag = false;
        }else {
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
}