package com.playerdb.playerservice.service.chat;

import com.playerdb.playerservice.model.chat.ChatPrompt;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatClientService.class);
    private final OpenAiService openAiService;

    @Value("${chat.openai.model}")
    private String model;

    @Autowired
    private RestTemplate restTemplate;

    public CompletionResult complete(String prompt) {
        return complete(toRequest(prompt));
    }

    public CompletionRequest toRequest(String prompt) {
        return CompletionRequest.builder().maxTokens(1000).model(model).prompt(prompt)
                .echo(true).user("testing").n(1).build();
    }

    public CompletionResult complete(CompletionRequest completionRequest) {
        LOGGER.info("Creating a completion: {}", completionRequest.toString());
        return this.openAiService.createCompletion(completionRequest);
    }

    public ChatCompletionResult complete(ChatCompletionRequest request) {
        LOGGER.info("Creating chat completion: {}", request);
        return this.openAiService.createChatCompletion(request);
    }

    public ChatCompletionRequest toChatRequest(String prompt) {
        return ChatCompletionRequest.builder().model(model).n(1).maxTokens(1000).user("assistant")
                .messages(List.of(new ChatMessage("user", prompt))).build();
    }

    public String listModels() {
        return restTemplate.getForEntity("http://localhost:11434/api/ps", String.class).getBody();
    }

    public String ollamaChat(ChatPrompt chatPrompt) {
        // Build the request body
        String requestBody = String.format("{\"model\": \"%s\", \"prompt\": \"%s\", \"stream\": false}",
                chatPrompt.getModel(), chatPrompt.getPrompt());

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Build the HttpEntity (request with headers and body)
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Send the POST request to the Ollama API
        return restTemplate.postForEntity("http://localhost:11434/api/generate", request, String.class).getBody();
    }
}
