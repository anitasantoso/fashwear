package au.com.example.wear;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.view.ViewGroup;
import android.preview.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat;

import au.com.example.wear.R;

import static au.com.example.wear.AssetUtils.loadBitmapAsset;


public class HomeActivity extends Activity {

    private NotificationManagerCompat notificationMgr;
    private ViewGroup imgContainer;
    Drawable selectedDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        notificationMgr = NotificationManagerCompat.from(this);
        imgContainer = (ViewGroup)findViewById(R.id.imgContainer);

        ImageView preselectedImgView = null;

        // register on click listener
        for(int i=0; i<imgContainer.getChildCount(); i++) {
            final ImageView imgView = (ImageView) this.imgContainer.getChildAt(i);
            final int index = i;

            // demo only
            if(index == 1) {
                preselectedImgView = imgView;
            }
            imgView.setOnClickListener(new View.OnClickListener() {

                private void clearSelection() {
                    for(int i=0; i<imgContainer.getChildCount(); i++) {
                        final ImageView imgView = (ImageView) HomeActivity.this.imgContainer.getChildAt(i);
                        imgView.getDrawable().clearColorFilter();
                        selectedDrawable = null;
                    }
                }
                @Override
                public void onClick(View view) {

                    // clear current selection
                    if(selectedDrawable != null) {
                       clearSelection();
                    }

                    // if not currently selected
                    if(imgView.getTag() == null) {
                        imgView.setTag(index);
                        imgView.getDrawable().setColorFilter(android.R.color.black, PorterDuff.Mode.DARKEN);
                        selectedDrawable = imgView.getDrawable();
                    } else {
                        imgView.setTag(null);
                    }
                }
            });
        }

        // preselect an item
        preselectedImgView.performClick();
    }

    public void pushNotification(View view) {

        if(selectedDrawable == null) {
            return;
        }

        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.setBigContentTitle("FashWear");
        style.bigPicture(AssetUtils.drawableToBitmap(selectedDrawable));
        style.setSummaryText("Your outfit for work today");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setStyle(style);

        // addPages?
        notificationMgr.notify(0, builder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
