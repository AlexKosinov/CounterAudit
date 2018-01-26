package com.alexsoft.homesolutions.counteraudit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity{

    NumberPicker npGasCounter1;
    NumberPicker npGasCounter2;
    NumberPicker npGasCounter3;
    NumberPicker npGasCounter4;
    NumberPicker npGasCounter5;
    EditText npGasCounterFraction;
    Button buttonSave;

    //private String directory = getFilesDir();
    private static final String FILENAME = "GasMeterReadings.csv";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitvity_main);

        initGasCounter();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initGasCounter() {
        npGasCounter1 = findViewById(R.id.npGasCounter1);
        npGasCounter2 = findViewById(R.id.npGasCounter2);
        npGasCounter3 = findViewById(R.id.npGasCounter3);
        npGasCounter4 = findViewById(R.id.npGasCounter4);
        npGasCounter5 = findViewById(R.id.npGasCounter5);
        npGasCounterFraction = findViewById(R.id.npGasCounterFraction);
        buttonSave = findViewById(R.id.buttonSave);

        npGasCounter1.setMinValue(0);
        npGasCounter1.setMaxValue(9);

        npGasCounter2.setMinValue(0);
        npGasCounter2.setMaxValue(9);

        npGasCounter3.setMinValue(0);
        npGasCounter3.setMaxValue(9);

        npGasCounter4.setMinValue(0);
        npGasCounter4.setMaxValue(9);

        npGasCounter5.setMinValue(0);
        npGasCounter5.setMaxValue(9);

        buttonSave.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                writeFile(getGasCounterText());
                Toast.makeText(getApplicationContext(), R.string.save_success, Toast.LENGTH_SHORT).show();
            }
        });

        npGasCounter1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Toast.makeText(getApplicationContext(), getGasCounterText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    private String getGasCounterText() {

        String result = "";

        result = String.valueOf(npGasCounter5.getValue());
        /*
        result.concat(String.valueOf(npGasCounter5.getValue()))
              .concat(String.valueOf(npGasCounter4.getValue()))
              .concat(String.valueOf(npGasCounter3.getValue()))
              .concat(String.valueOf(npGasCounter2.getValue()))
              .concat(String.valueOf(npGasCounter1.getValue()))
              .concat("." + npGasCounterFraction.toString());
        */
        return result;
    }

    private void writeFile(String record) {
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAME, MODE_PRIVATE)));
            // пишем данные
            bw.write(record);
            // закрываем поток
            bw.close();
            //Log.d(LOG_TAG, "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void TestFilePathClick(View view) {
        Toast.makeText(getApplicationContext(), getFileStreamPath(FILENAME).getAbsolutePath(), Toast.LENGTH_SHORT).show();
    }
}
