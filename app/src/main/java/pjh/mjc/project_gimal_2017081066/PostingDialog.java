package pjh.mjc.project_gimal_2017081066;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

public class PostingDialog {
    private Context context;
    String title, article, url, date;
    Double lat, lng;

    public PostingDialog(Context context, String title, String article, Double lat, Double lng, String date) {
        this.context = context;
        this.title = title;
        this.article = article;
        this.lat = lat;
        this.lng = lng;
        this.date = date;
    }
    public PostingDialog(Context context, String title, String article, Double lat, Double lng, String url, String date) {
        this.context = context;
        this.title = title;
        this.article = article;
        this.lat = lat;
        this.lng = lng;
        this.url = url;
        this.date = date;
    }

    public void show() {
        Dialog dlg = new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.posting_dialog);
        dlg.show();
        TextView title_tv = dlg.findViewById(R.id.dialog_title);
        TextView article_tv = dlg.findViewById(R.id.dialog_article);
        TextView date_tv = dlg.findViewById(R.id.dialog_date);
        ImageView image = dlg.findViewById(R.id.dialog_image);
        title_tv.setText(title);
        article_tv.setText(article);
        date_tv.setText(date);

        if(url == null) image.setVisibility(View.GONE); else image.setVisibility(View.VISIBLE);
        try {
            Uri uri = Uri.parse("file://" + url);
            image.setImageURI(uri);
        } catch (Exception e) {
        }
        Button close = dlg.findViewById(R.id.dialog_submit);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
                ((PostingActivity)context).finish();
            }
        });
    }
}
