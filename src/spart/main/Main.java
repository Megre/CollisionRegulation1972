package spart.main;

import java.io.InputStream;
import java.net.URISyntaxException;

import spart.search.SearchEngine;
import spart.search.SearchResult;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-11 17:06:36
 */
public class Main {

	public static void main(String[] args) throws URISyntaxException {
		final InputStream is = Main.class.getResourceAsStream("/1972年国际海上避碰规则.txt");
		SearchEngine engine = SearchEngine.initiate(is);
		
		{
			final SearchResult result = engine.semanticSearch("用机器推动的所有船只");
			System.out.println(result.first(3)); 
		}
		
		{
			final SearchResult result = engine.smartQA("船舶包括哪些");
			System.out.println(result);
		}		

		{
			final SearchResult result = engine.regulationInfer("两船的不同船舷受风");
			System.out.println(result);
		}
		
		{
			final SearchResult result = engine.regulationInfer("两船交叉相遇, 有碰撞危险, 他船在本船右舷");
			System.out.println(result);
		}
		
		SearchEngine.exit();
	}
}
