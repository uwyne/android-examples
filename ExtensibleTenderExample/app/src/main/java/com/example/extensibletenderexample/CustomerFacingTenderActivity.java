package com.example.extensibletenderexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.clover.sdk.v1.Intents;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.util.Currency;

/**
 * Created by mmaietta on 8/16/15.
 */
public class CustomerFacingTenderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_tender1);

        setResult(RESULT_CANCELED);

        setSystemUiVisibility();

        final long amount = getIntent().getLongExtra(Intents.EXTRA_AMOUNT, 0);
        final Currency currency = (Currency) getIntent().getSerializableExtra(Intents.EXTRA_CURRENCY);
        final String orderId = getIntent().getStringExtra(Intents.EXTRA_ORDER_ID);
        final String merchantId = getIntent().getStringExtra(Intents.EXTRA_MERCHANT_ID);

        TextView amountText = (TextView) findViewById(R.id.text_amount);
        amountText.setText(longToAmountString(currency, amount));

        TextView orderIdText = (TextView) findViewById(R.id.text_orderid);
        orderIdText.setText(orderId);
        TextView merchantIdText = (TextView) findViewById(R.id.text_merchantid);
        merchantIdText.setText(merchantId);

        Button approveButton = (Button) findViewById(R.id.acceptButton);
        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(Intents.EXTRA_AMOUNT, amount);
                data.putExtra(Intents.EXTRA_CLIENT_ID, nextSampleId());
                data.putExtra(Intents.EXTRA_NOTE, "Thanks for using Test Tender!");

                setResult(RESULT_OK, data);
                finish();
            }
        });

        Button declineButton = (Button) findViewById(R.id.declineButton);
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(Intents.EXTRA_DECLINE_REASON, "You pressed the decline button");

                setResult(RESULT_CANCELED, data);
                finish();
            }
        });
    }

    private String nextSampleId() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    public String longToAmountString(Currency currency, long amt) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        if (currency != null)
            format.setCurrency(currency);

        double currencyAmount = (double) amt / Math.pow(10.0D, (double) format.getCurrency().getDefaultFractionDigits());

        return format.format(currencyAmount);
    }

    public void setSystemUiVisibility() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
