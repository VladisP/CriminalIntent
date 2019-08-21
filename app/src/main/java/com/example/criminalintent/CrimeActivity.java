package com.example.criminalintent;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID = "com.example.criminalintent.crime_id";
    private static final String EXTRA_POSITION = "com.example.criminalintent.position";

    public static Intent newIntent(Context packageContext, UUID crimeId, int position) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        intent.putExtra(EXTRA_POSITION, position);
        return intent;
    }

    public static int getCrimePosition(Intent data) {
        return data.getIntExtra(EXTRA_POSITION, -1);
    }

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        int position = getIntent().getIntExtra(EXTRA_POSITION, -1);

        setPositionResult(position);

        return CrimeFragment.newInstance(crimeId);
    }

    private void setPositionResult(int position) {
        Intent data = new Intent();
        data.putExtra(EXTRA_POSITION, position);
        setResult(RESULT_OK, data);
    }
}
