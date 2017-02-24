package com.blakequ.jsonparser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.blakequ.jsonparser.model.DoctorEntity;
import com.blakequ.jsonparser.model.SimpleEntity;
import com.blakequ.jsonparser.model.UserEntity;
import com.blakequ.jsonparser.model.UserEntity2;
import com.blakequ.parser.BindView;
import com.blakequ.parser.JsonParserUtils;
import com.blakequ.parser.ResourceUtils;
import com.blakequ.parser.ViewInjectUtils;

import org.json.JSONException;

import java.util.List;

public class MainActivity extends AppCompatActivity {
	@BindView(id = R.id.tv_content)
	TextView mTvView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		ViewInjectUtils.initBindView(this);
		initData();
    }

    public void initData() {
        // TODO Auto-generated method stub
		long time = System.currentTimeMillis();
		long currentTime = 0;
		StringBuilder sb = new StringBuilder();

		try {
//        测试json解析
			sb.append("------parser json_error.txt---------\n");
			String str1 = ResourceUtils.geFileFromAssets(this, "json_error.txt");
			SimpleEntity status = (SimpleEntity) JsonParserUtils.initEntityParser(SimpleEntity.class, str1);
			sb.append(status);

			currentTime = System.currentTimeMillis();
			System.out.println("------------------"+(currentTime-time));
			time = currentTime;

			sb.append("\n\n------parser json_list.txt---------\n");
			String str2 = ResourceUtils.geFileFromAssets(this, "json_list.txt");
			List<UserEntity> user = (List<UserEntity>) JsonParserUtils.initEntityParser(UserEntity.class, str2, true);
			if (user != null) {
				for (int  i= 0;  i< user.size(); i++) {
					sb.append(i+": "+user.get(i)+"\n");
				}
			}


			currentTime = System.currentTimeMillis();
			System.out.println("------------------"+(currentTime-time));
			time = currentTime;

			sb.append("\n------parser json_normal.txt---------\n");
			String str3 = ResourceUtils.geFileFromAssets(this, "json_normal.txt");
			UserEntity2 user2 = (UserEntity2) JsonParserUtils.initEntityParser(UserEntity2.class, str3);
			sb.append(user2);

			currentTime = System.currentTimeMillis();
			System.out.println("------------------"+(currentTime-time));
			time = currentTime;

			sb.append("\n\n------parser json_object_normal.txt---------\n");
			String str4 = ResourceUtils.geFileFromAssets(this, "json_object_normal.txt");
			List<DoctorEntity> doctor = (List<DoctorEntity>) JsonParserUtils.initEntityParser(DoctorEntity.class, str4);
			if (doctor != null) {
				for (int  i= 0;  i< doctor.size(); i++) {
					sb.append(i+": "+doctor.get(i)+"\n");
				}
			}

			currentTime = System.currentTimeMillis();
			System.out.println("------------------"+(currentTime-time));

			mTvView.setText(sb.toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
