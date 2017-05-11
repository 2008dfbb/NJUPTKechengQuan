package com.example.njuptkechengquan;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {
    LinearLayout weekPanels[] = new LinearLayout[7];
    List courseData[] = new ArrayList[7];
    int itemHeight;
    int marTop, marLeft;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.settings:
                Toast.makeText(this,"You click Settings",Toast.LENGTH_SHORT).show();
                break;
            case R.id.swinfo:
                Toast.makeText(this,"You click swinfo",Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Toast.makeText(this,"You click Share",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
        //
        itemHeight = getResources().getDimensionPixelSize(R.dimen.weekItemHeight);
        marTop = getResources().getDimensionPixelSize(R.dimen.weekItemMarTop);
        marLeft = getResources().getDimensionPixelSize(R.dimen.weekItemMarLeft);

        //数据
        getData();

        for (int i = 0; i < weekPanels.length; i++) {
            weekPanels[i] = (LinearLayout) findViewById(R.id.weekPanel_1 + i);
            initWeekPanel(weekPanels[i], courseData[i]);
        }

    }

    public void getData() {
        List<Course> list1 = new ArrayList<Course>();
        Course c1 = new Course("软件工程", "教1-403", 1, 4, "Kongo", "1002");
        list1.add(c1);
        list1.add(new Course("C语言", "教4-201", 6, 3, "Atogo", "1001"));
        courseData[0] = list1;

        List<Course> list2 = new ArrayList<Course>();
        list2.add(new Course("计算机组成原理", "教4-201", 6, 3, "Haruna", "1001"));
        courseData[1] = list2;

        List<Course> list3 = new ArrayList<Course>();
        list3.add(new Course("数据库原理", "教4-201", 2, 3, "Azusa", "1008"));
        list3.add(new Course("计算机网络", "教4-201", 6, 2, "Aoba", "1009"));
        list3.add(new Course("时间众筹学", "中南海", 9, 3, "Ha", "1039"));
        courseData[2] = list3;

        List<Course> list4 = new ArrayList<Course>();
        list4.add(new Course("数据结构", "教4-201", 5, 3, "Shigure", "1012"));
        list4.add(new Course("操作系统", "教5-101", 6, 3, "Yamato", "1014"));
        courseData[3] = list4;

        List<Course> list5 = new ArrayList<Course>();
        list5.add(new Course("Android开发", "教4-201", 1, 4, "Shimakaze", "1250"));
        list5.add(new Course("CSGO P90战术思路", "Katowice", 8, 4, "captainMO", "1251"));
        courseData[4] = list5;
    }

    public void initWeekPanel(LinearLayout ll, List<Course> data) {
        if (ll == null || data == null || data.size() < 1) return;
        Log.i("Msg", "初始化面板");
        Course pre = data.get(0);
        for (int i = 0; i < data.size(); i++) {
            Course c = data.get(i);
            TextView tv = new TextView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    itemHeight * c.getStep() + marTop * (c.getStep() - 1));
            if (i > 0) {
                lp.setMargins(marLeft, (c.getStart() - (pre.getStart() + pre.getStep())) * (itemHeight + marTop) + marTop, 0, 0);
            } else {
                lp.setMargins(marLeft, (c.getStart() - 1) * (itemHeight + marTop) + marTop, 0, 0);
            }
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.TOP);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextSize(12);
            tv.setTextColor(getResources().getColor(R.color.courseTextColor));
            tv.setText(c.getName() + "\n" + c.getRoom() + "\n" + c.getTeach());
            tv.setBackgroundColor(getResources().getColor(R.color.actionBarBg));
            // tv.setBackground(getResources().getDrawable(R.drawable.tvshape));
            ll.addView(tv);
            pre = c;
        }
    }
}
