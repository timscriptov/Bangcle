package com.mcal.bangcle;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import java.io.File;

public class MainActivity extends Activity 
{
	String path = Environment.getExternalStorageDirectory() + "/Bangcle";
	private static  final String AES_KEY = "1234567812345678";
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Settings.ACTION_MANAGE_OVERLAY_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Settings.ACTION_MANAGE_OVERLAY_PERMISSION}, 1);
            }
        }
		encryptDex();
    }
	
	public void encryptDex() {
		String assetDir = path + "/assets";
		File file_asset=new File(assetDir);
		if(!file_asset.exists()){
			file_asset.mkdirs();
		}
		
		String rawdex = path + "/classes.dex";
		
		byte[] data=FileUtil.getFileByte(rawdex);
		byte[] encrypt_data=AESUtil.encrypt(data, AES_KEY);
		System.out.println("AES encrypt classes.dex finished");
		FileUtil.byteToFile(encrypt_data, assetDir, "jiami.dat");

		System.out.println("copy jiami.dat to assets dir finished");
	}
}
