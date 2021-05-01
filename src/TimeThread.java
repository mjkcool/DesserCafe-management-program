import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class TimeThread extends Thread{
	/*시간 형식*/SimpleDateFormat format1 = new SimpleDateFormat("M/dd a hh:mm:ss",new Locale("en", "US"));
	JTextField timeField;
	JPanel topBar;
	
	public TimeThread(JTextField timeField, JPanel topBar) {
		this.timeField = timeField;
		this.topBar = topBar;
	}
	
	public void run()
    {
        try {
            while(true){
            	Date time = new Date();
        		String timeStr = format1.format(time);
        		timeField.setText(timeStr);
        		topBar.setVisible(false);
        		topBar.setVisible(true);
        		
                Thread.sleep(1000);    
            }
        } catch (Exception e) {
        	timeField.setText("시간 오류");
        	topBar.setVisible(false);
    		topBar.setVisible(true);
        }
    }
}
