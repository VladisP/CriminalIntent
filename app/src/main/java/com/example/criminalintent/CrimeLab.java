package com.example.criminalintent;

import android.content.Context;

import com.example.criminalintent.entities.Crime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private HashMap<UUID, Crime> mCrimeHashMap;
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimeHashMap = new HashMap<>();
        mCrimes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Нарушение #" + i);
            crime.setSolved(i % 2 == 0);
            crime.setRequiresPolice((i % 2 != 0) && (i % 5 == 0));
            mCrimeHashMap.put(crime.getId(), crime);
            mCrimes.add(crime);
        }
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        return mCrimeHashMap.get(id);
    }
}
