package com.example.criminalintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.criminalintent.database.CrimeBaseHelper;
import com.example.criminalintent.entities.Crime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private HashMap<UUID, Crime> mCrimeHashMap;
    private List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
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

    public void deleteCrime(Crime crime) {
        mCrimeHashMap.remove(crime.getId());

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId() == crime.getId()) {
                mCrimes.remove(i);
                break;
            }
        }
    }

    public Crime getCrime(UUID id) {
        return mCrimeHashMap.get(id);
    }
}
