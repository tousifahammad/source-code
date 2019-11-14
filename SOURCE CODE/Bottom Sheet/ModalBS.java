
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ModalBS extends BottomSheetDialogFragment {

    TextView preview, share, getlink, copy, email;

    public ModalBS() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.modal_bs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**Init The Views in Bottom Sheet Dialog**/
        preview = (TextView) view.findViewById(R.id.preview);
        share = (TextView) view.findViewById(R.id.share);
        getlink = (TextView) view.findViewById(R.id.getlink);
        copy = (TextView) view.findViewById(R.id.copy);
        email = (TextView) view.findViewById(R.id.email);

        /**Click Listener on any TextViews**/
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**Dismiss Dialog before showing Toast**/
                dismiss();
                Toast.makeText(getActivity(), "Preview", Toast.LENGTH_SHORT).show();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Toast.makeText(getActivity(), "Share", Toast.LENGTH_SHORT).show();
            }
        });
        getlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Toast.makeText(getActivity(), "Get a Link", Toast.LENGTH_SHORT).show();
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Toast.makeText(getActivity(), "Make a Copy", Toast.LENGTH_SHORT).show();
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Toast.makeText(getActivity(), "Email", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
