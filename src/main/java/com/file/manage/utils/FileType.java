package com.file.manage.utils;


import com.google.common.collect.Lists;

import java.util.List;

public class FileType {

    public static List<String> text = Lists.newArrayListWithCapacity(16);
    public static List<String> video = Lists.newArrayListWithCapacity(16);
    public static List<String> music = Lists.newArrayListWithCapacity(16);

    static {
        text.add("txt");
        text.add("pdf");
        text.add("md");
        text.add("doc");
        text.add("docx");
        text.add("ppt");
        text.add("pptx");
        text.add("xml");
        text.add("xls");
        text.add("xlsx");
        text.add("png");
        text.add("jpg");
        text.add("svg");

        video.add("mp4");
        video.add("avi");
        video.add("rmvb");
        video.add("gpg");

        music.add("ma4");
        music.add("mp3");
    }
}
