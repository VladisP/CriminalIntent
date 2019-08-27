package com.example.criminalintent.list_crimes;

import androidx.fragment.app.Fragment;

import com.example.criminalintent.single_crime.SingleFragmentActivity;

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
