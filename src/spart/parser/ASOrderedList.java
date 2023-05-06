package spart.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-12 16:05:30
 */
public class ASOrderedList extends ASList {

	/**
	 * @param matcher
	 */
	public ASOrderedList() {
		super("(^\\d{1,2}．)|(^（([a-z]|(?:\\d{1,2}))）)|(^[①②③④⑤])");
	}
	
	public ASTitle clone() {
		return clone(new ASOrderedList());
	}


	public static void main(String[] args) {
		
		Pattern pattern = Pattern.compile("(^\\d{1,2}．)|(^（([a-z]|(?:\\d{1,2}))）)|(^[①②③④⑤])");
		Matcher matcher = pattern.matcher("（1）项的规定");
		System.out.println(matcher.find());
		System.out.println(matcher.group());
	}
	
}
