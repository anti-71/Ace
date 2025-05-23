package com.jeunesse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 核心类：用于实现所有功能
public class LL1Analyzer {
    private Map<String, List<String>> original_productions = new LinkedHashMap<>(); // 存储原产生式
    private Map<String, List<String>> processed_productions = new LinkedHashMap<>(); // 存储处理后的产生式
    private Set<String> nonTerminals = new HashSet<>(); // 存储所有非终结符
    private Set<String> terminals = new HashSet<>(); // 存储所有终结符
    private Map<String, Set<String>> firstSets = new LinkedHashMap<>(); // 存储First集
    private Map<String, Set<String>> followSets = new LinkedHashMap<>(); // 存储Follow集
    private Map<String, Map<String, String>> ll1Table = new HashMap<>(); // 存储LL(1)分析表
    private List<Map<String, String>> parseTable = new ArrayList<>(); // 存储分析表
    private String s; // 存储输入的字符串

    // 实现文法的输入功能，保存到original_productions中
    public void inputGrammar(String grammarText) {
        original_productions.clear(); // 清空现有产生式
        processed_productions.clear();
        String[] lines = grammarText.split("\n"); // 按行分割

        // 遍历每一行，解析产生式
        for (String line : lines) {
            // 去除行首尾的空格和换行符、空行
            line = line.trim();
            if (line.isEmpty()) continue;

            // 按"→"分割
            String[] parts = line.split("→");
            // 处理左右两边的空格
            String left = parts[0].trim();
            String right = parts[1].trim();
            nonTerminals.add(left); // 添加非终结符

            // 处理右边可能有的选择
            original_productions.putIfAbsent(left, new ArrayList<>());
            for (String r : right.split("\\|")) {
                original_productions.get(left).add(r.trim());
            }
        }

        // 消除左递归，无论有没有左递归
        RemoveLeftRecursion();
        // 获取所有终结符
        collectTerminals();
    }

    private void collectTerminals() {
        terminals.clear();
        // 预定义需要排除的特殊符号（非终结符特征）
        Set<String> excludedSymbols = new HashSet<>(Arrays.asList("ε", "'"));

        for (List<String> productions : processed_productions.values()) {
            for (String prod : productions) {
                // 通用符号分割：按空格分割，并处理括号等特殊符号
                String[] tokens = prod.split("\\s+");

                for (String token : tokens) {
                    // 处理括号等特殊符号的独立分割
                    List<String> symbols = splitCompoundToken(token);

                    for (String symbol : symbols) {
                        // 排除条件：非终结符、ε、排除列表中的符号（如'）
                        if (!nonTerminals.contains(symbol)
                                && !excludedSymbols.contains(symbol)
                                && !symbol.isEmpty()) {
                            terminals.add(symbol);
                        }
                    }
                }
            }
        }
    }

    // 分割复合符号
    private List<String> splitCompoundToken(String token) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        for (char c : token.toCharArray()) {
            if (isSpecialSymbol(c)) { // 检测括号等特殊符号
                if (current.length() > 0) {
                    result.add(current.toString());
                    current.setLength(0);
                }
                result.add(String.valueOf(c));
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0) {
            result.add(current.toString());
        }
        return result;
    }

    // 判断是否为需要独立分割的特殊符号（如括号、运算符等）
    private boolean isSpecialSymbol(char c) {
        return "(){}[],;".indexOf(c) != -1;
    }

    // 消除直接左递归，保存到processed_productions中
    private void RemoveLeftRecursion() {
        List<String> orderednonTerminals = new ArrayList<>(original_productions.keySet());

        for (int i = 0; i < orderednonTerminals.size(); i++) {
            String A = orderednonTerminals.get(i);
            List<String> productions = new ArrayList<>(original_productions.get(A));
            List<String> leftRecursive = new ArrayList<>();
            List<String> nonLeftRecursive = new ArrayList<>();

            for (String prod : productions) {
                if (prod.startsWith(A)) {
                    // 如果产生式以 A 开头，则是直接左递归
                    leftRecursive.add(prod.substring(A.length()).trim());
                } else {
                    // 否则是非左递归产生式
                    nonLeftRecursive.add(prod);
                }
            }

            // 如果存在左递归产生式
            if (!leftRecursive.isEmpty()) {
                String newNonTerminal = A + "'";
                nonTerminals.add(newNonTerminal);
                List<String> newProduction1 = new ArrayList<>();
                List<String> newProduction2 = new ArrayList<>();

                // 更新非左递归产生式，使其指向新的非终结符
                for (String prod : nonLeftRecursive) {
                    newProduction1.add(prod + " " + newNonTerminal);
                }
                // 处理左递归产生式，使其指向新的非终结符
                for (String prod : leftRecursive) {
                    newProduction2.add(prod + " " + newNonTerminal);
                }
                // 添加空产生式 ε 到新的非终结符的产生式中
                newProduction2.add("ε");

                // 将新的产生式添加到处理后的文法中
                processed_productions.put(A, newProduction1);
                processed_productions.put(newNonTerminal, newProduction2);
            } else {
                // 如果没有左递归，直接将原来的产生式添加到处理后的文法中
                processed_productions.put(A, nonLeftRecursive);
            }
        }
    }

