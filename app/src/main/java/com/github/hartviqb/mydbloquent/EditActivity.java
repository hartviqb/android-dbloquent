package com.github.hartviqb.mydbloquent;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by hartviq <apiq404@gmail.com> on 4/18/16.
 */
public class EditActivity extends Activity {
    private EditText edtName, edtPhone;
    private Button btnSave, btnDelete;
    private Integer primaryIDFromDB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        edtName = (EditText) findViewById(R.id.edit_edt_name);
        edtPhone = (EditText) findViewById(R.id.edit_edt_phone);
        btnSave = (Button) findViewById(R.id.edit_btn_save);
        btnDelete = (Button) findViewById(R.id.edit_btn_delete);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNewData(primaryIDFromDB);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(primaryIDFromDB);
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("id")){
            primaryIDFromDB = intent.getIntExtra("id", 0);
            loadExistingData(primaryIDFromDB);
        }
    }

    private void updateNewData(Integer idFromDb) {
        UserModel userModel = new UserModel(getApplicationContext());

        ContentValues updateData = new ContentValues();
        updateData.put("name", edtName.getText().toString());
        updateData.put("phone", Integer.valueOf(edtPhone.getText().toString()));

        userModel.findByID(idFromDb);
        if (userModel.update(updateData)) {
            finish();
        }
    }

    private void deleteData(Integer idFromDb) {
        UserModel userModel = new UserModel(getApplicationContext());
        userModel.findByID(idFromDb);
        if (userModel.delete()) {
            finish();
        }
    }

    private void loadExistingData(Integer idFromDb){
        UserModel userModel = new UserModel(getApplicationContext());
        ContentValues dataExisting = userModel.findByID(idFromDb);
        edtName.setText(dataExisting.getAsString("name"));
        edtPhone.setText(dataExisting.getAsString("phone"));

    }
}