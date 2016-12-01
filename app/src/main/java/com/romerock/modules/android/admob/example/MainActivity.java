package com.romerock.modules.android.admob.example;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.romerock.modules.android.admob.Admob;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnAdmobSizes)
    Button btnAdmobSizes;
    @BindView(R.id.btnAdmobSimple)
    Button btnAdmobSimple;
    @BindView(R.id.btnAdmobInterstitial)
    Button btnAdmobInterstitial;
    @BindView(R.id.btnAdmobVideo)
    Button btnAdmobVideo;
    @BindView(R.id.btnNative)
    Button btnNative;
    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = getString(R.string.share_text_msn);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.romerock));
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_visitanos) {
            Uri uri = Uri.parse(getString(R.string.romerock_site)); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btnAdmobSizes, R.id.btnAdmobSimple, R.id.btnAdmobInterstitial, R.id.btnAdmobVideo, R.id.btnNative})
    public void onClick(View view) {
        int typeAdmob = 0;
        switch (view.getId()) {
            case R.id.btnAdmobSizes:
                typeAdmob = 1;
                break;
            case R.id.btnAdmobInterstitial:
                typeAdmob = 2;
                break;
            case R.id.btnAdmobVideo:
                typeAdmob = 3;
                break;
            case R.id.btnNative:
                typeAdmob = 4;
                break;
            case R.id.btnAdmobSimple:
            default:
                typeAdmob = 0;
                break;
        }
        Admob admob = new Admob(this, typeAdmob);
        if (typeAdmob != 2 && typeAdmob != 3) {
            View v = admob;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(v)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.getWindow().getAttributes().dimAmount = 0.7f;
            dialog.show();
        }
    }
}
