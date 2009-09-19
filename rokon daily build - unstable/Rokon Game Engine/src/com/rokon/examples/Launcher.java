package com.rokon.examples;

import rokon.Debug;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Launcher extends Activity implements OnClickListener {

	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.launcher);
		findViewById(R.id.btnExample1).setOnClickListener(this);
		findViewById(R.id.btnExample2).setOnClickListener(this);
		findViewById(R.id.btnExample3).setOnClickListener(this);
		findViewById(R.id.btnExample4).setOnClickListener(this);
		findViewById(R.id.btnExample5).setOnClickListener(this);
		findViewById(R.id.btnExample6).setOnClickListener(this);
		findViewById(R.id.btnExample7).setOnClickListener(this);
		findViewById(R.id.btnExample8).setOnClickListener(this);
		findViewById(R.id.btnExample9).setOnClickListener(this);
		findViewById(R.id.btnExample10).setOnClickListener(this);
	}
	
	public void onClick(View v) {
		Intent intent;
		switch(v.getId()) {
			case R.id.btnExample1:
        		Debug.print("Starting Example 1");
				intent = new Intent(Launcher.this, Example1.class);
				startActivity(intent);
				break;
			case R.id.btnExample2:
		        Debug.print("Starting Example 2");
				intent = new Intent(Launcher.this, Example2.class);
				startActivity(intent);
				break;
			case R.id.btnExample3:
		        Debug.print("Starting Example 3");
				intent = new Intent(Launcher.this, Example3.class);
				startActivity(intent);
				break;
			case R.id.btnExample4:
		        Debug.print("Starting Example 4");
				intent = new Intent(Launcher.this, Example4.class);
				startActivity(intent);
				break;
			case R.id.btnExample5:
		        Debug.print("Starting Example 5");
				intent = new Intent(Launcher.this, Example5.class);
				startActivity(intent);
				break;
				case R.id.btnExample6:
		        Debug.print("Starting Example 6");
				intent = new Intent(Launcher.this, Example6.class);
				startActivity(intent);
				break;
			case R.id.btnExample7:
		        Debug.print("Starting Example 7");
				intent = new Intent(Launcher.this, Example7.class);
				startActivity(intent);
				break;
			case R.id.btnExample8:
		        Debug.print("Starting Example 8");
				intent = new Intent(Launcher.this, Example8.class);
				startActivity(intent);
				break;
			case R.id.btnExample9:
		        Debug.print("Starting Example 9");
				intent = new Intent(Launcher.this, Example9.class);
				startActivity(intent);
				break;
			case R.id.btnExample10:
		        Debug.print("Starting Example 10");
				intent = new Intent(Launcher.this, Example10.class);
				startActivity(intent);
				break;
		}
	}
	
}
