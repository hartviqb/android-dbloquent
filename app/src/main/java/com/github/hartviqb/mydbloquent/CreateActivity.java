package com.github.hartviqb.mydbloquent;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by hartviq <apiq404@gmail.com> on 4/18/16.
 */
public class CreateActivity extends Activity {
    private EditText edtName, edtPhone;
    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);
        edtName = (EditText) findViewById(R.id.create_edt_name);
        edtPhone = (EditText) findViewById(R.id.create_edt_phone);
        btnSave = (Button) findViewById(R.id.create_btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewData();
            }
        });
    }

    private void createNewData() {
        UserModel userModel = new UserModel(getApplicationContext());
        ContentValues createDate = new ContentValues();
        createDate.put("name", edtName.getText().toString());
        createDate.put("phone", Integer.valueOf(edtPhone.getText().toString()));
        if (userModel.create(createDate) > 0){
            finish();
        }
    }
}
