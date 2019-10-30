import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;

public class MainActivityAlerter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_alerter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Alerter");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void simpleAlerter(View view) {
        // If Alerter is already showing it will hide the alerter
        // and show again
        if (Alerter.isShowing()) {
            Alerter.hide();
        }
        Alerter.create(this)
                .setTitle("Simple Alerter")
                .setText("Alerter with Background Color")
                .setBackgroundColorRes(R.color.colorPrimary)
                .setDuration(10000) //this method use to set the duration for the alerter (in milliseconds)
                .show();
    }

    public void withIcon(View view) {
        if (Alerter.isShowing()) {
            Alerter.hide();
        }
        Alerter.create(this)
                .setTitle("Simple Alerter")
                .setText("Alerter with Custom Icon")
                .setIcon(android.R.drawable.ic_dialog_email)
                .setIconColorFilter(0)
                .setBackgroundColorRes(R.color.colorPrimary)
                .setDuration(10000)
                .show();
    }

    public void withOutTitle(View view) {
        if (Alerter.isShowing()) {
            Alerter.hide();
        }
        Alerter.create(this)
                .setText("Alerter Without Title")
                .setBackgroundColorRes(R.color.colorPrimary)
                .setDuration(10000)
                .show();
    }

    public void withListener(View view) {
        if (Alerter.isShowing()) {
            Alerter.hide();
        }
        Alerter.create(this)
                .setTitle("New Message Received")
                .setText("You Got 5 New Messages Received.\nClick to Open")
                .setDuration(10000)
                .setBackgroundColorRes(R.color.colorPrimary)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Clicked on Alerter", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    public void withVerbose(View view) {
        if (Alerter.isShowing()) {
            Alerter.hide();
        }
        Alerter.create(this)
                .setTitle("5 New Messaged Received")
                .setText("Hello!\n" +
                        "How are you?\n" +
                        "What you doing today?\n" +
                        "Can we go for dinner today?\n" +
                        "Call me when you are free")
                .setBackgroundColorRes(R.color.colorPrimary)
                .show();
    }

    public void withAnim(View view) {
        if (Alerter.isShowing()) {
            Alerter.hide();
        }
        Alerter.create(this)
                .setTitle("Alerter")
                .setText("Alerter with enter exit animation")
                .setEnterAnimation(R.anim.alerter_slide_in_from_left)
                .setExitAnimation(R.anim.alerter_slide_out_to_right)
                .setBackgroundColorRes(R.color.colorPrimary)
                .setDuration(10000)
                .show();
    }

    public void swipeDismiss(View view) {
        if (Alerter.isShowing()) {
            Alerter.hide();
        }
        Alerter.create(this)
                .setTitle("Alerter")
                .setText("Swipe to Dismiss Alerter")
                .setBackgroundColorRes(R.color.colorPrimary)
                .setDuration(10000)
                .enableSwipeToDismiss()
                .enableIconPulse(true)
                .show();
    }

    public void withProgress(View view) {
        if (Alerter.isShowing()) {
            Alerter.hide();
        }
        Alerter.create(this)
                .setTitle("Alerter")
                .setText("Alert with Progress")
                .setDuration(10000)
                .enableProgress(true)
                .setProgressColorRes(R.color.colorPrimary)
                .show();
    }

    public void withButton(View view) {
        if (Alerter.isShowing()) {
            Alerter.hide();
        }
        Alerter.create(this)
                .setTitle("Alerter")
                .setText("Alerter With Buttons")
                .addButton("Ok", R.style.AlertButton, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Ok is Clicked", Toast.LENGTH_LONG).show();
                    }
                })
                .addButton("Cancel", R.style.AlertButton, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Cancel is Clicked", Toast.LENGTH_LONG).show();
                    }
                })
                .setBackgroundColorRes(R.color.colorPrimary)
                .setDuration(10000)
                .show();
    }

    public void customFont(View view) {
        if (Alerter.isShowing()) {
            Alerter.hide();
        }

        /*paste your font in assets/fonts path*/
        Alerter.create(this)
                .setTitle("Alerter")
                .setTitleAppearance(R.style.AlertTextAppearance_Title)
                .setTitleTypeface(Typeface.createFromAsset(getAssets(), "fonts/samplefont.otf"))    // font path in assets
                .setText("Alerter Appears Here")
                .setTextAppearance(R.style.AlertTextAppearance_Text)
                .setTextTypeface(Typeface.createFromAsset(getAssets(), "fonts/samplefont.otf"))
                .setDuration(10000)
                .setBackgroundColorRes(R.color.colorPrimary)
                .show();
    }
}
