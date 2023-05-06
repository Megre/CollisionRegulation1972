package spart.nlpir;

import spart.parser.ParserException;
import spart.py.HowNetTool;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-12 16:58:41
 */
public class NlpirWord {

	private String text;
	private NlpirWordType type;
	
	public NlpirWord(String word) {
		String[] textType = word.split("/");
		if(textType.length > 1) {
			setText(textType[0]);
			setType(new NlpirWordType(textType[1]));
			return;
		}
		
		throw new ParserException("error word");
	}
	
	public float similarity(NlpirWord word) {
		return HowNetTool.newInstance().similarity(this, word);
	}
	
	public NlpirWord(String text, NlpirWordType type) {
		this.text = text;
		this.type = type;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


	public NlpirWordType getType() {
		return type;
	}


	public void setType(NlpirWordType type) {
		this.type = type;
	}

	public String toString() {
		return text + "/" + type;
	}
	
	public boolean equals(NlpirWord word) {
		return text.equals(word.getText())
				&& type.equals(word.getType());
	}
	
	
}
