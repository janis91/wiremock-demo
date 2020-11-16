package com.example.demo

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("enabled")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock
class WiremockWithProperties {
    @Value("\${foo.url}")
    lateinit var url: String

    @Test
    fun test() {
        val restTemplate = RestTemplateBuilder().rootUri(url).build()

        val result = restTemplate.getForObject("/test", String::class.java)

        with(result) {
            Assertions.assertThat(this).contains("bar")
            Assertions.assertThat(this).contains("foo")
        }
    }
}
