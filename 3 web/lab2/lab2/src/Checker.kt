package com.dstresdf

object Checker {

    fun checkHit(request: PointRequest): Boolean {
        val (x,y,r) = request;
        val circle = (x <= 0 && y <= 0 && (x*x + y*y <= (r/2)*(r/2)))
        val rect = (x <= 0 && y >= 0 && (x >= -r && y <= (r/2)))
        val triangle = (x >= 0 && y <= 0 && y >= (x-r))

        return circle || rect || triangle;
    }
}