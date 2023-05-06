package spart.parser;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-12 16:03:25
 */
public class ASAppendix extends ASTitle {

	public ASAppendix() {
		super("^(附录(.))", ASAppendix.class.getName());
	}
	
	public ASAppendix(String levelID) {
		super("^(附录(.))", levelID);
	}
	
	public ASTitle clone() {
		return new ASAppendix();
	}

}
