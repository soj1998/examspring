package com.rm.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class SimCalculator {
	Analyzer analyzer = new SmartChineseAnalyzer();
//StandardAnalyzer();
    
	/**     
     * @param content1
     * @param content2
     * @param cisl 比较的词的数量
     * @return
     */
    public double calculate(String content1, String content2,int cisl) {    	
        List<String> tokenStream1 = getTokenizedList(content1,cisl);
        List<String> tokenStream2 = getTokenizedList(content2,cisl);
        if (isEmpty(tokenStream1) || isEmpty(tokenStream2)) {
            return 0d;
        }
        double size2=tokenStream2.size();
        double simCount = 0d;
        for (String token1 : tokenStream1) {
        	for(String token2 : tokenStream2) {
	            if (token1.equals(token2)) {
	                simCount++;
	                tokenStream2.remove(token2);
	                break;
	            }
        	}
        }
        /**
         * return compare t1 t2 t1 smaller return tokenStream1.size()>l2? simCount / l2:simCount / tokenStream1.size();
         case 1 c1 > c2 then result simC / c2
         case 2 c1 < c2 then simC / c2
         then anyway simC / c2         
         */
        return simCount / size2;
        
    }
    
    private static boolean isEmpty(Collection<?> c) {
        if ( c == null || c.isEmpty() ) {
            return true;
        }
        return false;
    }
 
    private List<String> getTokenizedList(String content,int cisl) {
    	if(content==null || "".equals(content)) {
    		return null;
    	}
        List<String> result = new ArrayList<String>();
        TokenStream stream  = analyzer.tokenStream(content, new StringReader(content));
        stream = new PorterStemFilter(stream);
        CharTermAttribute charTermAttribute = stream.addAttribute(CharTermAttribute.class);
        int sl=0;
        try {
            stream.reset();
            while(stream.incrementToken()) {
                String term = charTermAttribute.toString();
                sl++;
                if(sl>cisl) {
                	break;
                }
                result.add(term);
            }
        }
        catch(IOException e) {
            // not thrown b/c we're using a string reader...
        }
        if(stream != null) {
        	try {
				stream.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}
        }
        return result;
    }
    
    public static void main1(String[] args) {
        String[] str = new String[10];
        str[0] = "中华人民共和国我很好 中华人民共和国我很好 中华人民共和国我很好 中华人民共和国我很好 中华人民共和国我很好 中华人民共和国我很好 中华人民共和国我很好";
        str[1] = "中华人民共和国 我,很,好";        
        //System.out.println( new SimCalculator().calculate(str[0], str[1]) );
        List<String> abc=new ArrayList<String>();
        abc.add("中华人民共和国");
        abc.add("我很好");
        abc.add("中华人民共和国");
        for(String a:abc) {
        	System.out.println(a);
        }
        System.out.println("-----------");
        abc.remove("中华人民共和国");
        for(String a:abc) {
        	System.out.println(a);
        }
    }

}
