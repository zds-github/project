package com.firework;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.widget.EditText;

import com.fireview.MyView;

public class FireWorkActivity extends Activity {
	/** Called when the activity is first created. */

	// EventListener mListener = new EventListener();
    //Added by Dumbbell Yang at 2014-05-03
	static final String SETTINGS = "Settings";
	static final String FIREWORK_TEXT = "FireworkText";
	static final String FIREWORK_SIZE = "FireworkSize";
	protected SharedPreferences settings;
	
	static final String LOG_TAG = FireWorkActivity.class.getSimpleName();
	static int SCREEN_W = 480;// 当前窗口的大小
	static int SCREEN_H = 800;

	MyView fireworkView;
    
	// get the current looper (from your Activity UI thread for instance
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		}
		
		//added by Dumbbell Yang at 2014-05-03
		settings = this.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
		String fireworkText = settings.getString(FIREWORK_TEXT, "老婆我爱你");
		int fireworkSize = settings.getInt(FIREWORK_SIZE, 30);
		
		fireworkView = new MyView(this,fireworkText,fireworkSize);
		setContentView(fireworkView);
		
		//added by Dumbbell Yang 2014-05-03 长按设定
		((View)fireworkView).setOnLongClickListener(new View.OnLongClickListener() {                
			@Override  
			public boolean onLongClick(View v) { 
				System.out.println("FireworkActivity onLongClick");
				showSettingsDialog();
			    //默认是return false，返回true时，表示已经完整地处理了这个事件，  
			    //并不希望其他的回调方法再次进行处理；当返回false时，表示并没有完全处理  
			    //完该事件，更希望其他方法继续对其进行处理  
			    return true;  
			}  
		});  
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (fireworkView.isRunning()) {
			fireworkView.setRunning(false);
		}
	}
	
	//added by Dumbbell Yang at 2014-05-03
	private void showSettingsDialog(){
		 LayoutInflater factory = LayoutInflater.from(this);   
		 final View settingsView = factory.inflate(R.layout.settings, null);   
		 final EditText txtFireworkText = (EditText)settingsView.findViewById(R.id.txtFireworkText);   
		 final EditText txtFireworkSize = (EditText)settingsView.findViewById(R.id.txtFireworkSize);   

		 AlertDialog.Builder sd = new AlertDialog.Builder(FireWorkActivity.this);   
		 sd.setTitle("燃放烟花设定:");   
		 sd.setIcon(android.R.drawable.ic_dialog_info);   
		 sd.setView(settingsView);   

         sd.setPositiveButton("确定", new DialogInterface.OnClickListener() {   
			 public void onClick(DialogInterface dialog, int i) {   
			     String fireworkText = txtFireworkText.getText().toString();   
                 int    fireworkSize = Integer.parseInt(txtFireworkSize.getText().toString());
			     
                 SharedPreferences.Editor editor = settings.edit();
                 editor.putString(FIREWORK_TEXT, fireworkText);
                 editor.putInt(FIREWORK_SIZE, fireworkSize);
                 
                 editor.commit();
			  }   

		  });   

		  sd.setNegativeButton("取消", new DialogInterface.OnClickListener() { 
			   public void onClick(DialogInterface dialog, int i) {   
			   }   
		  });   

		  sd.show();// 显示对话框   
	}
}