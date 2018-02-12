package com.alexsoft.homesolutions.counteraudit;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    NumberPicker npGasCounter1;
    NumberPicker npGasCounter2;
    NumberPicker npGasCounter3;
    NumberPicker npGasCounter4;
    NumberPicker npGasCounter5;
    EditText npGasCounterFraction;
    Button buttonSave;

    private static final String DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/Показания счетчиков/";
    private static final String FILENAME = "GasMeterReadings.csv";
    private static final String FILEPATH = DIRECTORY + FILENAME;


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

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());

        result = strDate + ","
            + String.valueOf(npGasCounter5.getValue())
            + String.valueOf(npGasCounter4.getValue())
            + String.valueOf(npGasCounter3.getValue())
            + String.valueOf(npGasCounter2.getValue())
            + String.valueOf(npGasCounter1.getValue())
            + "." + npGasCounterFraction.getText()
            + "\n";

        return result;
    }

    private void writeFile(String record) {
        try {

            FileWriter writer = new FileWriter(FILEPATH, true);
            writer.write(record);
            writer.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        try {
            File file = new File(FILEPATH);
            String lastLine = ReadLastLine(file);
            Toast.makeText(getApplicationContext(), lastLine.split(",")[1], Toast.LENGTH_SHORT).show();

            String gasCounterValue = lastLine.split(",")[1];
            npGasCounter5.setValue(Integer.parseInt(gasCounterValue.substring(0, 1)));
            npGasCounter4.setValue(Integer.parseInt(gasCounterValue.substring(1, 2)));
            npGasCounter3.setValue(Integer.parseInt(gasCounterValue.substring(2, 3)));
            npGasCounter2.setValue(Integer.parseInt(gasCounterValue.substring(3, 4)));
            npGasCounter1.setValue(Integer.parseInt(gasCounterValue.substring(4, 5)));
            npGasCounterFraction.setText(gasCounterValue.subSequence(6, 9));

        }
        catch (Exception e) {
        }
    }

    private static String ReadLastLine(File file) throws FileNotFoundException, IOException {
        String result = null;
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            long startIdx = file.length();
            while (startIdx >= 0 && (result == null || result.length() == 0)) {
                raf.seek(startIdx);
                if (startIdx > 0)
                    raf.readLine();
                result = raf.readLine();
                startIdx--;
            }
        }
        return result;
    }

    public void TestFilePathClick(View view) {
        //Toast.makeText(getApplicationContext(), getFileStreamPath(FILENAME).getAbsolutePath(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), FILEPATH, Toast.LENGTH_SHORT).show();

        readFile();
    }
}
