package com.example.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.criminalintent.entities.Crime;

import java.text.DateFormat;
import java.util.List;

public class CrimeListFragment extends Fragment {

    private static final String KEY_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    private boolean mIsSubtitleVisible;

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        private Crime mCrime;
        private int mCurrentPosition;

        CrimeHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.crime_title);
            mDateTextView = itemView.findViewById(R.id.crime_date);
            mSolvedImageView = itemView.findViewById(R.id.crime_solved);
            itemView.setOnClickListener(this);
        }

        void setCurrentPosition(int currentPosition) {
            mCurrentPosition = currentPosition;
        }

        void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            String date = DateFormat.getDateInstance().format(mCrime.getDate());
            String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(mCrime.getDate());
            mDateTextView.setText(date + " " + time);
            mSolvedImageView.setImageResource(crime.isSolved() ? R.drawable.ic_smile_man_green_24dp : R.drawable.ic_sad_man_red_24dp);
        }

        @Override
        public void onClick(View v) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCurrentPosition);
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private static final int DEFAULT_TYPE = 0;
        private static final int POLICE_TYPE = 1;

        private List<Crime> mCrimes;

        CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view;

            switch (viewType) {
                case POLICE_TYPE:
                    view = layoutInflater.inflate(R.layout.list_police_item_crime, parent, false);
                    break;
                case DEFAULT_TYPE:
                default:
                    view = layoutInflater.inflate(R.layout.list_default_item_crime, parent, false);
            }

            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
            holder.setCurrentPosition(position);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (mCrimes.get(position).isRequiresPolice()) ? POLICE_TYPE : DEFAULT_TYPE;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mIsSubtitleVisible = savedInstanceState.getBoolean(KEY_SUBTITLE_VISIBLE, false);
        }

        return inflater.inflate(R.layout.fragment_crime_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mIsSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                CrimeLab.get(getActivity()).addCrime(new Crime());
                Intent intent = CrimePagerActivity.newIntent(getActivity(), mAdapter.getItemCount());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                mIsSubtitleVisible = !mIsSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_SUBTITLE_VISIBLE, mIsSubtitleVisible);
    }

    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);

        if (!mIsSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        if (mAdapter == null) {
            CrimeLab crimeLab = CrimeLab.get(getActivity());
            List<Crime> crimes = crimeLab.getCrimes();
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }
}
