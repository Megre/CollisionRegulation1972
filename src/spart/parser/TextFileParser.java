package spart.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-13 09:08:23
 */
public class TextFileParser {

	private String filePath;
	private InputStream inputStream;
	
	private Stack<ASTitle> stack = new Stack<ASTitle>();
	private List<ASBlock> blockList = new ArrayList<ASBlock>();
	
	public TextFileParser(String filePath) {
		this.filePath = filePath;
	}
	
	public TextFileParser(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public List<ASBlock> parse() {
		try {
			BufferedReader bufferedReader = null;
			if(filePath != null) {
				bufferedReader = new BufferedReader(new FileReader(filePath));
			}
			else {
				InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
				bufferedReader = new BufferedReader((reader));
			}
			
			String line = null;
			List<ASTitle> titleTemps = makeTitleTemps();
			while((line = bufferedReader.readLine()) != null) {
				if(line.trim().isEmpty()) continue;
							
				// build article structure
				ASTitle title = parseTitle(titleTemps, line);
				if(title == null) {
					String[] sList = line.split("[。：]");
					for(String s: sList) {
						final String trimmed = s.trim();
						if(trimmed.isEmpty()) continue;
						
						appendBlock(new ASSentence(trimmed));
					}
				}
				else {
					adjustStack(title);
					appendBlock(title);
					stack.push(title);
				}
			}
			bufferedReader.close();
			return blockList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
		
	private void adjustStack(ASTitle title) {
		if(title == null) return ;
		
		if(!findSameLevelASTitle(title)) {
			return ;
		}
		
		while(!stack.isEmpty()) {
			ASTitle head = stack.pop();
			if(head.getLevelID().equals(title.getLevelID())) { 
				break;
			}
		}
		
	}
	
	private boolean findSameLevelASTitle(ASTitle title) {
		for(int idx = stack.size()-1; idx >= 0; --idx) {
			ASTitle title2cmp = stack.get(idx);
			if(title2cmp.getLevelID().equals(title.getLevelID()))
				return true;
		}
		
		return false;
	}
	
	private void appendBlock(ASBlock block) {
		if(stack.isEmpty()) {
			blockList.add(block);
		}
		else {
			stack.peek().addContent(block);
			block.setParent(stack.peek());
		}
	}
		
	private List<ASTitle> makeTitleTemps() {
		ArrayList<ASTitle> titles = new ArrayList<ASTitle>();
		titles.add(new ASTitle("^(1972年国际海上避碰规则)$", "level0"));
		titles.add(new ASTitle("^(1972年国际海上避碰规则公约)$", "level0"));
		titles.add(new ASAppendix("level1"));
		titles.add(new ASCatalog());
		titles.add(new ASChapter("level1"));
		titles.add(new ASChapterItem());
		titles.add(new ASOrderedList());
		titles.add(new ASUnorderedList());
		titles.add(new ASSection());
		
		return titles;
	}
	
	private ASTitle parseTitle(List<ASTitle> titles, String line) {
		for(ASTitle title: titles ) {
			if(title.matches(line)) {
				ASTitle titleClone = title.clone();
				String name = title.group(),
						text = line.substring(name.length());
					
				titleClone.setName(name);
				titleClone.setText(text);
				titleClone.setLevelID(title.getLevelID());
				return titleClone;
			}
		}
		return null;
	}
}
