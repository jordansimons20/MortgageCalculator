package edu.suffolk.jsimons3.mortgagecalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.text.NumberFormat; // for currency formatting
import android.app.Activity; // base class for activities
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing custom tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.widget.TextView; // for displaying text

public class MainActivity extends AppCompatActivity {

    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();

    private EditText purchasepricenum;
    private EditText downpaymentnum;
    private EditText interestratenum;

    private TextView tenyearsnum;
    private TextView twentyyearsnum;
    private TextView thirtyyearsnum;
    private TextView purchasepricetextview;
    private TextView interestratetextview;
    private TextView downpaymenttextview;
    private TextView custompaymentview;
    private TextView customamountnum;

    private double loanamount, purchaseprice, downpayment, customyears = 0;
    private double interestrate = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        purchasepricenum =
                (EditText) findViewById(R.id.purchasepricenum);
        purchasepricenum.addTextChangedListener(purchasepricenumWatcher);
        //purchasepricenum.setVisibility(View.INVISIBLE);



        downpaymentnum =
                (EditText) findViewById(R.id.downpaymentnum);
        downpaymentnum.addTextChangedListener(downpaymentnumWatcher);

        interestratenum =
                (EditText) findViewById(R.id.interestratenum);
        interestratenum.addTextChangedListener(interestratenumWatcher);

        purchasepricetextview =
                (TextView) findViewById(R.id.purchasepricetextview);

        interestratetextview =
                (TextView) findViewById(R.id.interestratetextview);

        downpaymenttextview =
                (TextView) findViewById(R.id.downpaymenttextview);

        tenyearsnum =
                (TextView) findViewById(R.id.tenyearsnum);

        twentyyearsnum =
                (TextView) findViewById(R.id.twentyyearsnum);

        thirtyyearsnum =
                (TextView) findViewById(R.id.thirtyyearsnum);

        custompaymentview =
                (TextView) findViewById(R.id.custompaymentview);

        customamountnum =
                (TextView) findViewById(R.id.customamountnum);

        SeekBar customYearSeekBar =
                (SeekBar) findViewById(R.id.loanseekBar);
        customYearSeekBar.setOnSeekBarChangeListener(customSeekBarListener);



    }

    private void updateMonthyPayment()
    {
       java.lang.Double tensum = loanamount / 120;
        java.lang.Double twentysum = loanamount / 240;
        java.lang.Double thirtysum = loanamount / 360;

        tenyearsnum.setText( currencyFormat.format(tensum) ); //loanamount / months in years.
        twentyyearsnum.setText(currencyFormat.format(twentysum));
        thirtyyearsnum.setText(currencyFormat.format(thirtysum));
    }

    private void updateCustomPayment()
    {
        if (customyears == 0) //Handles infinite display error.
        {
            custompaymentview.setText(currencyFormat.format(0));
        }
        else
        {
            java.lang.Double Months = customyears * 12;
            java.lang.Double CustomSum = loanamount / Months;
            custompaymentview.setText(currencyFormat.format(CustomSum));
        }
    }

    private TextWatcher purchasepricenumWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        // called when the user enters a number
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try
            {
                purchasepricetextview.setText(purchasepricenum.getText());
                purchaseprice = Double.parseDouble(s.toString());
                loanamount = (purchaseprice - downpayment) * interestrate;
            }

            catch(Exception e)
            {
                purchasepricetextview.setText("0");
                purchaseprice = 0;
                loanamount = 0;
            }

            updateMonthyPayment();
            updateCustomPayment();
            //update views
        } // end method onTextChanged

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher downpaymentnumWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        // called when the user enters a number
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try
            {
                downpaymenttextview.setText(downpaymentnum.getText());
                downpayment = Double.parseDouble(s.toString());
                loanamount = (purchaseprice - downpayment) * interestrate;
            }
            catch (Exception e)
            {
                downpaymenttextview.setText("0");
                downpayment = 0;
                loanamount = (purchaseprice - downpayment) * interestrate;
            }

            updateMonthyPayment();
            updateCustomPayment();
            //update views
        } // end method onTextChanged

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher interestratenumWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        // called when the user enters a number
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try {

                interestratetextview.setText(interestratenum.getText());
                interestrate = Double.parseDouble(s.toString());
                loanamount = (purchaseprice - downpayment) * interestrate;
            }

            catch(Exception e)
            {
                interestratetextview.setText("1");
                interestrate = 1;
                loanamount = (purchaseprice - downpayment) * interestrate;

            }

            updateMonthyPayment();
            updateCustomPayment();
            //update views
        } // end method onTextChanged

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private OnSeekBarChangeListener customSeekBarListener =
            new OnSeekBarChangeListener()
            {
                // update customPercent, then call updateCustom
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser)
                {
                    java.lang.Integer newprogress = progress + 10;
                    customamountnum.setText((newprogress.toString()));
                    customyears = newprogress;
                    updateCustomPayment();
                } // end method onProgressChanged

                @Override
                public void onStartTrackingTouch(SeekBar seekBar)
                {
                } // end method onStartTrackingTouch

                @Override
                public void onStopTrackingTouch(SeekBar seekBar)
                {
                } // end method onStopTrackingTouch
            }; // end OnSeekBarChangeListener




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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

