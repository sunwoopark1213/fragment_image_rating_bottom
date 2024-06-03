package com.example.fragment_image_rating_bottom;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultTextView = findViewById(R.id.result_text_view);

        String type = getIntent().getStringExtra("type");
        if ("history".equals(type)) {
            // 히스토리 표시
            List<Float> history = (List<Float>) getIntent().getSerializableExtra("history");
            StringBuilder historyText = new StringBuilder("Rating History:\n");
            for (float rating : history) {
                historyText.append(rating).append("\n");
            }
            resultTextView.setText(historyText.toString());
        } else if ("average".equals(type)) {
            // 평균 별점 표시
            float averageRating = getIntent().getFloatExtra("average", 0);
            resultTextView.setText("Average Rating: " + averageRating);
        } else {
            // 평가 결과 표시
            float rating = getIntent().getFloatExtra("rating", 0);
            resultTextView.setText("Rating: " + rating);
        }
    }
}
