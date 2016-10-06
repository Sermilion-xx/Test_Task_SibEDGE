package com.sibedge.sibedge_test.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sibedge.sibedge_test.Activities.ScalingActivity;
import com.sibedge.sibedge_test.R;
import com.sibedge.sibedge_test.Utility.Utility;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.sibedge.sibedge_test.Utility.Utility.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE;


/**
 * Created by Sermilion on 05/10/2016.
 */

public class SibEDGE_ScalingFragment extends Fragment {

    private ImageButton galleryButton;
    private ImageButton cameraButton;
    private Uri fileUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scaling, container, false);
        galleryButton = (ImageButton) view.findViewById(R.id.gallery_button);
        cameraButton = (ImageButton) view.findViewById(R.id.camera_button);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = Utility.getOutputMediaFileUri(Utility.MEDIA_TYPE_IMAGE, "scale_picture");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < 19) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.please_choose_an_image)), Utility.GALLERY_INTENT_CALLED);
                } else {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.setType("image/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(intent, Utility.GALLERY_KITKAT_INTENT_CALLED);
                }
            }
        });
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == Utility.GALLERY_INTENT_CALLED && RESULT_OK == resultCode) {
                if (data != null) {
                    fileUri = data.getData();
                    processImageUri(fileUri);
                }
            } else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && RESULT_OK == resultCode) {
                if (fileUri != null) {
                    processImageUri(fileUri);
                }
            } else if (requestCode == Utility.GALLERY_KITKAT_INTENT_CALLED && RESULT_OK == resultCode) {
                if (data != null) {
                    fileUri = data.getData();
                    getActivity().grantUriPermission("com.sibedge.sibedge_test.Activities", fileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    processImageUri(fileUri);
                }
            }
            if (fileUri != null)
                getActivity().revokeUriPermission(fileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processImageUri(Uri fileUri) throws IOException {
        if (fileUri != null) {
            Intent intent = new Intent(getContext(), ScalingActivity.class);
            intent.putExtra("fileUri", fileUri);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.please_choose_an_image), Toast.LENGTH_LONG).show();
        }
    }

}
