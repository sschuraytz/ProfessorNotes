package com.example.macbook.professor_notes;

import android.support.v4.app.Fragment;

public class RecordListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new RecordListFragment();
    }
}
