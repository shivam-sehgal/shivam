package com.example.user.assignment3;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FormFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Context ctx;
    private ImageView imageView;
    private final int PICK_IMAGE_REQUEST = 1;

    Calendar cal;
    private Bitmap userImage;
    private Button datePicker;
    public static TextView dateOfBirthTextView;
    String DateOfBirth;
    private FragmentManager fragmentManager;
    DataPassListener mCallback;
    private String gender;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private RadioGroup rg;
    private DatePickerDialog.OnDateSetListener datePickerListener;
    private Spinner spinner;
    private String stream;
    private Button btn;
    private EditText firstName;
    private  EditText lastName;
   private String firstnameText;
    private String lastnameText;
    public FormFragment() {
        // Required empty public constructor
    }


    public interface DataPassListener{
        public void passData(Data data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        imageView = (ImageView) view.findViewById(R.id.user_image);
        datePicker = (Button) view.findViewById(R.id.date_of_birth_form_btn);
        dateOfBirthTextView = (TextView) view.findViewById(R.id.date_birth_tv_form);
        ctx = getActivity().getApplicationContext();
        radioMale = (RadioButton) view.findViewById(R.id.radio_male);
        radioFemale = (RadioButton) view.findViewById(R.id.radio_female);
        rg = (RadioGroup) view.findViewById(R.id.my_radio_group);
        firstName=(EditText) view.findViewById(R.id.first_name_form);
        lastName=(EditText) view.findViewById(R.id.last_name_form);

        spinner=(Spinner)view.findViewById(R.id.streams_spinner);
        btn=(Button)view.findViewById(R.id.submit);
        fragmentManager = getFragmentManager();
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Make sure that container activity implement the callback interface
        try {
            mCallback = (DataPassListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPassListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radio_male:
                        // do operations specific to this selection
                        gender = "Male";
                        Toast.makeText(getActivity(), "MALE IS SELECTED", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_female:
                        gender = "FeMale";
                        Toast.makeText(getActivity(), "FeMALE IS SELECTED", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
        spinner.setOnItemSelectedListener(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });
      setSpinner();
    }


    public void setSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ctx,
                R.array.streams, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
    public void chooseImage() {


        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                userImage = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));


                imageView.setImageBitmap(userImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

     public void submitData(){

         firstnameText=String.valueOf(firstName.getText());
         lastnameText=String.valueOf(lastName.getText());


         if((userImage!=null)&&!(String.valueOf(dateOfBirthTextView.getText()).isEmpty())&&(gender!=null)&&(stream!=null)&&(!firstnameText.isEmpty())&&(!lastnameText.isEmpty())) {

             Data data = new Data(userImage,firstnameText,lastnameText,String.valueOf(dateOfBirthTextView.getText()));
             ((FirstPage)getActivity()).setData(data);

              fragmentManager = getActivity().getSupportFragmentManager();

             fragmentManager.popBackStack();

         }
         else{
            Toast.makeText(ctx,"please fill the complete form",Toast.LENGTH_SHORT).show();

         }


     }
    public void getDate() {

        DialogFragment newFragment = new SelectDateFragment();

        newFragment.show(fragmentManager, "datePicker");

    }

    public static TextView getDateTextView() {
        return dateOfBirthTextView;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
   stream=String.valueOf(parent.getItemAtPosition(position));
        Toast.makeText(ctx,"value of stream is"+stream,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}