package spart.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import spart.parser.ASBlock;
import spart.parser.ASSentence;
import spart.search.SearchEngine;
import spart.search.SearchResult;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-05-12 10:52:32
 */
public class PerformanceTest {
	private static final String selectPath = "./select.txt",
			searchPath = "./search.txt",
			resultPath = "./result.txt";
	private SearchEngine engine;
	private List<ASSentence> sentenceList = new ArrayList<ASSentence>();
	
	
	private PerformanceTest() {
		final InputStream is = Main.class.getResourceAsStream("/1972年国际海上避碰规则.txt");
		engine = SearchEngine.initiate(is);
	}
	
	public static void main(String[] args) throws IOException {
		PerformanceTest instance = new PerformanceTest();
//		instance.selectSentences(selectPath);
		instance.search(searchPath);
	}
	
	private void search(String inputPath) throws IOException {
		BufferedReader select = new BufferedReader(new FileReader(new File(selectPath)));
		BufferedReader reader = new BufferedReader(new FileReader(new File(inputPath)));
		String search = null;
		StringBuffer buffer = new StringBuffer();
		TimeRecorder recorder = new TimeRecorder();
		while((search = reader.readLine()) != null) {
			System.out.println("search: " + search);
			recorder.start();
			SearchResult result = engine.semanticSearch(search);
			recorder.end();
			
			buffer.append(search).append("\t");
			buffer.append(select.readLine()).append("\t");
			buffer.append(result.first(3)).append("\t");
			buffer.append(recorder.duration()).append("\r\n");
		}
		reader.close();
		select.close();
		
		System.out.println("save search result");
		FileWriter writer = new FileWriter(new File(resultPath));
		writer.write(buffer.toString());
		writer.close();
	}

	@SuppressWarnings("unused")
	private void selectSentences(String outputPath) throws IOException {		
		
		for(ASBlock block: engine.getBlockList()) {
			extractSentences(block);
		}
		
		Random random = new Random(new Date().getTime());
		FileWriter writer = new FileWriter(new File(outputPath));
		Set<String> selectSet = new HashSet<String>();
		
		final int resultNum = Math.min(100, sentenceList.size());
		for(int i=0, idx=0; i<resultNum; ) {
			idx = random.nextInt(sentenceList.size());
			
			String select = sentenceList.get(idx).toString();
			if(select.length() > 5 && !select.contains("………………………") && !selectSet.contains(select)){
				++i;
				System.out.println(select);
				writer.write(select + "\r\n");
				selectSet.add(select);
			}
		}
		writer.close();
	}

	private void extractSentences(ASBlock block) {
		if(block instanceof ASSentence) sentenceList.add((ASSentence) block);
		
		for(ASBlock child: block.getContentList()) {
			extractSentences(child);
		}
	}
}
