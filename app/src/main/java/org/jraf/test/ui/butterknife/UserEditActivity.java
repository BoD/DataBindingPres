package org.jraf.test.ui.butterknife;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import org.jraf.test.R;
import org.jraf.test.data.Repository;
import org.jraf.test.model.User;
import org.jraf.test.util.Async;

public class UserEditActivity extends LifecycleActivity {
    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";

    @BindView(R.id.txtMessage) TextView mTxtMessage;
    @BindView(R.id.edtEmail) EditText mEdtEmail;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android);
        ButterKnife.bind(this);

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
    }

    @OnTextChanged(R.id.edtEmail)
    protected void onEmailChanged(Editable editable) {
        mUser.setEmail(editable.toString());

        mTxtMessage.setText(getString(R.string.mail_userEdit, mUser.getFirstName(), mUser.getLastName(), mUser.getEmail()));
    }

    @OnClick(R.id.btnSave)
    protected void save() {
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