package spart.parser;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-12 16:01:08
 */
public class ASCatalog extends ASTitle {

	/**
	 * @param matcher
	 */
	public ASCatalog() {
		super("^(目录)");
	}
	
	public ASTitle clone() {
		return clone(new ASCatalog());
	}

}
