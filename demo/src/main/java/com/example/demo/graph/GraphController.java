package com.example.demo.graph;

import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.GraphStateException;
import org.bsc.langgraph4j.StateGraph;
import org.bsc.langgraph4j.state.AgentState;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/graph")
public class GraphController {

    private final CompiledGraph<AgentState> compiledGraph;

    public GraphController(StateGraph<AgentState> taskProcessingGraph) throws GraphStateException {
        this.compiledGraph = taskProcessingGraph.compile();
    }

    @PostMapping("/process")
    public Map<String, Object> processTask(@RequestBody Map<String, Object> input) throws Exception {
        // 创建初始状态
        Map<String, Object> initialState = new HashMap<>(input);

        // 执行图
        Optional<AgentState> result = compiledGraph.invoke(initialState);

        // 返回最终结果
        return result.get().data();
    }

    @PostMapping("/processAsync")
    public CompletableFuture<Map<String, Object>> processTaskAsync(@RequestBody Map<String, Object> input) {
        // 创建初始状态
        Map<String, Object> initialState = new HashMap<>(input);

        // 异步执行图
//        return compiledGraph.invokeAsync(initialState)
//                .thenApply(AgentState::data);
        return null;
    }
}