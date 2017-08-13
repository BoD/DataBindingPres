package org.jraf.test.ui.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.jraf.test.R;
import org.jraf.test.data.Repository;
import org.jraf.test.model.User;
import org.jraf.test.util.Async;
import org.jraf.test.util.ValidationUtil;

public class UserEditActivity extends AppCompatActivity {
    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";

    private TextView mTxtMessage;
    private EditText mEdtEmail;
    private ImageView mImgValid;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android);
        mTxtMessage = (TextView) findViewById(R.id.txtMessage);
        mEdtEmail = (EditText) findViewById(R.id.edtEmail);
        mImgValid = (ImageView) findViewById(R.id.imgValid);
        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        loadData();
    }

    private void loadData() {
        new Async() {
            @Override
            protected void doInBackground() {
                mUser = Repository.findUser(getIntent().getLongExtra(EXTRA_USER_ID, -1));
            }

            @Override
            protected void onPostExecute() {
                updateUi();
            }
        }.execute();
    }

    private void updateUi() {
        mTxtMessage.setText(getString(R.string.mail_userEdit, mUser.getFirstName(), mUser.getLastName()));
        mEdtEmail.setText(mUser.getEmail());
        mEdtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                mUser.setEmail(editable.toString());
                mImgValid.setVisibility(ValidationUtil.isValidEmail(mUser.getEmail()) ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }

    private void save() {
        new Async() {
            @Override
            protected void doInBackground() {
                Repository.insertOrUpdateUser(mUser);
            }

            @Override
            protected void onPostExecute() {
                finish();
            }
        }.execute();
    }
}