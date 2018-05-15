package com.kortain.windo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.kortain.windo.R;
import com.kortain.windo.fragment.AddLocationFragment;

public class AddLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        getSupportFragmentManager().beginTransaction().replace(
                R.id.add_location_container,
                AddLocationFragment.newInstance(),
                AddLocationFragment.class.toString()).commit();
    }

    public void onBackPressed(View view) {
        onBackPressed();
    }

}
