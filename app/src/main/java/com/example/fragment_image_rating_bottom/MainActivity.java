package com.example.fragment_image_rating_bottom;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static List<Float> ratingHistory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            // 하단 탭 클릭에 따라 프래그먼트 전환
            if (item.getItemId() == R.id.nav_image1) {
                selectedFragment = ImageFragment.newInstance(R.drawable.ic_image1, "Description for Image 1");
            } else if (item.getItemId() == R.id.nav_image2) {
                selectedFragment = ImageFragment.newInstance(R.drawable.ic_image2, "Description for Image 2");
            } else if (item.getItemId() == R.id.nav_image3) {
                selectedFragment = ImageFragment.newInstance(R.drawable.ic_image3, "Description for Image 3");
            }

            // 선택된 프래그먼트로 전환
            if (selectedFragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, selectedFragment);
                fragmentTransaction.commit();
            }
            return true;
        });

        // 초기 프래그먼트를 설정
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_image1);
        }
    }

    // 옵션 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    // 옵션 메뉴 선택시 이벤트 처리
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_history) {
            // 히스토리 보기 기능
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("type", "history");
            intent.putExtra("history", new ArrayList<>(ratingHistory));
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.menu_average) {
            // 평균 별점 보기 기능
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("type", "average");
            float averageRating = calculateAverageRating();
            intent.putExtra("average", averageRating);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 별점 히스토리에 추가
    public static void addRatingToHistory(float rating) {
        ratingHistory.add(rating);
    }

    // 평균 별점 계산
    private float calculateAverageRating() {
        if (ratingHistory.isEmpty()) {
            return 0;
        }
        float sum = 0;
        for (float rating : ratingHistory) {
            sum += rating;
        }
        return sum / ratingHistory.size();
    }
}
