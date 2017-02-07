package appewtc.masterung.sortingthai;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }   // Main Method

    public void clickPlay(View view) {

        startActivity(new Intent(MainActivity.this, SortListView.class));
        finish();

    }   // clickPlay

    public void clickExit(View view) {
        finish();
    }


}   // Main Class
