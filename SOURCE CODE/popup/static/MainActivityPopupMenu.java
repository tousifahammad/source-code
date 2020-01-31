
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivityPopupMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_popup_menu);

        ImageButton imgMenu = (ImageButton) findViewById(R.id.imgMenu);
        Button btnMenu = (Button) findViewById(R.id.btnMenu);

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(MainActivityPopupMenu.this, v);
                popup.getMenuInflater()
                        .inflate(R.menu.ex_popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.add) {
                            Snackbar.make(v, "Added", Snackbar.LENGTH_SHORT).show();
                        } else if (item.getItemId() == R.id.rem) {
                            Snackbar.make(v, "Reminder Added", Snackbar.LENGTH_SHORT).show();
                        }

                        return true;
                    }
                });
                popup.show();
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(MainActivityPopupMenu.this, v);
                popup.getMenuInflater().inflate(R.menu.ex_popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.add) {
                            Snackbar.make(v, "Added", Snackbar.LENGTH_SHORT).show();
                        } else if (item.getItemId() == R.id.rem) {
                            Snackbar.make(v, "Reminder Added", Snackbar.LENGTH_SHORT).show();
                        }

                        return true;
                    }
                });
                popup.show();
            }
        });
    }
}