    // 实现计算First集的功能
    public void calculateFirstSets() {
        firstSets.clear();
        for (String nonTerminal : processed_productions.keySet()) {
            getFirst(nonTerminal);
        }
        // 输出结果到文件（覆盖写入）
        writeFirstSetsToFile("D:/Ace/Java/LL1/src/files/first_sets.txt");
    }

    private void writeFirstSetsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("===== First Sets =====\n");
            for (Map.Entry<String, Set<String>> entry : firstSets.entrySet()) {
                writer.write(entry.getKey() + ": " + String.join(", ", entry.getValue()) + "\n");
            }
        } catch (IOException e) {
            System.err.println("写入文件失败: " + e.getMessage());
        }
    }

    private Set<String> getFirst(String symbol) {
        // 如果已经计算过，直接返回
        if (firstSets.containsKey(symbol)) {
            return firstSets.get(symbol);
        }

        Set<String> first = new HashSet<>();
        // 如果是终结符或ε
        if (terminals.contains(symbol) || symbol.equals("ε")) {
            first.add(symbol);
            return first;
        }

        // 处理非终结符的所有产生式
        List<String> productions = processed_productions.get(symbol);
        if (productions == null || productions.isEmpty()) {
            // 如果没有定义产生式，返回空的 first 集合或者按需处理
            return first;
        }

        for (String production : productions) {
            List<String> symbols = new ArrayList<>();
            // 使用增强分割逻辑处理每个符号
            for (String token : production.split("\\s+")) {
                symbols.addAll(splitCompoundToken(token));
            }
            boolean canProduceEpsilon = true; // 表示当前产生式是否可以产生ε

            for (String s : symbols) {
                Set<String> sFirst = getFirst(s);
                first.addAll(sFirst);
                first.remove("ε"); // 暂时移除ε

                if (!sFirst.contains("ε")) {
                    canProduceEpsilon = false;
                    break;
                }
            }

            if (canProduceEpsilon) {
                first.add("ε");
            }
        }

        firstSets.put(symbol, first);
        return first;
    }

    // 实现计算Follow集的功能
    public void calculateFollowSets() {
        followSets.clear();
        // 初始化所有非终结符的 Follow 集
        for (String nonTerminal : processed_productions.keySet()) {
            followSets.put(nonTerminal, new HashSet<>());
        }

        // 显式指定起始符号（假设第一个非终结符是起始符号）
        String startSymbol = new ArrayList<>(processed_productions.keySet()).get(0);
        followSets.get(startSymbol).add("$");

        boolean changed;
        do {
            changed = false;
            for (Map.Entry<String, List<String>> entry : processed_productions.entrySet()) {
                String A = entry.getKey(); // 产生式左部 A
                for (String production : entry.getValue()) {
                    // 增强符号分割：处理复合符号（如括号）
                    List<String> symbols = new ArrayList<>();
                    for (String token : production.split("\\s+")) {
                        symbols.addAll(splitCompoundToken(token));
                    }

                    for (int i = 0; i < symbols.size(); i++) {
                        String B = symbols.get(i);
                        if (!nonTerminals.contains(B)) continue; // 只处理非终结符

                        Set<String> followB = followSets.get(B);
                        int oldSize = followB.size();

                        // Case 1: B 后面有符号 β
                        if (i < symbols.size() - 1) {
                            String betaFirstSymbol = symbols.get(i + 1);
                            Set<String> firstBeta = getFirst(betaFirstSymbol);

                            // 将 FIRST(β) 加入 FOLLOW(B)
                            followB.addAll(firstBeta);
                            // 如果 FIRST(β) 包含 ε，需要继续处理后续符号
                            boolean betaCanBeEpsilon = firstBeta.contains("ε");
                            int j = i + 1;
                            while (betaCanBeEpsilon && j < symbols.size()) {
                                betaCanBeEpsilon = false;
                                Set<String> firstNext = getFirst(symbols.get(j));
                                followB.addAll(firstNext);
                                if (firstNext.contains("ε")) {
                                    betaCanBeEpsilon = true;
                                    j++;
                                }
                            }

                            // 如果所有 β 都能推导出 ε，将 FOLLOW(A) 加入 FOLLOW(B)
                            if (betaCanBeEpsilon) {
                                followB.addAll(followSets.get(A));
                            }
                        }
                        // Case 2: B 是产生式最后一个符号
                        else {
                            followB.addAll(followSets.get(A));
                        }

                        // 标记是否发生变化
                        if (followB.size() != oldSize) {
                            changed = true;
                        }
                    }
                }
            }
        } while (changed);

        // 输出结果到文件（覆盖写入）
        writeFollowSetsToFile("D:/Ace/Java/LL1/src/files/follow_sets.txt");
    }

    private void writeFollowSetsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("===== Follow Sets =====\n");
            for (Map.Entry<String, Set<String>> entry : followSets.entrySet()) {
                writer.write(entry.getKey() + ": " + String.join(", ", entry.getValue()) + "\n");
            }
        } catch (IOException e) {
            System.err.println("写入文件失败: " + e.getMessage());
        }
    }

    // 实现生成预测分析表的功能
    public void generateLL1Table() {
        ll1Table.clear();

        // 初始化分析表，为每个非终结符创建条目
        for (String nonTerminal : processed_productions.keySet()) {
            ll1Table.put(nonTerminal, new HashMap<>());
        }

        // 遍历所有非终结符的产生式
        for (Map.Entry<String, List<String>> entry : processed_productions.entrySet()) {
            String A = entry.getKey(); // 当前非终结符
            List<String> productions = entry.getValue();

            for (String production : productions) {
                // 增强符号分割：处理复合符号（如括号）
                List<String> symbols = new ArrayList<>();
                for (String token : production.split("\\s+")) {
                    symbols.addAll(splitCompoundToken(token));
                }

                // 计算产生式右侧的FIRST集
                Set<String> firstAlpha = new HashSet<>();
                boolean canDeriveEpsilon = true;

                for (String symbol : symbols) {
                    Set<String> symbolFirst = getFirst(symbol);
                    firstAlpha.addAll(symbolFirst);
                    firstAlpha.remove("ε"); // 临时移除ε

                    if (!symbolFirst.contains("ε")) {
                        canDeriveEpsilon = false;
                        break;
                    }
                }

                // 如果产生式可以推导ε，加入FOLLOW(A)
                if (canDeriveEpsilon) {
                    firstAlpha.addAll(followSets.get(A));
                }

                // 填充LL(1)表
                for (String terminal : firstAlpha) {
                    // 处理特殊符号（如括号）的显示
                    String formattedProduction = production.replace(" ", " ");
                    ll1Table.get(A).put(terminal, formattedProduction);
                }

                // 处理空产生式（ε）
                if (symbols.contains("ε")) {
                    for (String terminal : followSets.get(A)) {
                        ll1Table.get(A).put(terminal, "ε");
                    }
                }
            }
        }

        // 写入文件
        writeLL1TableToFile("D:/Ace/Java/LL1/src/files/ll1_table.txt");
    }

    private void writeLL1TableToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("===== LL(1) Predictive Parsing Table =====\n");
            for (String nonTerminal : ll1Table.keySet()) {
                Map<String, String> row = ll1Table.get(nonTerminal);
                writer.write(nonTerminal + ":\n");
                for (Map.Entry<String, String> entry : row.entrySet()) {
                    writer.write("  " + entry.getKey() + " → " + entry.getValue() + "\n");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.err.println("写入文件失败: " + e.getMessage());
        }
    }

    // 实现生成解析过程表格的功能
    public void generateParseTable() {
        parseTable.clear();

        // Tokenize输入（保留原始符号）
        List<String> inputTokens = tokenizeInput(s);
        inputTokens.add("$");

        // 初始化栈
        Deque<String> stack = new ArrayDeque<>();
        String startSymbol = new ArrayList<>(processed_productions.keySet()).get(0);
        stack.push("$");
        stack.push(startSymbol);

        int step = 1;
        int currentInputIndex = 0;
        String currentInputSymbol = inputTokens.get(currentInputIndex);

        while (true) {
            Map<String, String> row = new LinkedHashMap<>();
            row.put("步骤", Integer.toString(step));

            // 构建栈显示字符串
            StringBuilder stackStr = new StringBuilder();
            for (String elem : stack) {
                stackStr.insert(0, elem + " ");
            }
            row.put("解析栈", stackStr.toString().trim());

            // 构建剩余输入显示字符串
            String remainingInput = String.join(" ", inputTokens.subList(currentInputIndex, inputTokens.size()));
            row.put("输入流", remainingInput);

            String top = stack.peek();

            // 处理栈顶为$
            if (top.equals("$")) {
                if (currentInputSymbol.equals("$")) {
                    row.put("动作", "接受");
                    parseTable.add(row);
                    break;
                } else {
                    row.put("动作", "错误：输入未结束");
                    parseTable.add(row);
                    break;
                }
            }

            // 处理栈顶为终结符
            else if (terminals.contains(top) || top.equals("ε")) {
                String expectedToken = top;
                // 动态分类当前输入符号
                String actualTokenType = classifyToken(currentInputSymbol);

                if (expectedToken.equals(actualTokenType)) {
                    stack.pop();
                    currentInputIndex++;
                    currentInputSymbol = (currentInputIndex < inputTokens.size()) ?
                            inputTokens.get(currentInputIndex) : "$";
                    row.put("动作", "匹配 " + currentInputSymbol);
                } else {
                    row.put("动作", "错误：期待 " + expectedToken + "，但输入是 " + currentInputSymbol);
                    parseTable.add(row);
                    break;
                }
            }

            // 处理栈顶为非终结符
            else if (nonTerminals.contains(top)) {
                // 动态分类当前输入符号
                String tokenType = classifyToken(currentInputSymbol);
                Map<String, String> tableRow = ll1Table.get(top);
                String production = (tableRow != null) ? tableRow.get(tokenType) : null;

                if (production == null) {
                    row.put("动作", "错误：无产生式 " + top + " → [" + tokenType + "]");
                    parseTable.add(row);
                    break;
                } else {
                    stack.pop();
                    if (!production.equals("ε")) {
                        List<String> symbols = new ArrayList<>();
                        for (String token : production.split("\\s+")) {
                            symbols.addAll(splitCompoundToken(token));
                        }
                        Collections.reverse(symbols);
                        for (String symbol : symbols) {
                            if (!symbol.equals("ε")) stack.push(symbol);
                        }
                    }
                    row.put("动作", "应用 " + top + " → " + production);
                }
            }

            // 无效符号
            else {
                row.put("动作", "错误：无效符号 " + top);
                parseTable.add(row);
                break;
            }

            parseTable.add(row);
            step++;
        }

        writeParseTableToFile("D:/Ace/Java/LL1/src/files/parse_table.txt");
    }

    // 符号分类逻辑
    private String classifyToken(String token) {
        if (token.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            return "id";  // 标识符映射到id
        } else if (token.matches("[0-9]+")) {
            return "number"; // 数字映射到num
        } else {
            return token; // 其他符号保持原样
        }
    }

    // 修改后的tokenizeInput（保留原始符号）
    private List<String> tokenizeInput(String input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
                currentToken.append(c);
            } else {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
                if (!Character.isWhitespace(c)) {
                    tokens.add(String.valueOf(c));
                }
            }
        }
        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }

        return tokens;
    }

    public void writeParseTableToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("LL(1) 语法分析过程\n\n");
            // 调整列宽
            writer.write(String.format("%-10s %-50s %-30s %s\n",
                    "步骤", "解析栈", "输入流", "动作"));

            for (Map<String, String> row : parseTable) {
                // 调整列宽
                writer.write(String.format("%-10s %-50s %-30s %s\n",
                        row.get("步骤"),
                        row.get("解析栈"),
                        row.get("输入流"),
                        row.get("动作")));
            }
        } catch (IOException e) {
            System.err.println("写入文件失败: " + e.getMessage());
        }
    }

    // 实现字符串输入功能
    public void inputString(String text) {
        s = text.trim();
    }
}
