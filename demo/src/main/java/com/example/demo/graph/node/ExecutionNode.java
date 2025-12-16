package com.example.demo.graph.node;

import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.state.AgentState;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class ExecutionNode implements AsyncNodeAction<AgentState> {

    @Override
    public CompletableFuture<Map<String, Object>> apply(AgentState state) {
        // 获取已处理的输入
        String processedInput = (String) state.data().get("processed_input");

        // 执行具体业务逻辑
        String executionResult = executeTask(processedInput);

        // 返回执行结果
        return CompletableFuture.completedFuture(Map.of("execution_result", executionResult));
    }

    private String executeTask(String input) {
        // 实现具体的执行逻辑
        // 这里可以根据实际需求调用其他服务或组件
        return "Executed: " + input;
    }
}