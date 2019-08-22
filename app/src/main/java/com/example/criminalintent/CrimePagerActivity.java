package com.example.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.criminalintent.entities.Crime;

import java.util.List;

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_POSITION = "com.example.criminalintent.position";
    private static final String KEY_POSITION = "position";

    public static Intent newIntent(Context packageContext, int position) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_POSITION, position);
        return intent;
    }

    private ViewPager mViewPager;
    private int mPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        mPosition = getIntent().getIntExtra(EXTRA_POSITION, 0);

        if (savedInstanceState != null) {
            mPosition = savedInstanceState.getInt(KEY_POSITION, 0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mViewPager = findViewById(R.id.crime_view_pager);
        final List<Crime> crimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = crimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return crimes.size();
            }
        });

        mViewPager.setCurrentItem(mPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_POSITION, mViewPager.getCurrentItem());
    }
}
