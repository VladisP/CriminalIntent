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
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public void addCrime(Crime crime) {
        mCrimeHashMap.put(crime.getId(), crime);
        mCrimes.add(crime);
    }

    public Crime getCrime(UUID id) {
        return mCrimeHashMap.get(id);
    }
}
