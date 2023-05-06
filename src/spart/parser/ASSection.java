package spart.parser;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-12 16:03:11
 */
public class ASSection extends ASTitle {

	/**
	 * @param matcher
	 */
	public ASSection() {
		super("^(第(.{1,2})节)");
	}
	
	public ASTitle clone() {
		return clone(new ASSection());
	}


}
