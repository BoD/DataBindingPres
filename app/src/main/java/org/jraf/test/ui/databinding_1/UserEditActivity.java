package org.jraf.test.ui.databinding_1;

import android.arch.lifecycle.LifecycleActivity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import org.jraf.test.R;
import org.jraf.test.data.Repository;
import org.jraf.test.databinding.Databinding1Binding;
import org.jraf.test.model.User;
import org.jraf.test.util.Async;

public class UserEditActivity extends LifecycleActivity {
    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";

    private Databinding1Binding mBinding;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.databinding_1);

        mBinding.btnSave.setOnClickListener(new View.OnClickListener() {
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
        mBinding.setUser(mUser);

        mBinding.edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                mUser.setEmail(editable.toString());

                mBinding.txtMessage.setText(getString(R.string.mail_userEdit, mUser.getFirstName(), mUser.getLastName(), mUser.getEmail()));
            }
        });
    }

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