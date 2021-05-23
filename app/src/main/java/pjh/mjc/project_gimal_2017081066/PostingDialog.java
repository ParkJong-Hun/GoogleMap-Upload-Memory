package pjh.mjc.project_gimal_2017081066;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PostingDialog {
    private Context context;

    public PostingDialog(Context context) {
        this.context = context;
    }

    public void show() {
        Dialog dlg = new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.posting_dialog);
        dlg.show();
        TextView title = dlg.findViewById(R.id.dialog_title);
        TextView article = dlg.findViewById(R.id.dialog_article);
        ImageView image = dlg.findViewById(R.id.dialog_image);
        Button cancel = dlg.findViewById(R.id.dialog_cancel);
        Button submit = dlg.findViewById(R.id.dialog_submit);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
    }
}
