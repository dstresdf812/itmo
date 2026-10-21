package com.dstresdf

object RequestProcessor {
    fun process(request: PointRequest, start: Long): PointResult {
        if (!Validator.isValid(request)) {
            throw IllegalArgumentException("Invalid args")
        }


        val hit = Checker.checkHit(request);

        val scriptTime = (System.nanoTime() - start) / 1_000_000.0;

        val result = PointResult(
            x = request.x,
            y = request.y,
            r = request.r,
            hit = hit,
            currentTime = java.time.LocalTime.now().toString(),
            scriptTime = scriptTime

        )

        return result
    }
}