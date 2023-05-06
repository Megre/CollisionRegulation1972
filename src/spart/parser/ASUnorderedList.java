package spart.parser;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-12 16:05:45
 */
public class ASUnorderedList extends ASTitle {

	/**
	 * @param matcher
	 */
	public ASUnorderedList() {
		super("^(——)");
	}
	
	public ASTitle clone() {
		return clone(new ASUnorderedList());
	}


}
