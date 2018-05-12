package model


/**
 * ClassName:View
 * Description:所有控件的父接口
 */
interface View {
    //坐标
    var x:Int
    var y:Int
    //宽度和高度
    var width:Int
    var height:Int
    //绘制自身的行为
    fun draw()

    //检测是否和传递的碰撞物碰撞
    fun checkCollision(newX:Int=x,newY:Int=y,block:View):Boolean{
        return when {
            block.y + block.height <= newY -> false
            block.y >= newY + height -> false
            block.x + block.width <= newX -> false
            newX + width <= block.x -> false
            else -> true
        }
    }
}