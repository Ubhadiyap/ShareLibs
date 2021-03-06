package com.zlcdgroup.stickerlib;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.zlcdgroup.stickerlib.utils.FileUtils;
import com.zlcdgroup.stickerlib.view.BubbleInputDialog;
import com.zlcdgroup.stickerlib.view.BubbleTextView;
import com.zlcdgroup.stickerlib.view.StickerView;

import java.io.File;
import java.util.ArrayList;

public class StickerMainActivity extends AppCompatActivity {

    //气泡输入框
    private BubbleInputDialog mBubbleInputDialog;

    //当前处于编辑状态的贴纸
    private StickerView mCurrentView;

    //当前处于编辑状态的气泡
    private BubbleTextView mCurrentEditTextView;

    //存储贴纸列表
    private ArrayList<View> mViews;

    private RelativeLayout mContentRootView;

    private ImageView   image_content;

    private FloatingActionsMenu mMultipleActions;

    private View mAddSticker;

    private View mAddBubble;

    public   static    final   String  KEY_SAVE_FILENAME="saveReName";
    public   static    final   String  KEY_SAVE_FILEDIR="directoryPath";
    public   static    final   String  KEY_PATH="key_path";


    public   String   fileName;
    public   String   dir;
    public   String   path;

    public   int  maxwidth;
    public   int  maxheight;

    public   int  px,py;
    public   int  endx,endy;

