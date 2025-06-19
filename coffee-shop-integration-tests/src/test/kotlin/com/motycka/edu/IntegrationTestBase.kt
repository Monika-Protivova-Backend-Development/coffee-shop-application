package com.motycka.edu

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FreeSpec
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

abstract class IntegrationTestBase : FreeSpec() {

    override fun isolationMode() = IsolationMode.InstancePerTest

    companion object {
        private val container = ApplicationContainer().apply {
            start()
        }
    }

    protected val baseUrl by lazy { container.getBaseUrl() }

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    protected fun runTest(block: suspend (client: HttpClient) -> Unit) {
        runBlocking {
            block(client)
        }
    }
}
