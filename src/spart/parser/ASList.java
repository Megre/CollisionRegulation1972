package spart.parser;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-12 16:04:56
 */
public class ASList extends ASTitle {

	/**
	 * @param matcher
	 */
	public ASList(String matcher) {
		super(matcher);
	}
	
	public ASTitle clone() {
		return clone(new ASList(getMatcherRegex()));
	}


}
