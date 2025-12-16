package com.example.demo.graph;

import com.example.demo.graph.node.InputProcessingNode;
import com.example.demo.graph.node.ExecutionNode;
import com.example.demo.graph.node.OutputProcessingNode;
import org.bsc.langgraph4j.GraphStateException;
import org.bsc.langgraph4j.StateGraph;
import org.bsc.langgraph4j.state.AgentState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphConfig {

    private final InputProcessingNode inputProcessingNode;
    private final ExecutionNode executionNode;
    private final OutputProcessingNode outputProcessingNode;

    public GraphConfig(InputProcessingNode inputProcessingNode,
                       ExecutionNode executionNode,
                       OutputProcessingNode outputProcessingNode) {
        this.inputProcessingNode = inputProcessingNode;
        this.executionNode = executionNode;
        this.outputProcessingNode = outputProcessingNode;
    }

    @Bean
    public StateGraph<AgentState> taskProcessingGraph() throws GraphStateException {
        StateGraph<AgentState> graph = new StateGraph<>(AgentState::new);

        // 添加节点
        graph.addNode("input_processing", inputProcessingNode);
        graph.addNode("execution", executionNode);
        graph.addNode("output_processing", outputProcessingNode);

        // 设置边（定义执行顺序）
        graph.addEdge(StateGraph.START, "input_processing");
        graph.addEdge("input_processing", "execution");
        graph.addEdge("execution", "output_processing");
        graph.addEdge("output_processing", StateGraph.END);

        return graph;
    }
}