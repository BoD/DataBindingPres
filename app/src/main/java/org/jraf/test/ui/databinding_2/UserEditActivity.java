package org.jraf.test.ui.databinding_2;

import android.arch.lifecycle.LifecycleActivity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import org.jraf.test.R;
import org.jraf.test.data.Repository;
import org.jraf.test.databinding.Databinding2Binding;
import org.jraf.test.model.User;
import org.jraf.test.util.Async;

public class UserEditActivity extends LifecycleActivity {
    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";

    private Databinding2Binding mBinding;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.databinding_2);
        mBinding.setController(this);
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
    }

    public void save() {
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