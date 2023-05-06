package spart.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * Article Structure Title
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-12 15:58:20
 */
public class ASTitle extends ASBlock {

	private String matcherRegex;
	private String name;
	private ASSentence text;
	private String levelID;
	
	private Pattern pattern;
	private Matcher matcher;
	
	public ASTitle(String matcherRegex, String levelID) {
		this(matcherRegex);
		setLevelID(levelID);
	}
	
	public ASTitle(String matcherRegex) {
		this.levelID = getClass().getName();
		this.matcherRegex = matcherRegex;
		pattern = Pattern.compile(matcherRegex);
	}	

	public ASTitle clone() {
		return clone(new ASTitle(matcherRegex));
	}
	
	protected ASTitle clone(ASTitle newObj) {
		newObj.matcher = matcher;
		return newObj;
	}
	
	public boolean matches(String line) {
		matcher = pattern.matcher(line);
		return matcher.find();
	}
	
	public String group() {
		return matcher.group();
	}
	
	public String group(int index) {
		return matcher.group(index);
	}
	
	public String getLevelID() {
		return levelID;
	}
	
	public void setLevelID(String levelID) {
		this.levelID = levelID;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ASSentence getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = new ASSentence(text);
	}

	public String getMatcherRegex() {
		return matcherRegex;
	}

	public void setMatcher(String matcher) {
		this.matcherRegex = matcher;
	}
	
	public String toString() {
		return name + " " + text;
	}

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("^(第(.)条)");
		Matcher matcher = pattern.matcher("第三条领土的适用范围");		
		System.out.println(matcher.find());
		System.out.println(matcher.group(2));
		
	}

}
