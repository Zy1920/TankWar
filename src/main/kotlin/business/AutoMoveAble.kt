package business

import enmus.Direction


/**
 * ClassName:AutoMoveAble
 * Description:自动移动的能力
 */
interface AutoMoveAble {
    //自动移动的方向
    val direction:Direction
    //自动移动的速度
    val speed:Int
    //自动移动
    fun autoMove()
}