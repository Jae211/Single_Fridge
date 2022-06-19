package org.techtown.Single_Fridge;

import android.app.Activity;
import android.os.Bundle;

public class Fridge_remove extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setResult(RESULT_OK);
        finish();
        return;
    }
}
