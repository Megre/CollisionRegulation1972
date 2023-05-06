package spart.parser;

import java.util.ArrayList;
import java.util.List;

/** 
 * Article Structure: Block
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-13 11:08:21
 */
public class ASBlock {
	private ASTitle parent;
	private List<ASBlock> contentList = new ArrayList<ASBlock>();
	
	public ASTitle getParent() {
		return parent;
	}

	public void setParent(ASTitle parent) {
		this.parent = parent;
	}	
	
	public void addContent(ASBlock block) {
		contentList.add(block);
	}
	
	public List<ASBlock> getContentList() {
		return contentList;
	}
}
