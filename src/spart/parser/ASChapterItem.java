package spart.parser;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-12 16:04:08
 */
public class ASChapterItem extends ASTitle {

	public ASChapterItem() {
		super("^(第(.{1,3})条)");
	}
	
	public ASTitle clone() {
		return clone(new ASChapterItem());
	}


}
