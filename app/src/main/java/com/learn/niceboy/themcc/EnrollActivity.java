package com.learn.niceboy.themcc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

public class EnrollActivity extends AppCompatActivity {

    RadioGroup rgb;
    RadioButton rb_1, rb_2, result;
    TextView subEventName;
    Spinner spinner_department, spinner_year;
    EditText firstName, lastname, mobileNo, rollNo;
    Button next;

    ProgressDialog progressDialog = null;

    String member, department, year, event_name, sub_event_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        Intent mIntent = getIntent();
        event_name = mIntent.getStringExtra("Event_Name");
        sub_event_name = mIntent.getStringExtra("Sub_Event_Name");

        getSupportActionBar().setTitle("Registration");

        rgb = (RadioGroup) findViewById(R.id.rgb);
        rb_1 = (RadioButton) findViewById(R.id.rb_1);
        rb_2 = (RadioButton) findViewById(R.id.rb_2);

        firstName = (EditText) findViewById(R.id.input_name);
        lastname = (EditText) findViewById(R.id.input_surname);
        mobileNo = (EditText) findViewById(R.id.input_mobile);
        rollNo = (EditText) findViewById(R.id.input_rollno);

        subEventName = (TextView) findViewById(R.id.event);

        spinner_department = (Spinner) findViewById(R.id.department);
        spinner_year = (Spinner) findViewById(R.id.year);

        next = (Button) findViewById(R.id.done);

        subEventName.setText(sub_event_name);

        // Array of choices
        final String departments[] = {"Course", "BCOM", "BBI", "BAF", "BFM", "BMS", "BSc.CS", "BSc.IT", "MSc.IT", "MCOM", "Other College"};

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, departments);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner_department.setAdapter(spinnerArrayAdapter);

        // Array of choices
        String years[] = {"Year", "FY", "SY", "TY"};

        ArrayAdapter<String> spinnerArrayAdapter_1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        spinnerArrayAdapter_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner_year.setAdapter(spinnerArrayAdapter_1);

        spinner_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                department = spinner_department.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                year = spinner_year.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(EnrollActivity.this, "Please wait...", "Registering...");
                boolean test = false;
                try {
                    int selectedId = rgb.getCheckedRadioButtonId();
                    result = (RadioButton) findViewById(selectedId);
                    member = result.getText().toString();

                    if (firstName.getText().length() == 0) {
                        Toast.makeText(getApplicationContext(), "First name can not be blank!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (lastname.getText().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Surname name can not be blank!", Toast.LENGTH_SHORT).show();
                        } else {
                            if ((mobileNo.getText().length() <= 9)) {
                                Toast.makeText(getApplicationContext(), "Check your Mobile Number!", Toast.LENGTH_SHORT).show();
                            } else {
                                if (department.equals("Course")) {
                                    Toast.makeText(getApplicationContext(), "Please select Course!", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (year.equals("Year")) {
                                        Toast.makeText(getApplicationContext(), "Please select Year!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (rollNo.getText().length() == 0) {
                                            Toast.makeText(getApplicationContext(), "Roll Number can not be blank!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            ParseObject object = new ParseObject("EnrolledUser");
                                            object.put("EventName", event_name);
                                            object.put("SubEventName", sub_event_name);
                                            object.put("FirstName", firstName.getText().toString());
                                            object.put("LastName", lastname.getText().toString());
                                            object.put("Switch", false);
                                            object.put("RollNo", rollNo.getText().toString());
                                            object.put("Course", department);
                                            object.put("Year", year);
                                            object.put("MobileNo", mobileNo.getText().toString());
                                            object.put("Member", member);
                                            object.saveEventually();

                                            test = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                } finally {
                    progressDialog.dismiss();
                    if (test) {
                        Toast.makeText(getApplicationContext(), "Registered Successfully!!!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), EventDescription.class);
                        intent.putExtra("Event_Name", event_name);
                        intent.putExtra("Sub_Event_Name", sub_event_name);
                        startActivity(intent);
                        EventDescription.eventDesc.finish();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please try Again!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
