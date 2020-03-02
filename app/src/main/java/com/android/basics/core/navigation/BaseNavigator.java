package com.android.basics.core.navigation;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

interface BaseNavigator {

    void launchActivity(Intent intent);

    void finishActivity();

    void closeApplication();

    Intent createIntent(Class<? extends AppCompatActivity> clazz);

    Bundle createBundle();
}
