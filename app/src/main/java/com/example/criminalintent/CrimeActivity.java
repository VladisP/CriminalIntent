package com.example.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID = "com.example.criminalintent.crime_id";
    private static final String EXTRA_POSITION = "com.example.criminalintent.position";
    private static final String KEY_POSITION = "position";

    public static Intent newIntent(Context packageContext, UUID crimeId, int position) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        intent.putExtra(EXTRA_POSITION, position);
        return intent;
    }

    public static int getCrimePosition(Intent data) {
        return data.getIntExtra(EXTRA_POSITION, -1);
    }

    private int mPosition;

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mPosition = getIntent().getIntExtra(EXTRA_POSITION, -1);

        setPositionResult(mPosition);

        return CrimeFragment.newInstance(crimeId);
    }

    private void setPositionResult(int position) {
        Intent data = new Intent();
        data.putExtra(EXTRA_POSITION, position);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_POSITION, mPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mPosition = savedInstanceState.getInt(KEY_POSITION, -1);
            setPositionResult(mPosition);
        }
    }
}
