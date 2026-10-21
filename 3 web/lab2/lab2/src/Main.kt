package com.dstresdf

import com.fastcgi.FCGIInterface

val log = System.out;
fun main() {

    val resultsList = mutableListOf<PointResult>();
    val fcgi = FCGIInterface()

    while (fcgi.FCGIaccept() >= 0) {
        try {
            val start = System.nanoTime();
            val method = System.getProperty("REQUEST_METHOD")
            if (method != "POST") {
                sendJson("405 Method Not Allowed", """{"error": "Method not allowed"}""")
                log.println("Method not allowed: $method")
                continue
            }
            val contentLength =
                System.getProperty("CONTENT_LENGTH").toInt()

            val body = String(
                System.`in`.readNBytes(contentLength),
                Charsets.UTF_8
            )
            log.println(body)
            val request = parseRequest(body)
            val result = RequestProcessor.process(request, start)
            resultsList.add(result)
            sendJson("200 OK", responseToJson(result, resultsList))
        } catch (e: IllegalArgumentException) {
            log.println("Bad request: ${e.message}")
            sendJson(
                "400 Bad Request",
                """{"error":"Некорректные параметры запроса"}"""
            )
        } catch (e: Exception) {
            e.printStackTrace(log)
            sendJson(
                "500 Internal Server Error",
                """{"error":"Внутренняя ошибка сервера"}"""
            )
        }
    }
}

fun resultToJson(result: PointResult): String {
    return """
        {
            "x": ${result.x},
            "y": ${result.y},
            "r": ${result.r},
            "hit": ${result.hit},
            "currentTime": "${result.currentTime}",
            "scriptTime": ${result.scriptTime}
        }
    """.trimIndent()
}

fun responseToJson(result: PointResult, resultList: List<PointResult>): String {
    val resultJson = resultList.takeLast(15).joinToString(
        separator = ",",
        prefix = "[",
        postfix = "]"
    ) { resultToJson(it) }

    return """
        {
        "result": ${resultToJson(result)},
        "resultsList": $resultJson
        }
    """.trimIndent()
}

fun sendJson(status: String, json: String) {
    print("Status: $status\r\n")
    print("Content-Type: application/json; charset=UTF-8\r\n")
    print("\r\n")
    print(json)
    System.out.flush()
}

fun parseRequest(request: String): PointRequest {
    var x: Double? = null
    var y: Double? = null
    var r: Double? = null

    val parts = request.split("&")

    for (part in parts) {
        val pair = part.split("=")
        if (pair.size != 2) {
            throw IllegalArgumentException("Invalid args")
        }
        val key = pair[0]
        val value = pair[1]

        if (key == "x") {
            x = value.toDoubleOrNull()
        }

        if (key == "y") {
            y = value.toDoubleOrNull()
        }

        if (key == "r") {
            r = value.toDoubleOrNull()
        }
    }
    if (x == null || y == null || r == null) {
        throw IllegalArgumentException("Invalid request")
    }
    return PointRequest(x, y, r);
}