package com.sttri.util;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.sttri.pojo.SensitiveWord;

@SuppressWarnings("rawtypes")
public class SensitiveWordUtil {
	
	public static Map sensitiveWordMap = null;
	public static int minMatchTYpe = 1;      //最小匹配规则
	public static int maxMatchType = 2;      //最大匹配规则

	/**
	 * 替换敏感字字符
	 * @param txt 需要替换的文字内容
	 * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @param replaceChar 替换字符，默认*
	 */
	public static String replaceSensitiveWord(String txt,int matchType,String replaceChar,List list){
		String resultTxt = txt;
		Set<String> set = getSensitiveWord(txt, matchType,list); //获取所有的敏感词
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}
		return resultTxt;
	}
	
	/**
	 * 获取文字中的敏感词
	 * @param txt 需要替换的文字内容
	 * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * 比如说我是中国人，铭感词库里面有“中国”、“中国人”，最小规则就变成了：我是**人、最大规则就是：我是***。
	 * 所以最小规则就是：找到敏感词就结束，最大规则就是：找到最底层的那个敏感词。两个深度不一样！
	 * @return
	 */
	public static Set<String> getSensitiveWord(String txt , int matchType,List list){
		Set<String> sensitiveWordList = new HashSet<String>();
		for(int i = 0 ; i < txt.length() ; i++){
			int length = CheckSensitiveWord(txt, i, matchType,list);    //判断是否包含敏感字符
			if(length > 0){    //存在,加入list中
				sensitiveWordList.add(txt.substring(i, i+length));
				i = i + length - 1;    //减1的原因，是因为for会自增
			}
		}
		
		return sensitiveWordList;
	}
	
	/**
	 * 获取替换字符串
	 * @param replaceChar 替换字符，默认*
	 * @param length 敏感词关键字长度
	 * @return 替换后的字符串   比如：‘我是中国人’中的 ‘中国’是敏感词，则替换成：‘我是**人’ 
	 */
	private static String getReplaceChars(String replaceChar,int length){
		String resultReplace = replaceChar;
		for(int i = 1 ; i < length ; i++){
			resultReplace += replaceChar;
		}
		return resultReplace;
	}
	
	
	/**
	 * 检查文字中是否包含敏感字符，检查规则如下：<br>
	 * @param txt 需要替换的文字内容
	 * @param beginIndex
	 * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @return，如果存在，则返回敏感词字符的长度，不存在返回0
	 */
	public static int CheckSensitiveWord(String txt,int beginIndex,int matchType,List list){
		boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
		int matchFlag = 0;     //匹配标识数默认为0
		char word = 0;
		Map nowMap = addSensitiveWordToHashMap(list);
		for(int i = beginIndex; i < txt.length() ; i++){
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word);     //获取指定key
			if(nowMap != null){     //存在，则判断是否为最后一个
				matchFlag++;     //找到相应key，匹配标识+1 
				if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
					flag = true;       //结束标志位为true   
					if(SensitiveWordUtil.minMatchTYpe == matchType){    //最小规则，直接返回,最大规则还需继续查找
						break;
					}
				}
			}
			else{     //不存在，直接返回
				break;
			}
		}
		if(matchFlag < 2 || !flag){        //长度必须大于等于1，为词 
			matchFlag = 0;
		}
		return matchFlag;
	}
	
	@SuppressWarnings("unchecked")
	public static Map addSensitiveWordToHashMap(List list) {
		Set<String> keyWordSet = new HashSet<>(list);
		sensitiveWordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
		
		String key = null;  
		Map nowMap = null;
		Map<String, String> newWorMap = null;
		//迭代keyWordSet
		Iterator<String> iterator = keyWordSet.iterator();
		while(iterator.hasNext()){
			key = iterator.next();    //关键字
			nowMap = sensitiveWordMap;
			for(int i = 0 ; i < key.length() ; i++){
				char keyChar = key.charAt(i);       //转换成char型
				Object wordMap = nowMap.get(keyChar);       //获取
				
				if(wordMap != null){        //如果存在该key，直接赋值
					nowMap = (Map) wordMap;
				}
				else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
					newWorMap = new HashMap<String,String>();
					newWorMap.put("isEnd", "0");     //不是最后一个
					nowMap.put(keyChar, newWorMap);
					nowMap = newWorMap;
				}
				
				if(i == key.length() - 1){
					nowMap.put("isEnd", "1");    //最后一个
				}
			}
		}
		return sensitiveWordMap;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<SensitiveWord> list = new ArrayList<>();
		SensitiveWord s1 = new SensitiveWord();
		s1.setId("1");
		s1.setSensitiveWord("三级片");
		s1.setAddTime(Util.dateToStr(new Date()));
		list.add(s1);
		SensitiveWord s2 = new SensitiveWord();
		s2.setId("11");
		s2.setSensitiveWord("金正恩");
		s2.setAddTime(Util.dateToStr(new Date()));
		list.add(s2);
		SensitiveWord s3 = new SensitiveWord();
		s3.setId("111");
		s3.setSensitiveWord("习近平");
		s3.setAddTime(Util.dateToStr(new Date()));
		list.add(s3);
		/*list.add("三级片");
		list.add("法轮功");
		list.add("习近平");
		list.add("金正恩");*/
		List<String> sList = new ArrayList<>();
		sList = list.stream().map(s->s.getSensitiveWord()).collect(Collectors.toList());
		String str = "据说这是一个测试三级片敏感词的语句库，其中含有金正恩这个最牛80后，与习近平见了几次面，谈了几次话的名字";
		System.out.println("oldStr:"+str);
		String newStr = replaceSensitiveWord(str, 2, "*",sList);
		System.out.println("newStr:"+newStr);
	}

}
