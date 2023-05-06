package spart.search;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-28 17:51:33
 */
public class ResultPair {

	private Object result;
	private float score;
	
	public ResultPair(Object result, float score) {
		this.result = result;
		this.score = score;
	}
	
	public Object getResult() {
		return result;
	}
	
	public float getScore() {
		return score;
	}
	
	public String toString() {
		return result.toString() + " - " + score;
	}
	
	public boolean equals(ResultPair other) {
		return result.equals(other.result) 
				&& score == other.score;
	}
}
