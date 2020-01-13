import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityBottomSheet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_bottom_sheet);

        Button persistent = (Button) findViewById(R.id.persistent);
        Button bottomSheetDialog = (Button) findViewById(R.id.bottomSheetDialog);
        Button bottomSheetDialogFrag = (Button) findViewById(R.id.bottomSheetDialogFrag);

        /**When Click on Persistent Bottom Sheet Open New Activity(PersistentBS.java)**/
        persistent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersistentBS.class);
                startActivity(intent);
            }
        });

        /**When Click on Modal Bottom SheetShow Modal Bottom Sheet**/
        bottomSheetDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.modal_bs, null);

                final BottomSheetDialog dialog = new BottomSheetDialog(MainActivityBottomSheet.this);
                dialog.setContentView(view);
                dialog.show();
                TextView preview = (TextView) view.findViewById(R.id.preview);
                TextView share = (TextView) view.findViewById(R.id.share);
                TextView getlink = (TextView) view.findViewById(R.id.getlink);
                TextView copy = (TextView) view.findViewById(R.id.copy);
                TextView email = (TextView) view.findViewById(R.id.email);
                preview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Preview", Toast.LENGTH_SHORT).show();
                    }
                });
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Share", Toast.LENGTH_SHORT).show();
                    }
                });
                getlink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Get a Link", Toast.LENGTH_SHORT).show();
                    }
                });
                copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Make a Copy", Toast.LENGTH_SHORT).show();
                    }
                });
                email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Email", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        /**When Click on Modal Bottom Sheet Show Bottom Sheet Dialog Fragment**/
        bottomSheetDialogFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModalBS bottomSheetFragment = new ModalBS();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });
    }
}
