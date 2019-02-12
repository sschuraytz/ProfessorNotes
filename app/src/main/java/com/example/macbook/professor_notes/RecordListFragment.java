package com.example.macbook.professor_notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static javax.xml.xpath.XPathFactory.newInstance;

public class RecordListFragment extends Fragment {
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mRecordRecyclerView;
    private RecordAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_list, container, false);

        setUpRecyclerList(view);

        if(savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    private void setUpRecyclerList(View view) {
        mRecordRecyclerView = (RecyclerView)view
            .findViewById(R.id.record_recycler_view);
        mRecordRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_record_list, menu);

        setUpSubtitleItem(menu);
    }

    private void setUpSubtitleItem(Menu menu) {
        MenuItem subtitleItem = menu.findItem(R.id.action_toggle_subtitle);

        if(mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }

    }

    private void updateSubtitle() {
        RecordLab recordLab = RecordLab.get(getActivity());
        int recordCount = recordLab.getRecords().size();
        String subtitle = getString(R.string.subtitle_format, recordCount);

        if(!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.about_page: {
                showAbout();
                return true;
            }
            case R.id.new_record:
                Record record = new Record();
                RecordLab.get(getActivity()).addRecord(record);
                Intent intent = RecordPagerActivity
                        .newIntent(getActivity(), record.getId());
                startActivity(intent);
                return true;
            case R.id.action_toggle_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAbout() {

        FragmentManager manager = getFragmentManager();
        MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(manager, "action_about");
    }

    private void updateUI() {
        RecordLab recordLab = RecordLab.get(getActivity());
        List<Record> records = recordLab.getRecords();

        setUpAdapter(records);

        updateSubtitle();
    }

    private void setUpAdapter(List<Record> records) {
        if(mAdapter == null) {
            mAdapter = new RecordAdapter(records);
            mRecordRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setRecords(records);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class RecordAdapter extends RecyclerView.Adapter<RecordHolder> {

        private List<Record> mRecords;

        public RecordAdapter(List<Record> records) {
            mRecords = records;
        }

        @NonNull
        @Override
        public RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new RecordHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RecordHolder holder, int position) {
            Record record = mRecords.get(position);
            holder.bind(record);
        }

        @Override
        public int getItemCount() {
            return mRecords.size();
        }

        public void setRecords(List<Record> records) {
            mRecords = records;
        }
    }

    private class RecordHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        private ImageButton mDeleteButton;

        private Record mRecord;

        public RecordHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_record, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.record_list_title_text);
            mDateTextView = (TextView) itemView.findViewById(R.id.record_list_date_text);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.record_list_dealt_with_image);

            mDeleteButton = (ImageButton) itemView.findViewById(R.id.record_list_delete_button);
        }

        public void bind(Record Record) {
            mRecord = Record;
            if(mRecord.getFirstName() != null) {
                if(mRecord.getLastName() != null) {
                    mTitleTextView.setText(mRecord.getFirstName() + " " + mRecord.getLastName());
                } else {
                    mTitleTextView.setText(mRecord.getFirstName());
                }
            } else if(mRecord.getLastName() != null) {
                mTitleTextView.setText(mRecord.getLastName());
            } else {
                mTitleTextView.setText(" ");
            }

            mDateTextView.setText(mRecord.getDate().toString());
            mSolvedImageView.setVisibility(Record.isDealtWith() ? View.VISIBLE : View.GONE);

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecordLab.get(getActivity()).deleteRecord(mRecord.getId());
                    updateUI();
                }
            });
        }

        @Override
        public void onClick(View view) {
            Intent intent = RecordPagerActivity.newIntent(getActivity(), mRecord.getId());
            startActivity(intent);
        }
    }


}