    public   int[]  borders = new int[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickermain);
        mContentRootView = (RelativeLayout) findViewById(R.id.rl_content_root);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fileName = getIntent().getStringExtra(KEY_SAVE_FILENAME);
        dir = getIntent().getStringExtra(KEY_SAVE_FILEDIR);
        path = getIntent().getStringExtra(KEY_PATH);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addStickerView();
//            }
//        });
        mMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        mAddSticker = findViewById(R.id.action_add_sticker);
        image_content = (ImageView)findViewById(R.id.image_content);
        mAddBubble = findViewById(R.id.action_add_bubble);
        mAddSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStickerView();
                mMultipleActions.collapse();
            }
        });
        mAddBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBubble();
                mMultipleActions.collapse();
            }
        });
        mViews = new ArrayList<>();
        mBubbleInputDialog = new BubbleInputDialog(this);
        mBubbleInputDialog.setCompleteCallBack(new BubbleInputDialog.CompleteCallBack() {
            @Override
            public void onComplete(View bubbleTextView, String str) {
                ((BubbleTextView) bubbleTextView).setText(str);
            }
        });
        try{
            DisplayMetrics dm = getResources().getDisplayMetrics();
            File file = new File(path);
            if(file.exists()){
                Bitmap src = BitmapFactory.decodeFile(file.getAbsolutePath());
                maxheight = src.getHeight();
                maxwidth  = src.getWidth();
                ViewGroup.LayoutParams lp =  mContentRootView.getLayoutParams();
                lp.width = src.getWidth();
                lp.height =src.getHeight();
                mContentRootView.setLayoutParams(lp);
                if (src.getWidth() > src.getHeight()) {
                    py = 0;
                    if (dm.widthPixels > dm.heightPixels) {
                        px = (dm.widthPixels - src.getWidth()) / 2;
                    } else {
                        px = (dm.heightPixels - src.getWidth()) / 2;
                    }

                    endy = src.getHeight();
                    endx = src.getWidth();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    endx = src.getWidth();
                    endy = src.getHeight();
                    px = (dm.widthPixels - src.getWidth()) / 2;
                    py = (dm.heightPixels - src.getHeight() - 50) / 2;
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
            if (py < 0) {
                py = 0;
            }
            if (px < 0) {
                px = 0;
            }
            borders[0]=px;
            borders[1] = py;
            borders[2] = endx+px;
            borders[3] = endy+py;
            System.out.println(px+":"+py+":"+endx+":"+endy);
            image_content.setImageURI(Uri.fromFile(file));
            System.out.println(image_content.getWidth()+":"+image_content.getHeight());

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
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
        if (id == R.id.action_complete) {
            if(null != mCurrentView){
                mCurrentView.setInEdit(false);
            }

            if(null != mCurrentEditTextView){
                mCurrentEditTextView.setInEdit(false);
            }
            generateBitmap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //添加表情
    private void addStickerView() {
        final StickerView stickerView = new StickerView(this,maxwidth,maxheight,borders);
        stickerView.setImageResource(R.mipmap.ic_cat);
        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mViews.remove(stickerView);
                mContentRootView.removeView(stickerView);
            }

            @Override
            public void onEdit(StickerView stickerView) {
                if (mCurrentEditTextView != null) {
                    mCurrentEditTextView.setInEdit(false);
                }
                mCurrentView.setInEdit(false);
                mCurrentView = stickerView;
                mCurrentView.setInEdit(true);
            }

            @Override
            public void onTop(StickerView stickerView) {
                int position = mViews.indexOf(stickerView);
                if (position == mViews.size() - 1) {
                    return;
                }
                StickerView stickerTemp = (StickerView) mViews.remove(position);
                mViews.add(mViews.size(), stickerTemp);
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        mContentRootView.addView(stickerView, lp);
        mViews.add(stickerView);
        setCurrentEdit(stickerView);
    }

    //添加气泡
    private void addBubble() {
        final BubbleTextView bubbleTextView = new BubbleTextView(this,
                Color.WHITE, 0);
        bubbleTextView.setImageResource(R.mipmap.bubble_7_rb);
        bubbleTextView.setText("这是标签");
        bubbleTextView.setOperationListener(new BubbleTextView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mViews.remove(bubbleTextView);
                mContentRootView.removeView(bubbleTextView);
            }

            @Override
            public void onEdit(BubbleTextView bubbleTextView) {
                if (mCurrentView != null) {
                    mCurrentView.setInEdit(false);
                }
                mCurrentEditTextView.setInEdit(false);
                mCurrentEditTextView = bubbleTextView;
                mCurrentEditTextView.setInEdit(true);
            }

            @Override
            public void onClick(BubbleTextView bubbleTextView) {
                mBubbleInputDialog.setBubbleTextView(bubbleTextView);
                mBubbleInputDialog.show();
            }

            @Override
            public void onTop(BubbleTextView bubbleTextView) {
                int position = mViews.indexOf(bubbleTextView);
                if (position == mViews.size() - 1) {
                    return;
                }
                BubbleTextView textView = (BubbleTextView) mViews.remove(position);
                mViews.add(mViews.size(), textView);
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContentRootView.addView(bubbleTextView, lp);
        mViews.add(bubbleTextView);
        setCurrentEdit(bubbleTextView);
    }



    /**
     * 设置当前处于编辑模式的贴纸
     */
    private void setCurrentEdit(StickerView stickerView) {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }
        mCurrentView = stickerView;
        stickerView.setInEdit(true);
    }

    /**
     * 设置当前处于编辑模式的气泡
     */
    private void setCurrentEdit(BubbleTextView bubbleTextView) {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }
        mCurrentEditTextView = bubbleTextView;
        mCurrentEditTextView.setInEdit(true);
    }

    private void generateBitmap() {

        Bitmap bitmap = Bitmap.createBitmap(image_content.getWidth(), image_content.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        mContentRootView.draw(canvas);
        FileUtils.saveBitmapToLocal(bitmap, dir+File.separator+fileName);
        Intent   data =  new Intent();
        data.putExtra(KEY_SAVE_FILEDIR,dir);
        data.putExtra(KEY_SAVE_FILENAME,fileName);
        setResult(RESULT_OK,data);

        Intent intent = new Intent(this, DisplayActivity.class);
        intent.putExtra("image", dir+File.separator+fileName);
        startActivity(intent);
    }

}
