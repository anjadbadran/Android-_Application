package example.user;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class MainActivity extends Activity {

	    byte[] buffer;	
	 	int port=50005;
		DatagramSocket socket;
		DatagramPacket packet;
		
		String GpsLocation="";
		
		EditText jtf1;
		TextView loc;
		
		String ipAddress;
		WifiManager wifiMgr;
		
		Timer t1;
        Handler handler;
        
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        handler=new Handler();
        
        jtf1=(EditText)findViewById(R.id.editText1);
        loc=(TextView)findViewById(R.id.latlabel);
        
        wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        ///////////////// Show My IP /////////////////////
       	@SuppressWarnings("deprecation")
       	WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
       	int ip = wifiInfo.getIpAddress();
        ipAddress = Formatter.formatIpAddress(ip);
        jtf1.setText(ipAddress);
        jtf1.setEnabled(false);
		///////////////// Show My IP /////////////////////
        
        
        t1=new Timer();
        
        t1.scheduleAtFixedRate(new TimerTask() {
			
			@SuppressWarnings("resource")
			@Override
			public void run() {
				
				try {
					

					DatagramSocket socket;
					byte[] arr = new byte[9216];
					socket = new DatagramSocket(port);
						
					packet = new DatagramPacket(arr, arr.length);
					socket.receive(packet);
				    GpsLocation=new String(packet.getData(),0,packet.getLength());
				    handler.post(new Runnable() {
						
						@Override
						public void run() {
							
							loc.setText(GpsLocation);
						}
					});
					
				    
				}catch(Exception e) {}
				
				
			}
		}, 100,100);
        
    }
    
}
