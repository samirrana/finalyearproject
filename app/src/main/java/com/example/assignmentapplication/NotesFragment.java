package com.example.assignmentapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class NotesFragment extends Fragment {

    private EditText mResultEt;
    private ImageView mPreviewIv;

    private Button mImageRecognition;
    private Notes mNotes;
    private Button mSave;
    private Button mDeleteNotes;
    private TextView mHeader;
    private List<Notes> mNotez;
    private int index = 1;


    private static final String ARG_NOTES_ID = "notes_id";

    private static final int  CAMERA_REQUEST_CODE = 200;
    private static final int  STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;

    public static boolean isView;






    public static NotesFragment newInstance(UUID notesId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTES_ID,notesId);
        setIsView(false);
        NotesFragment fragment = new NotesFragment();
        fragment.setArguments(args);
        return fragment;


    }


    @Override
    public void onPause() {
        NotesLab.get(getActivity())
                .updateNotes(mNotes);
        super.onPause();



    }
    public static boolean isIsView() {
        return isView;
    }

    public static void setIsView(boolean isView) {
        NotesFragment.isView = isView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        UUID notesId = (UUID) getArguments().getSerializable(ARG_NOTES_ID);
        mNotes = NotesLab.get(getActivity()).getNotes(notesId);
        super.onCreate(savedInstanceState);


    }


/*    @Override
    public void onPause() {
        super.onPause();
        if(mResultEt.getText().length() < 1){
            NotesLab.get(getActivity())
                    .deleteNotes(mNotes);
        } else {
            NotesLab.get(getActivity())
                    .updateNotes(mNotes);
        }

    }*/

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notes_add, container, false);

        mResultEt = v.findViewById(R.id.result);
        mPreviewIv = v.findViewById(R.id.imageIv);

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE};

        mImageRecognition = v.findViewById(R.id.pic);

        mHeader = v.findViewById(R.id.heading);

        mHeader.setText("Notes " + index);



        mNotes.setHeader(mHeader.getText().toString());

        mResultEt.setText(mNotes.getText());








        mResultEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNotes.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mImageRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageImportDialog();
            }
        });

        mSave = v.findViewById(R.id.save);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                index +=1;
                NotesLab.get(getActivity())
                        .updateNotes(mNotes);
                Intent intent = new Intent(getActivity(), NotesActivity.class);
                startActivity(intent);

            }
        });

        mDeleteNotes = v.findViewById(R.id.delete);

        mDeleteNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotesLab.get(getActivity())
                        .deleteNotes(mNotes);
                Intent intent = new Intent(getActivity(), NotesActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void showImageImportDialog() {

        String[] items = { "Camera" , "Gallery" };
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    } else {
                        pickCamera();
                    }

                }
                if (which == 1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    } else {
                        pickGallery();
                    }
                    

                }

            }


        });

        dialog.create().show();

    }

    private void pickGallery() {

        Intent intent = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("images/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);

    }

    private void pickCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image To Text");
        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(getActivity(), storagePermission, STORAGE_REQUEST_CODE);

    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)== (PackageManager.PERMISSION_GRANTED);
        return result;
        
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(getActivity(), cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean resultl = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)== (PackageManager.PERMISSION_GRANTED);
        return result && resultl;
    }

    //handle permission result


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if(grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted){
                        pickCamera();
                    } else {
                        Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case STORAGE_REQUEST_CODE:
                if(grantResults.length > 0){
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted){
                        pickGallery();
                    } else {
                        Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if (requestCode == IMAGE_PICK_GALLERY_CODE){
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                    .start(getContext(), this);


            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE){
                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(getContext(),this);

            }




            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri resultUri = result.getUri();
                mPreviewIv.setImageURI(resultUri);

                BitmapDrawable bitmapDrawable = (BitmapDrawable)mPreviewIv.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getActivity().getApplicationContext()).build();

                if(!recognizer.isOperational()){
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i<items.size(); i++){
                        TextBlock myItem = items.valueAt(i);
                        sb.append(myItem.getValue());
                        sb.append("\n" + " \u2022 ");
                    }

                    mResultEt.setText(" \u2022 " + sb.toString());
                    mPreviewIv.setImageBitmap(bitmap);

                }
            }

        }
    }
}

