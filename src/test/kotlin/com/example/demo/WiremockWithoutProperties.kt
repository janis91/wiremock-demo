package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0, files = ["classpath:/mocks"])
class WiremockWithoutProperties {
    @Value("\${foo.url}")
    lateinit var url: String

    @Test
    fun test() {
        val restTemplate = RestTemplateBuilder().rootUri(url).build()

        val result = restTemplate.getForObject("/test", String::class.java)

        with(result) {
            assertThat(this).contains("bar")
            assertThat(this).contains("foo")
        }
    }
}
