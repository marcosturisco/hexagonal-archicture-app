package com.turisco.learning.classifier

import com.turisco.learning.api.constants.ModelType.GPT_4
import com.turisco.learning.api.prompt.OpenAIPrompts
import com.turisco.learning.api.classifier.OpenAIUtility
import kotlinx.coroutines.runBlocking

class AnimalSpeciesClassifierTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) = runBlocking {
            System.setErr(java.io.PrintStream(java.io.OutputStream.nullOutputStream()))
            val userPrompt = "Squirrel"
            val systemPrompt = OpenAIPrompts.animalSpeciesClassifier
            val species = OpenAIUtility().openAIResponseString(GPT_4.type, userPrompt, systemPrompt)
            println("Species: $species")
            return@runBlocking
        }
    }
}