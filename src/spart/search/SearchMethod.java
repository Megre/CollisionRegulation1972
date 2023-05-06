package spart.search;

import java.util.List;

import spart.parser.ASBlock;
import spart.parser.ASList;
import spart.parser.ASSentence;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-28 11:34:49
 */
public abstract class SearchMethod {
	
	protected List<ASBlock> blockList;
	
	public SearchMethod(List<ASBlock> blockList) {
		this.blockList = blockList;
	}
	
	public abstract SearchResult search(String text);
	
	protected ASSentence retrieveSentence(ASBlock block) {
		ASSentence sentence = null;
		
		if(block instanceof ASSentence) {
			 sentence = (ASSentence) block;
		}
		else if(block instanceof ASList) {
			ASList asList = ((ASList) block);
			String prefix = asList.group(0);
			if(prefix != null) {
				String listContent = asList.getText().toString();
				sentence = new ASSentence(listContent);
			}
		}
		
		return sentence;
	}

}
