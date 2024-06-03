package com.example.fragment_image_rating_bottom;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ImageFragment extends Fragment {

    private static final String ARG_IMAGE_RES = "image_res";
    private static final String ARG_IMAGE_DESC = "image_desc";
    private boolean isImageZoomed = false;

    public static ImageFragment newInstance(int imageRes, String imageDesc) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_RES, imageRes);
        args.putString(ARG_IMAGE_DESC, imageDesc);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView imageView = view.findViewById(R.id.image_view);
        RatingBar ratingBar = view.findViewById(R.id.rating_bar);
        TextView descriptionTextView = view.findViewById(R.id.description_text_view);

        if (getArguments() != null) {
            int imageRes = getArguments().getInt(ARG_IMAGE_RES);
            String imageDesc = getArguments().getString(ARG_IMAGE_DESC);
            imageView.setImageResource(imageRes);
            descriptionTextView.setText(imageDesc);
        }

        // 이미지 클릭 이벤트 리스너
        imageView.setOnClickListener(v -> {
            if (isImageZoomed) {
                imageView.setScaleX(1.0f);
                imageView.setScaleY(1.0f);
            } else {
                imageView.setScaleX(2.0f);
                imageView.setScaleY(2.0f);
            }
            isImageZoomed = !isImageZoomed;
        });

        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            if (fromUser) {
                // 히스토리에 별점 추가
                MainActivity.addRatingToHistory(rating);

                // ResultActivity 시작
                Intent intent = new Intent(getActivity(), ResultActivity.class);
                intent.putExtra("rating", rating);
                startActivity(intent);
            }
        });

        return view;
    }
}
