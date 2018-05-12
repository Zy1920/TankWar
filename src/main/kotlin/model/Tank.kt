package model

import Config
import business.AttackAble
import business.BlockAble
import business.MoveAble
import business.SufferAble
import enmus.Direction
import org.itheima.kotlin.game.core.Painter


/**
 * ClassName:Tank
 * Description:坦克
 */
class Tank(override var x: Int, override var y: Int) : MoveAble,BlockAble,SufferAble {
    //血量
    override var blood: Int = 20

    override fun notifySuffer(attack: AttackAble): Array<View>? {
        blood -= attack.attackPower
        return arrayOf(Blast(x,y))
    }

    override var badBlock: BlockAble? = null
    override var badDirection: Direction? = null
    override var width: Int = Config.blockSize
    override var height: Int = Config.blockSize

    /**
     * 通知碰撞
     */
    override fun notifyCollision(badBlock: BlockAble?, badDirection: Direction?) {
        this.badBlock = badBlock
        this.badDirection = badDirection
    }

    /**
     * 判断是否会碰撞 返回碰撞的方向
     * 返回为null 说明和当前block物体不会发生碰撞
     */
//    override fun willCollision(block: BlockAble): Direction? {
//        //将将要移动的距离加上去
//        var x = this.x
//        var y = this.y
//        when (direction) {
//            Direction.UP -> y -= speed
//            Direction.DOWN -> y += speed
//            Direction.LEFT -> x -= speed
//            Direction.RIGHT -> x += speed
//        }
//
//        var willCollison = checkCollision(newX = x,newY = y,block = block)
//
////        var willCollison = when {
////            block.y + block.height <= y -> false
////            block.y >= y + height -> false
////            block.x + block.width <= x -> false
////            x + width <= block.x -> false
////            else -> true
////        }
//
////        var willCollison = if(block.y+block.height<=y){
////            false
////        }else if(block.y>=y+height){
////            false
////        }else if(block.x+block.width<=x){
////            false
////        }else if(x+width<=block.x){
////            false
////        }else{
////            true
////        }
//        //运动物在阻挡物下面
////        if(block.y+block.height<=y){
////            willCollison = false
////        }else if(block.y>=y+height){
////            willCollison = false
////        }else if(block.x+block.width<=x){
////            willCollison = false
////        }else if(x+width<=block.x){
////            willCollison = false
////        }else{
////            willCollison = true
////        }
//        return if (willCollison) direction else null
//    }

    //坦克移动速度
    override val speed = 8

    //当前坦克方向
    override var direction: Direction = Direction.UP
            //设置值  移动
        set(value) {
            //判断要移动的方向是否是当前方向
            if (direction != value) {//不一致
                field = value
                return
            }

            //是否会碰撞  如果碰撞  就不要再移动了
            if (value == badDirection) return//要移动的方向是阻挡的方向

            //根据方向移动
            when (direction) {
                Direction.UP -> y -= speed
                Direction.DOWN -> y += speed
                Direction.LEFT -> x -= speed
                Direction.RIGHT -> x += speed
            }
            //越界判断
//            if (x < 0) x = 0
//            else if (x > 12 * Config.blockSize) x = 12 * Config.blockSize
//            else if (y < 0) y = 0
//            else if (y > 12 * Config.blockSize) y = 12 * Config.blockSize

        }

    /**
     * 绘制坦克
     */
    override fun draw() = Painter.drawImage(when (direction) {
        Direction.UP -> "img/tank_u.gif"
        Direction.DOWN -> "img/tank_d.gif"
        Direction.LEFT -> "img/tank_l.gif"
        Direction.RIGHT -> "img/tank_r.gif"
    }, x, y)

    /**
     * 发射一枚子弹
     */
    fun shot(): Bullet {
        //子弹Bullet里面获取当前子弹的宽度和高度
        //子弹需要从Tank里面获取计算之后的x和y

        var bulletX = 0
        var bulletY = 0
//        var bulletWidth = 16
//        var bulletHeight = 32


        return Bullet(this,direction) { bulletWidth, bulletHeight ->
            when (direction) {
                Direction.UP -> {
                    bulletX = x + (width - bulletWidth) / 2
                    bulletY = y - bulletHeight / 2
                }
                Direction.DOWN -> {
                    bulletX = x + (width - bulletWidth) / 2
                    bulletY = y + height - bulletHeight / 2
                }
                Direction.LEFT -> {
                    bulletX = x - bulletWidth / 2
                    bulletY = y + (height - bulletHeight) / 2
                }
                Direction.RIGHT -> {
                    bulletX = x + width - bulletWidth / 2
                    bulletY = y + (height - bulletHeight) / 2
                }
            }
            bulletX to bulletY
        }
    }


//    override fun draw() {
//        when(direction){
//            Direction.UP->Painter.drawImage("img/tank_u.gif",x,y)
//            Direction.DOWN->Painter.drawImage("img/tank_d.gif",x,y)
//            Direction.LEFT->Painter.drawImage("img/tank_l.gif",x,y)
//            Direction.RIGHT->Painter.drawImage("img/tank_r.gif",x,y)
//        }
//    }


//    override fun draw() {
//        val imgPath = when(direction){
//            Direction.UP->"img/tank_u.gif"
//            Direction.DOWN->"img/tank_d.gif"
//            Direction.LEFT->"img/tank_l.gif"
//            Direction.RIGHT->"img/tank_r.gif"
//        }
//        Painter.drawImage(imgPath,x,y)
//    }

}