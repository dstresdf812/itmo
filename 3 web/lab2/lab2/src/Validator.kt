package com.dstresdf

object Validator {
    fun isValid(point: PointRequest): Boolean {
        val (x,y,r) = point;
        val resultX = arrayOf(-3.0,-2.0,-1.0,0.0,1.0,2.0,3.0,4.0,5.0).contains(x);
        val resultY = y in -5.0..5.0;
        val resultR = arrayOf(1.0,2.0,3.0,4.0,5.0).contains(r);
        return resultX && resultY && resultR;
    }
}