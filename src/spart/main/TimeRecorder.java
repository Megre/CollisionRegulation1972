package spart.main;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-05-12 12:05:04
 */
public class TimeRecorder {

	private long start, end;
	
	void start() {
		start = System.currentTimeMillis();
	}
	
	void end() {
		end = System.currentTimeMillis();
	}
	
	long duration() {
		return end - start;
	}
}
