package workshop2;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.rxtx.model.WsList;

public class Test {
	
	public static void main(String[] args) {
//		List<WsList> wsLists = new ArrayList<>();
//		List<WsList> tem = new ArrayList<>();
//		WsList ls = new WsList();
//		ls.setGuid("ssss");
//		wsLists.add(ls);
//		
//		tem.addAll(wsLists);
//		wsLists.clear();
//		System.out.println(tem.size()+"   "+wsLists.size());
		
		
		Timer timer = new Timer();
    	timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("00000---"+System.currentTimeMillis());
			}
		}, 1000, 5000);;
		
	}

}
