package com.example.macbook.professor_notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

public class RecordFragment extends Fragment {

    private static final String ARG_RECORD_ID = "record_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private Record mRecord;
    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mCourseField;
    private EditText mNotesField;
    private Button mDateButton;
    private CheckBox mDealtWithCheckBox;

    public static RecordFragment newInstance(UUID recordId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECORD_ID, recordId);

        RecordFragment fragment = new RecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeID = (UUID) getArguments().getSerializable(ARG_RECORD_ID);
        mRecord = RecordLab.get(getActivity()).getRecord(crimeID);
    }

    @Override
    public void onPause() {
        super.onPause();

        RecordLab.get(getActivity())
                .updateRecord(mRecord);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_record, container, false);

        mFirstNameField = (EditText) v.findViewById(R.id.first_name_field);
        mFirstNameField.setText(mRecord.getFirstName());
        mFirstNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRecord.setFirstName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLastNameField = (EditText) v.findViewById(R.id.last_name_field);
        mLastNameField.setText(mRecord.getLastName());
        mLastNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRecord.setLastName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCourseField = (EditText) v.findViewById(R.id.course_field);
        mCourseField.setText(mRecord.getCourse());
        mCourseField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRecord.setCourse(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mNotesField = (EditText) v.findViewById(R.id.notes_field);
        mNotesField.setText(mRecord.getNotes());
        mNotesField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRecord.setNotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.date_field);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mRecord.getDate());
                dialog.setTargetFragment(RecordFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mDealtWithCheckBox = (CheckBox) v.findViewById(R.id.dealt_with_checkbox);
        mDealtWithCheckBox.setChecked(mRecord.isDealtWith());
        mDealtWithCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mRecord.setDealtWith(isChecked);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mRecord.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(mRecord.getDate().toString());
    }
}
