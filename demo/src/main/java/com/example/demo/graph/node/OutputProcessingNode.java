package com.example.demo.graph.node;

import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.state.AgentState;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class OutputProcessingNode implements AsyncNodeAction<AgentState> {

    @Override
    public CompletableFuture<Map<String, Object>> apply(AgentState state) {
        // 获取执行结果
        String executionResult = (String) state.data().get("execution_result");

        // 处理输出结果（例如：格式化、包装等）
        String finalOutput = formatOutput(executionResult);

        // 返回最终输出
        return CompletableFuture.completedFuture(Map.of("final_output", finalOutput));
    }

    private String formatOutput(String result) {
        // 实现具体的输出格式化逻辑
        return "{\n  \"result\": \"" + result + "\",\n  \"status\": \"success\"\n}";
    }
}