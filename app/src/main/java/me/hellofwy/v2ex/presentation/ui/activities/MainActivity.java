package me.hellofwy.v2ex.presentation.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kodelabs.v2ex.R;
import me.hellofwy.v2ex.presentation.presenters.MainPresenter.View;

public class MainActivity extends AppCompatActivity implements View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }
}
