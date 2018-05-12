package business

import Config
import enmus.Direction
import model.View


/**
 * ClassName:MoveAble
 * Description:移动的能力(坦克)
 */
interface MoveAble:View {
    //碰撞物和碰撞方向
    var badBlock:BlockAble?
    var badDirection:Direction?
    /**
     * 通知碰撞
     */
    fun notifyCollision(badBlock:BlockAble?,badDirection: Direction?)
    /**
     * 判断是否会碰撞 返回碰撞的方向
     * 返回为null 说明和当前block物体不会发生碰撞
     */
    fun willCollision(block:BlockAble): Direction?{
        //将将要移动的距离加上去
        var x = this.x
        var y = this.y
        when (direction) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        var willCollison = checkCollision(newX = x,newY = y,block = block)


        //边界碰撞处理
        if (x < 0)return Direction.LEFT
        else if (x > 12 * Config.blockSize) return Direction.RIGHT
        else if (y < 0) return Direction.UP
        else if (y > 12 * Config.blockSize) return Direction.DOWN

//        var willCollison = when {
//            block.y + block.height <= y -> false
//            block.y >= y + height -> false
//            block.x + block.width <= x -> false
//            x + width <= block.x -> false
//            else -> true
//        }

//        var willCollison = if(block.y+block.height<=y){
//            false
//        }else if(block.y>=y+height){
//            false
//        }else if(block.x+block.width<=x){
//            false
//        }else if(x+width<=block.x){
//            false
//        }else{
//            true
//        }
        //运动物在阻挡物下面
//        if(block.y+block.height<=y){
//            willCollison = false
//        }else if(block.y>=y+height){
//            willCollison = false
//        }else if(block.x+block.width<=x){
//            willCollison = false
//        }else if(x+width<=block.x){
//            willCollison = false
//        }else{
//            willCollison = true
//        }
        return if (willCollison) direction else null
    }

    //坦克移动速度
    val speed:Int

    //当前坦克方向
    var direction: Direction
}