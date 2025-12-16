package com.example.demo.graph.node;

import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.state.AgentState;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class InputProcessingNode implements AsyncNodeAction<AgentState> {

    @Override
    public CompletableFuture<Map<String, Object>> apply(AgentState state) {
        // 获取输入数据
        String rawInput = (String) state.data().get("input");

        // 处理输入数据（例如：清洗、验证、格式化等）
        String processedInput = processInput(rawInput);

        // 返回处理后的数据
        return CompletableFuture.completedFuture(Map.of("processed_input", processedInput));
    }

    private String processInput(String input) {
        // 实现具体的输入处理逻辑
        if (input == null) {
            return "";
        }
        // 示例：去除首尾空格，转换为小写
        return input.trim().toLowerCase();
    }
}