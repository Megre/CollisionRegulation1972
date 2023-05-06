package spart.parser;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-12 16:04:34
 */
public class ASChapter extends ASTitle {

	public ASChapter() {
		super("^(第(.{1,2})章)");
	}
	
	public ASChapter(String levelID) {
		super("^(第(.{1,2})章)", levelID);
	}
	
	public ASTitle clone() {
		return clone(new ASChapter());
	}

}
