package com.example.macbook.professor_notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class RecordPagerActivity extends AppCompatActivity {
    protected static final String EXTRA_RECORD_ID = "com.example.macbook.professor_notes.record_id";

    private ViewPager mViewPager;
    private List<Record> mRecords;

    public static Intent newIntent(Context packageContext, UUID recordID) {
        Intent intent = new Intent(packageContext, RecordPagerActivity.class);
        intent.putExtra(EXTRA_RECORD_ID, recordID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_pager);

        UUID recordId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_RECORD_ID);

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);

        mRecords = RecordLab.get(this).getRecords();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Record record = mRecords.get(position);
                return RecordFragment.newInstance(record.getId());
            }

            @Override
            public int getCount() {
                return mRecords.size();
            }
        });

        for (int i = 0; i < mRecords.size(); i++) {
            if (mRecords.get(i).getId().equals(recordId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
