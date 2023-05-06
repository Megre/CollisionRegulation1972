package spart.py;

import java.util.List;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-28 14:55:34
 */
public class DSNFTriple {
	private List<Object> triple;

	public DSNFTriple(List<Object> triple) {
		this.triple = triple;
	}
	
	public String subject() {
		return triple.get(0).toString();
	}
	
	public String predicate() {
		return triple.get(1).toString();
	}
	
	public String object() {
		return  triple.get(2).toString();
	}
	
}
