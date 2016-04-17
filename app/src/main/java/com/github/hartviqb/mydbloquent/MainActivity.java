package com.github.hartviqb.mydbloquent;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.github.hartviqb.dbloquent.DbLoquent;
import com.github.hartviqb.dbloquent.DbLoquentMigration;
import com.github.hartviqb.dbloquent.helper.DatabaseConfig;
import com.github.hartviqb.mydbloquent.adapter.UserAdapter;
import com.github.hartviqb.mydbloquent.data.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hartviq <apiq404@gmail.com> on 4/17/16.
 */
public class MainActivity extends Activity {
    private ListView lstView;
    private Button btnCreate, btnDeleteAll;
    private UserAdapter userAdapter;
    private List<UserData> userDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstView = (ListView) findViewById(R.id.listView);
        btnCreate = (Button) findViewById(R.id.btn_create);
        btnDeleteAll = (Button) findViewById(R.id.btn_delete_all);

        userDatas = new ArrayList<>();
        userAdapter = new UserAdapter(getApplicationContext(), userDatas);
        lstView.setAdapter(userAdapter);

        new DbLoquent(getApplicationContext()).init(new DatabaseConfig() {
            @Override
            public Integer dbVersion() {
                return 1;
            }

            @Override
            public String dbName() {
                return "test_db";
            }

            @Override
            public List<DbLoquentMigration> addMigrationHelpers() {
                List<DbLoquentMigration> migrtaionList = new ArrayList<DbLoquentMigration>();

                /*---------------------------------------------------
                    please add list your migration class in here
                ---------------------------------------------------*/
                migrtaionList.add(new UserMigration());

                return migrtaionList;
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();
            }
        });

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserData data = userDatas.get(position);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("id", data.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDB();
    }

    private void loadDB() {
        UserModel userModel = new UserModel(getApplicationContext());
        List<ContentValues> dataArrays = userModel.get();
        userDatas.clear();
        for (int i = 0; i < dataArrays.size(); i++) {
            ContentValues data = dataArrays.get(i);
            UserData newData = new UserData();
            newData.setId(data.getAsInteger("id"));
            newData.setName(data.getAsString("name"));
            newData.setPhone(data.getAsInteger("phone"));
            userDatas.add(newData);
        }
        userAdapter.notifyDataSetChanged();
    }



    private void clearAll() {
        UserModel userModel = new UserModel(getApplicationContext());
        userModel.get();
        userModel.deleteAll();
        loadDB();
    }
}
