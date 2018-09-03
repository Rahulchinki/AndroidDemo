package com.mytaxi.android_demo.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;

import com.mytaxi.android_demo.App;
import com.mytaxi.android_demo.IdlingResourceImplementation.SimpleIdlingResource;
import com.mytaxi.android_demo.dependencies.component.AppComponent;
import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;

import javax.inject.Inject;

public class AuthenticatedActivity extends AppCompatActivity {

    @Nullable private SimpleIdlingResource mIdlingResource;
    @Inject
    SharedPrefStorage mSharedPrefStorage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppComponent appComponent = App.getApplicationContext(this).getAppComponent();
        appComponent.inject(this);
    }

    protected boolean isAuthenticated() {
        return mSharedPrefStorage.loadUser() != null;
    }





    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


}

