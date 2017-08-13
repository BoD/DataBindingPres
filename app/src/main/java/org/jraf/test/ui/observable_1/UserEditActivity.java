package org.jraf.test.ui.observable_1;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.jraf.test.R;
import org.jraf.test.data.Repository;
import org.jraf.test.databinding.Observable1Binding;
import org.jraf.test.model.User;
import org.jraf.test.util.Async;

public class UserEditActivity extends AppCompatActivity {
    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";

    public ObservableField<User> user = new ObservableField<>();
    public ObservableField<String> userEmail = new ObservableField<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable1Binding binding = DataBindingUtil.setContentView(this, R.layout.observable_1);
        binding.setController(this);

        userEmail.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                user.get().setEmail(userEmail.get());
            }
        });

        loadData();
    }

    private void loadData() {
        new Async() {
            @Override
            protected void doInBackground() {
                user.set(Repository.findUser(getIntent().getLongExtra(EXTRA_USER_ID, -1)));
                userEmail.set(user.get().getEmail());
            }
        }.execute();
    }

    public void save() {
        new Async() {
            @Override
            protected void doInBackground() {
                Repository.insertOrUpdateUser(user.get());
            }

            @Override
            protected void onPostExecute() {
                finish();
            }
        }.execute();
    }
}