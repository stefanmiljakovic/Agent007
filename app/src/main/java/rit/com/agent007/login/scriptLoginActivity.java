package rit.com.agent007.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import rit.com.agent007.R;
import rit.com.agent007.SharedAppToolbox;
import rit.com.agent007.mainActivity;

/**
 * Created by Stefan on 28/10/17.
 */

public class scriptLoginActivity extends SharedAppToolbox {

    private Button button_LogIn;
    private ImageButton imageButton_ClearFirstName;
    private ImageButton imageButton_ClearLastName;
    private EditText editText_FirstName;
    private EditText editText_LastName;

    private void loadButtonClickScript(Button btn) {

        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                String firstName = getEditTextById(R.id.login_EditText_FirstName).getText().toString();
                String lastName = getEditTextById(R.id.login_EditText_LastName).getText().toString();

                if(firstName.isEmpty() || firstName == null || lastName.isEmpty() || lastName == null)
                    showToastMessage();
                else
                    callNextActivity(scriptLoginActivity.this, mainActivity.class, new String[]{
                            Integer.toString(R.layout.activity_main),
                            "NAME_SURNAME",
                            firstName + " " + lastName.charAt(0) + "."});
            }
        });
    }

    private void showToastMessage()
    {
        Toast toast = Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT);
        toast.show();
    }


    private void loadEditTextScript(final EditText editText, final ImageButton imageButton) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count == 0)
                    imageButton.setVisibility(ImageButton.GONE);
                else
                    imageButton.setVisibility(ImageButton.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        button_LogIn = getButtonById(R.id.login_Button_LogIn);

        editText_FirstName = getEditTextById(R.id.login_EditText_FirstName);
        editText_LastName = getEditTextById(R.id.login_EditText_LastName);

        imageButton_ClearFirstName = getImgButtonById(R.id.login_ImageButton_clearFirstName);
        imageButton_ClearLastName = getImgButtonById(R.id.login_ImageButton_clearLastName);

        loadButtonClickScript(button_LogIn);

        loadEditTextScript(editText_FirstName, imageButton_ClearFirstName);
        loadEditTextScript(editText_LastName, imageButton_ClearLastName);
    }
}
