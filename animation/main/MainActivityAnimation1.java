
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivityAnimation1 extends AppCompatActivity {

    ImageView object;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_animation1);

        object = (ImageView) findViewById(R.id.object);
    }

    public void jumpFromDownAnim(View view) {
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_jump_from_down);
        object.startAnimation(anim);
    }

    public void jumpToDownAnim(View view) {
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_jump_to_down);
        object.startAnimation(anim);
    }


    public void jumpFromRightAnim(View view) {
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_jump_from_right);
        object.startAnimation(anim);
    }

    public void jumpToRightAnim(View view) {
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_jump_to_right);
        object.startAnimation(anim);
    }


    public void rollFromDownAnim(View view) {
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_roll_from_down);
        object.startAnimation(anim);
    }

    public void rollToDownAnim(View view) {
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_roll_to_down);
        object.startAnimation(anim);
    }

    public void rollFromRightAnim(View view) {
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_roll_from_right);
        object.startAnimation(anim);
    }

    public void rollToRightAnim(View view) {
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_roll_to_right);
        object.startAnimation(anim);
    }

    public void scaleUp(View view) {
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scale_up);
        object.startAnimation(anim);
    }

    public void scaleDown(View view) {
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scale_down);
        object.startAnimation(anim);
    }

    public void fallDown(View view) {
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_fall_down);
        object.startAnimation(anim);
    }

    public void slideOutRight(View view) {
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_slide_out_right);
        object.startAnimation(anim);
    }
}
