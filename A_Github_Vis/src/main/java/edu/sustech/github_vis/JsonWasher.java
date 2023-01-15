package edu.sustech.github_vis;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class JsonWasher {

    static List<FileWriter> fileWriters = new ArrayList<>();


    static void writeContent(int index, Character character) throws IOException {
        FileWriter temp = fileWriters.get(index);
        temp.write(character);
        temp.flush();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        String srcPath = "/Users/blank/repo/Github_VP/github_vis/src/main/resources/static/test.json";
//        String targetPath = "/Users/blank/repo/Github_VP/github_vis/src/main/resources/static/vscode/issues.json";

        Stack<Character> stack = new Stack<>();
        String srcPath = "/Users/blank/repo/Github_VP/github_vis/src/main/resources/static/test.json";
        String targetPath = "/Users/blank/repo/Github_VP/github_vis/src/main/resources/static/vscode/issues";
        FileReader fr = new FileReader(new File(srcPath));

        for (int i = 0; i < 30; i++) {
            String targetFile = targetPath + "/issues" + i + ".json";
            fileWriters.add(new FileWriter(new File(targetFile)));
        }

        int index = 0;
        int temp = 0;
        int count = 0;
        boolean startFlag = false;
        boolean contentFlag = false;
        while ((temp = fr.read()) != -1) {
            Character c = (char) temp;
            if (c == '\"') {
                contentFlag = !contentFlag;
            }
            if (c == '[') {
                if (!contentFlag) { // 属于json结构体
                    if (stack.empty()) { // stack为空
                        if (!startFlag) { // 是每个文件的开始第一个[
                            writeContent(index, c);
                            stack.push(c);
                            startFlag = true;
                        } else { // 是需要去除的组头[
//                            stack.push(c);
                            continue;
                        }
                    } else { // 正常内部结构体
                        writeContent(index, c);
                        stack.push(c);
                    }
                } else {
                    writeContent(index, c);
                }
            } else if (c == ']') {
                if (index == 0 && stack.size() != 0) {
                    System.out.println(stack);
                }
                if (!contentFlag) { // 属于json结构体
                    if (!stack.empty()) { // stack不为空
                        if (stack.size() == 1) { // 结束一组
//                            stack.pop();
                            writeContent(index, ',');
                            count++; // 叠加组数
                            System.out.println(count);
                            if (count == 2) { // 组数达到上限，更换文件
                                count = 0;
                                writeContent(index, '?');
                                stack.clear();
                                fileWriters.get(index).close();
                                index++;
                                System.out.println(index);
                                Thread.sleep(100000);
                                continue;
                            }
                            if (index == 28) {
                                break;
                            }
                        } else { // 正常内部结构体
                            stack.pop();
                            writeContent(index, c);
                        }
                    }
                } else {
                    writeContent(index, c);
                }
            } else {
                writeContent(index, c);
            }

        }

        for (FileWriter fileWriter : fileWriters) {
            fileWriter.close();
        }
    }
}
