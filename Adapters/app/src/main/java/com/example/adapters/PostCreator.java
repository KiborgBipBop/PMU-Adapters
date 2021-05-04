package com.example.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class PostCreator extends Fragment implements View.OnClickListener{

    private ImageButton buttonBrowse;
    private ImageButton buttonClose;
    private ImageButton buttonConfirm;

    private Uri imageUri = null;
    private EditText headerText;
    private EditText descText;

    private boolean checkImage = false;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        container.setClickable(true);
        View v = inflater.inflate(R.layout.fragment_post_creator, container, false);
        buttonBrowse = v.findViewById(R.id.post_browse);
        buttonClose = v.findViewById(R.id.button_close_post);
        buttonConfirm = v.findViewById(R.id.button_send_post);

        headerText = v.findViewById(R.id.post_header);
        descText = v.findViewById(R.id.post_desc);

        buttonBrowse.setOnClickListener(this);
        buttonClose.setOnClickListener(this);
        buttonConfirm.setOnClickListener(this);

        buttonBrowse.setClipToOutline(true);

        return v;
    }


    @Override
    public void onClick(View view)
    {

        OnButtonClickListener listener = (OnButtonClickListener) getActivity();
        switch (view.getId())
        {
            case R.id.post_browse:
                pickImageFromGallery();
                break;
            case R.id.button_send_post:
                boolean isCorrect = true;

                if (headerText.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(), "Header can not be empty", Toast.LENGTH_LONG).show();
                    break;
                }
                if (imageUri == null)
                {
                    Toast.makeText(getActivity(), "Pick an image", Toast.LENGTH_LONG).show();
                    break;
                }
                if (descText.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(), "Description can not be empty", Toast.LENGTH_LONG).show();
                    break;
                }

                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                listener.onConfirmButtonClicked(headerText.getText().toString(), imageUri, descText.getText().toString());
                break;
            case R.id.button_close_post:
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                listener = (OnButtonClickListener) getActivity();
                listener.onCloseButtonClicked();
                break;
        }

    }

    private void pickImageFromGallery()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //Bitmap selectedImage;
        if (resultCode == RESULT_OK)
        {

            switch (requestCode)
            {
                case 0:
                    try
                    {
                        imageUri = data.getData();
                        InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(inputStream);

                        buttonBrowse.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        buttonBrowse.setImageBitmap(selectedImage);
                        checkImage = true;
                        //buttonConfirm.setVisibility(View.VISIBLE);
                    } catch (FileNotFoundException | NullPointerException e)
                    {
                        e.printStackTrace();
                    }

            }

        }
    }

    public interface OnButtonClickListener
    {
        void onCloseButtonClicked();

        void onConfirmButtonClicked(String header, Uri imageUri, String desc);
    }
}