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
import org.jraf.test.util.ValidationUtil;

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                mUser.setEmail(editable.toString());
                mBinding.imgValid.setVisibility(ValidationUtil.isValidEmail(mUser.getEmail()) ? View.VISIBLE : View.INVISIBLE);
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