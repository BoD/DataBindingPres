package org.jraf.test.ui.android;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.jraf.test.R;
import org.jraf.test.data.Repository;
import org.jraf.test.model.User;
import org.jraf.test.util.Async;

public class UserEditActivity extends LifecycleActivity {
    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";

    private TextView mTxtMessage;
    private EditText mEdtEmail;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android);
        mTxtMessage = (TextView) findViewById(R.id.txtMessage);
        mEdtEmail = (EditText) findViewById(R.id.edtEmail);
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
        mTxtMessage.setText(getString(R.string.mail_userEdit, mUser.getFirstName(), mUser.getLastName(), mUser.getEmail()));
        mEdtEmail.setText(mUser.getEmail());
        mEdtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                mUser.setEmail(editable.toString());

                mTxtMessage.setText(getString(R.string.mail_userEdit, mUser.getFirstName(), mUser.getLastName(), mUser.getEmail()));
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