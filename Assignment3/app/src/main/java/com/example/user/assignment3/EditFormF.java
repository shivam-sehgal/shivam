package com.example.user.assignment3;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
public class EditFormF extends Fragment implements  AdapterView.OnItemSelectedListener{
private int position;
    private final int PICK_IMAGE_REQUEST = 1;
    private Bitmap editedImage;
    private ImageView userEditImageView;
    private TextView FirstName;
    private TextView LastName;
    private Button changeDate;
    public static TextView setDateOfBirth;
    private RadioGroup editRadioGroup;
    private String editedStream;
    private RadioButton editMaleRadio;
    private RadioButton editFemaleRadio;
    private Spinner spinner;
    private Button update;
    private FragmentManager fragmentManager;
    private String gender;
    private String dateOfBirth;
    private Context ctx;
    private String firstNameText;
    private String lastNameText;
    public EditFormF() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_edit_form, container, false);
        position=getArguments().getInt("position",-1);
        userEditImageView=(ImageView)view.findViewById(R.id.user_image_edit);
        FirstName=(TextView)view.findViewById(R.id.first_name_edit);
        LastName=(TextView)view.findViewById(R.id.last_name_edit);
        changeDate=(Button)view.findViewById(R.id.date_of_birth_edit_btn);
        setDateOfBirth=(TextView)view.findViewById(R.id.date_birth_tv_edit);
        fragmentManager = getFragmentManager();
        editRadioGroup=(RadioGroup)view.findViewById(R.id.my_radio_group_edit);
        editMaleRadio=(RadioButton)view.findViewById(R.id.radio_male_edit);
        editFemaleRadio=(RadioButton)view.findViewById(R.id.radio_female_edit);
        spinner=(Spinner)view.findViewById(R.id.streams_spinner_edit);
        update=(Button)view.findViewById(R.id.update);
        ctx=getContext();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
       super.onActivityCreated(savedInstanceState);
        Data data=((FirstPage)getActivity()).getData();
        userEditImageView.setImageBitmap(data.getImageID());
        FirstName.setText(data.getFirstName());
        LastName.setText(data.getLastName());
        setDateOfBirth.setText(data.getDateofBirth());
        userEditImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });

        editRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radio_male_edit:
                        // do operations specific to this selection
                        gender = "Male";
                        Toast.makeText(getActivity(), "MALE IS SELECTED", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_female_edit:
                        gender = "FeMale";
                        Toast.makeText(getActivity(), "FeMALE IS SELECTED", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
        spinner.setOnItemSelectedListener(this);
        setSpinner();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });


    }

    public void setSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ctx,
                R.array.streams, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    public void getDate() {

        DialogFragment newFragment = new EditSelectedDateFragment();

        newFragment.show(fragmentManager, "datePicker");

    }


    public static TextView getDateTextView() {
        return setDateOfBirth;

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
                editedImage = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));


                userEditImageView.setImageBitmap(editedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        editedStream=String.valueOf(parent.getItemAtPosition(position));
        Toast.makeText(ctx,"value of stream is"+editedStream,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void updateData(){
        firstNameText=String.valueOf(FirstName.getText());
        lastNameText=String.valueOf(LastName.getText());
        dateOfBirth=String.valueOf(setDateOfBirth.getText());


        if((editedImage!=null)&&!(dateOfBirth.isEmpty())&&(gender!=null)&&(editedStream!=null)&&(!firstNameText.isEmpty())&&(!lastNameText.isEmpty())) {

            Data data=new Data(editedImage,firstNameText,lastNameText,dateOfBirth);
            ((FirstPage)getActivity()).setData(data);
            ((FirstPage)getActivity()).setPosition(position);

            fragmentManager = getActivity().getSupportFragmentManager();

            fragmentManager.popBackStack();

        }
        else{
            Toast.makeText(ctx,"please fill the complete form",Toast.LENGTH_SHORT).show();

        }





    }
}
