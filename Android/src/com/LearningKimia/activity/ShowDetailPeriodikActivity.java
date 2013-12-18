package com.LearningKimia.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMyActivity;
import com.LearningKimia.util.Constant;
import com.LearningKimia.util.Utility;

public class ShowDetailPeriodikActivity extends BaseMyActivity{
	protected static final int SWIPE_MIN_DISTANCE = 120;
	protected static final int SWIPE_THRESHOLD_VELOCITY = 200;
	protected ViewFlipper mViewFlipper;
	private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());
	protected List<ImageView> periodikViews;
	protected int indexViewToView = -1;
	
	@Override
	public void initBundle() {
		super.initBundle();
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			indexViewToView = bundle.getInt("index");
			indexViewToView--;
		}
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);
		buildViewFliper();
		if(indexViewToView<=periodikViews.size()-1){
			int indexView = mViewFlipper.indexOfChild(periodikViews.get(indexViewToView));
			if(indexView!=-1){
				mViewFlipper.setDisplayedChild(indexView);
			}
		}
//		mViewFlipper.setDisplayedChild(mViewFlipper.indexOfChild(periodikViews.get(indexViewToView)));
        mViewFlipper.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });
	}
	
	class SwipeGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(ShowDetailPeriodikActivity.this, R.anim.left_in_flip));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(ShowDetailPeriodikActivity.this, R.anim.left_out_flip));                 
                    mViewFlipper.showNext();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(ShowDetailPeriodikActivity.this, R.anim.right_in_flip));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(ShowDetailPeriodikActivity.this,R.anim.right_out_flip));
                    mViewFlipper.showPrevious();
                    return true;
                }
 
            } catch (Exception e) {
                e.printStackTrace();
            }
 
            return false;
        }
    }
	
	protected void buildViewFliper(){
		periodikViews = new ArrayList<ImageView>();
		String[]files = Utility.getAllFilesInAsset(this, Constant.IMAGE_VIEW_PERIODIK_PATH);
		for(int i=0; i<files.length; i++){
			Log.i("files", files[i]);
			if(files[i].contains("png") || files[i].contains("jpg") || files[i].contains("jpeg"))
			addImageToFlipper(Constant.IMAGE_VIEW_PERIODIK_PATH + "/" + files[i]);
		}
		Log.i("buildViewFlipper", "run");
	}
	
	protected void addImageToFlipper(String path){
		ImageView imageView = (ImageView) getLayoutInflater().inflate(R.layout.image_periodik, null);
		Drawable drawable = Utility.getDrawableFromAsset(this, path);
		if(drawable!=null){
			imageView.setBackgroundDrawable(drawable);
			mViewFlipper.addView(imageView);
			periodikViews.add(imageView);
		} else Log.i("drawable", "null");
		Log.i("addImage", "run");
	}

	@Override
	public int getLayoutId() {
		return R.layout.show_detail_periodik_page;
	}

	@Override
	public int getIdViewToAppendFromInflate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIdViewToInflate() {
		// TODO Auto-generated method stub
		return 0;
	}

}
