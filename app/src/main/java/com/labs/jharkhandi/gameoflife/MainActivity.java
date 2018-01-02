package com.labs.jharkhandi.gameoflife;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BulletSpan;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    final private int INTERVAL = 250;

    private GameModel model;
    private GameModel chooserModel;

    private GameView gameView;
    private GameView aliveCellChooser;

    private Button startGameButton;
    private TextView clearButton;
    private TextView stopGameButton;

    private boolean stopFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();

        stopFlag = false;

        model = new GameModel(30, 15);
        chooserModel = new GameModel(3,3);

        gameView.setUp(model, 20, false);
        aliveCellChooser.setUp(chooserModel, 80, true);

        startGameButton.setOnClickListener(mOnClickListener);
        clearButton.setOnClickListener(mOnClickListener);
        stopGameButton.setOnClickListener(mOnClickListener);
    }

    private void bindViews(){
        gameView = findViewById(R.id.game_view);
        aliveCellChooser =  findViewById(R.id.alive_cell_chooser);
        startGameButton = findViewById(R.id.start_button);
        clearButton = findViewById(R.id.clear_button);
        stopGameButton = findViewById(R.id.stop_game_button);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.clear_button:
                    chooserModel.reset();
                    aliveCellChooser.invalidate();
                    break;
                case R.id.start_button:
                    startLifeOfCycle();
                    break;
                case R.id.stop_game_button:
                    stopFlag = true;
            }
        }
    };

    private void startLifeOfCycle() {
        model.reset();
        GameModel.copyModelState(chooserModel, model);

        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(model.next() && ! stopFlag){
                    gameView.invalidate();
                    handler.postDelayed(this, INTERVAL);
                }else{
                    stopFlag = false;
                }
            }
        };
        handler.postDelayed(runnable, INTERVAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_info:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getSpannableForInfo())
                        .setPositiveButton("OK", null)
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public CharSequence getSpannableForInfo(){
        String plainText = getString(R.string.info);

        int titleStart = 0;
        int titleEnd = plainText.indexOf("\n");

        int descStart = titleEnd + 1;
        int descEnd = plainText.indexOf("\n", descStart);

        int desc2Start = descEnd + 1;
        int desc2End = plainText.indexOf("\n", desc2Start);

        int firstPointStart = desc2End + 1;
        int firstPointEnd = plainText.indexOf("\n",firstPointStart);

        int secondPointStart = firstPointEnd + 1;
        int secondPointEnd = plainText.indexOf("\n", secondPointStart);

        int thirdPointStart = secondPointEnd + 1;
        int thirdPointEnd = plainText.indexOf("\n", thirdPointStart);

        int fourthPointStart = thirdPointEnd + 1;

        SpannableString spannablePart1 = new SpannableString(plainText.substring(0, titleEnd));

        spannablePart1.setSpan(new TextAppearanceSpan(this, R.style.Heading),
                titleStart, titleEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString spannableDesc = new SpannableString(Html.fromHtml(plainText.substring(descStart, descEnd)));
        spannableDesc.setSpan(new TextAppearanceSpan(this, R.style.DescText),
                0, descEnd-descStart, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString spannableDesc2 = new SpannableString(plainText.substring(desc2Start, desc2End));
        spannableDesc2.setSpan(new TextAppearanceSpan(this, R.style.DescText),
                0, desc2End-desc2Start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString spannablePart2 = new SpannableString(plainText.substring(firstPointStart, firstPointEnd));
        spannablePart2.setSpan(new BulletSpan(15, getResources().getColor(R.color.cust_grey)),
                0, firstPointEnd-firstPointStart, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannablePart2.setSpan(new TextAppearanceSpan(this, R.style.DescText),
                0, firstPointEnd-firstPointStart, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString spannablePart3 = new SpannableString(plainText.substring(secondPointStart, secondPointEnd));
        spannablePart3.setSpan(new BulletSpan(15, getResources().getColor(R.color.cust_grey)),
                0, secondPointEnd-secondPointStart, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannablePart3.setSpan(new TextAppearanceSpan(this, R.style.DescText),
                0, secondPointEnd-secondPointStart, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString spannablePart4 = new SpannableString(plainText.substring(thirdPointStart, thirdPointEnd));
        spannablePart4.setSpan(new BulletSpan(15, getResources().getColor(R.color.cust_grey)),
                0, thirdPointEnd-thirdPointStart, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannablePart4.setSpan(new TextAppearanceSpan(this, R.style.DescText),
                0, thirdPointEnd-thirdPointStart, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        String strPart5 = plainText.substring(fourthPointStart);
        SpannableString spannablePart5 = new SpannableString(strPart5);
        spannablePart5.setSpan(new BulletSpan(15, getResources().getColor(R.color.cust_grey)),
                0, strPart5.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannablePart5.setSpan(new TextAppearanceSpan(this, R.style.DescText),
                0, strPart5.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return TextUtils.concat(spannablePart1,"\n" ,
                spannableDesc2, "\n",
                spannablePart2, "\n",
                spannablePart3, "\n",
                spannablePart4, "\n",
                spannablePart5, "\n\n",
                spannableDesc);
    }
}
