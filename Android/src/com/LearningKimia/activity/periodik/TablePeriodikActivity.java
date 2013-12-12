package com.LearningKimia.activity.periodik;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseActivity;
import com.socratica.mobile.ImageMap;
import com.socratica.mobile.ImageMapListener;
import com.socratica.mobile.PaintType;

public class TablePeriodikActivity extends BaseActivity implements ImageMapListener{
	private ImageMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		this.map = ((ImageMap)findViewById(R.id.map));
//		BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inInputShareable = true;
//	    options.inPurgeable = true;
//	    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//	    options.inDither = true;
//		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.periodic_table, options);
//		Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap, 2373, 1025, true);
		this.map.setImageResource(R.drawable.periodic_table);
	    this.map.setImageMapListener(this);
	}

	@Override
	public void onAreaClicked(int paramInt) {
		this.map.showArea(paramInt, getHightlightPaintType(this.map.getDataId(paramInt)));
	    int[] arrayOfInt = new int[1];
	    arrayOfInt[0] = this.map.getDataId(paramInt);
	    Log.i("area", String.valueOf(arrayOfInt[0]));
	    
//	    SessionData localSessionData = createSessionData(arrayOfInt);
//	    Intent localIntent = new Intent(Utils.getAction(this, Action.SHOW_DATA));
//	    localIntent.putExtra("data", localSessionData);
//	    startActivity(localIntent);
	}
	
	protected PaintType getHightlightPaintType(int paramInt)
	{
		PaintType SELECTION_FILL_PAINT_TYPE = new PaintType(Paint.Style.STROKE, -65536);
//		return ImageMap.PAINT_TYPE_DEFAULT;
	    return SELECTION_FILL_PAINT_TYPE;
	}
	
}
