package com.example.room;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.example.room.databinding.ActivityOperationBinding;

/**
 * @author V
 * @since 2018/10/22
 */
public class OperationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityOperationBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_operation);
        final OperationModel model = new OperationModel(this);
        dataBinding.setModel(model);

        dataBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.getOperation().set(((DatabaseOperation) parent.getAdapter().getItem(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, OperationActivity.class);
        context.startActivity(starter);
    }
}
