package com.hgf.tool.normal;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分词
 *
 * @author huanggf
 * @date 2024/6/17
 */
public class WordSegmentationUtil {

    /**
     * 简体中文分词
     * @param text 简体中文
     * @return 分词列表
     */
    public static List<String> segmentSimplifiedChinese(String text) {
        List<Term> terms = HanLP.segment(text);
        return terms.stream().map(term -> term.word).collect(Collectors.toList());
    }

    /**
     * 繁体中文分词（先转简体，分词后，转为繁体）
     * @param text 繁体中文
     * @return 分词列表
     */
    public static List<String> segmentTraditionalChinese(String text) {
        String simpleText = HanLP.convertToSimplifiedChinese(text);
        List<Term> terms = HanLP.segment(simpleText);
        return terms.stream().map(term -> HanLP.convertToTraditionalChinese(term.word)).collect(Collectors.toList());
    }

}
