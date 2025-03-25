package com.turisco.learning.api.constants

enum class ModelType(val type: String, val length: Int) {
    GPT_4(type = "gpt-4", 8192),
    GPT_4_TURBO(type = "gpt-4-turbo", 16384),
    GPT_4_1106_PREVIEW(type = "gpt-4-1106-preview", 128000),
    GPT_3_5_TURBO(type = "gpt-3.5-turbo", 4096),
    GPT_3_5_TURBO_16K_0613(type = "gpt-3.5-turbo-16k-0613", 16384),
}