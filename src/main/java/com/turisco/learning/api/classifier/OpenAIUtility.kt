package com.turisco.learning.api.classifier

import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionChunk
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.core.Role
import com.aallam.openai.api.exception.OpenAIAPIException
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.logging.Logger
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import com.turisco.learning.api.constants.ModelType.GPT_4
import com.turisco.learning.api.prompt.OpenAIPrompts
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.future.future
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

private const val ERROR_KEY = "Erro com a chave da API!"
private const val ERROR_RATE_LIMIT = "Rate Limit Excedido!"
private const val ERROR_SERVER = "Server da API fora do ar!"
private var delayTime: Long = 3

@Component
class OpenAIUtility {
    @OptIn(DelicateCoroutinesApi::class)
    fun openAIResponseString(userPrompt: String): CompletableFuture<String?> {
        return GlobalScope.future {
            return@future withContext(context = Dispatchers.IO) {
                fireOpenAIRequest(GPT_4.type, userPrompt, OpenAIPrompts.animalSpeciesClassifier)
                    .chatCompletion?.choices?.first()?.message?.content
            }
        }
    }

    suspend fun openAIResponseString(
        model: String,
        userPrompt: String,
        systemPrompt: String
    ): String? {
        val response = fireOpenAIRequest(model, userPrompt, systemPrompt)
        val message = response.chatCompletion?.choices?.first()?.message?.content
        return message
    }

    private suspend fun fireOpenAIRequest(
        model: String,
        userPrompt: String,
        systemPrompt: String,
        stream: Boolean? = false
    ): ChatCompletionResponse {
        val apiKey = System.getenv("OPENAI_API_KEY")
        val openAI = OpenAI(
            token = apiKey,
            logging = LoggingConfig(logLevel = LogLevel.None, logger = Logger.Empty),
        )
        val messages = listOf(
            ChatMessage(role = Role.User, content = userPrompt),
            ChatMessage(role = Role.System, content = systemPrompt)
        )
        val request = ChatCompletionRequest(
            model = ModelId(model),
            maxTokens = 2048,
            messages = messages
        )
        var retry = 0
        while (retry++ != 3) {
            try {
                val flow = openAI.chatCompletions(request).takeIf { stream == true }
                val chatCompletion = openAI.chatCompletion(request).takeIf { stream != true }
                return ChatCompletionResponse(flow, chatCompletion)
            } catch (e: OpenAIAPIException) {
                when (e.statusCode) {
                    401 -> throw RuntimeException(ERROR_KEY, e)
                    429 -> println(ERROR_RATE_LIMIT)
                    500, 503 -> println(ERROR_SERVER)
                }
                delay(1000 * delayTime)
                delayTime *= 2
            }
        }
        throw RuntimeException(ERROR_SERVER)
    }
}

private data class ChatCompletionResponse(val flow: Flow<ChatCompletionChunk>?, val chatCompletion: ChatCompletion?)