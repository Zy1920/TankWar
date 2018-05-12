package model

import Config
import business.*
import enmus.Direction
import org.itheima.kotlin.game.core.Painter
import java.util.*


/**
 * ClassName:Enemy
 * Description:敌机
 * MoveAble:作用和阻挡物有碰撞的检测
 */
class Enemy(override var x: Int, override var y: Int) :MoveAble,AutoMoveAble,
        BlockAble,AutoShotAble,SufferAble,DestoryAble {

    override fun needDestory(): Boolean = blood<=0


    override var blood: Int = 3

    override fun notifySuffer(attack: AttackAble): Array<View>? {
        //判断如果是队友发射的子弹  不需要掉血
        if(attack.owner is Enemy)return null

        blood -= attack.attackPower

        return arrayOf(Blast(x,y))//爆炸效果
    }


    override fun autoMove() {
        //判断这个方向是否可以继续前进
        if(direction==badDirection){
            //重新获取新的方向
            direction = randomDirection(badDirection)
            return
        }

        //根据方向移动
        when (direction) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
        //越界判断
//        if (x < 0) x = 0
//        else if (x > 12 * Config.blockSize) x = 12 * Config.blockSize
//        else if (y < 0) y = 0
//        else if (y > 12 * Config.blockSize) y = 12 * Config.blockSize
    }



    override var badBlock: BlockAble? = null
    override var badDirection: Direction? = null

    override val speed: Int = 4
    override var direction: Direction = Direction.DOWN

    override var width: Int = Config.blockSize
    override var height: Int = Config.blockSize

    override fun draw() = Painter.drawImage(when (direction) {
            Direction.UP -> "img/enemy_1_u.gif"
            Direction.DOWN -> "img/enemy_1_d.gif"
            Direction.LEFT -> "img/enemy_1_l.gif"
            Direction.RIGHT -> "img/enemy_1_r.gif"
        }, x, y)


    override fun notifyCollision(badBlock: BlockAble?, badDirection: Direction?) {
        this.badBlock = badBlock
        this.badDirection = badDirection
    }

    /**
     * 随机获取一个新的方向  新的方向和碰撞的badDirection不是一个方向
     */
    private fun randomDirection(badDirection: Direction?): Direction {
        val randomIndex = Random().nextInt(4)
        //新方向
        var newDirection = when(randomIndex){
            0->Direction.UP
            1->Direction.DOWN
            2->Direction.LEFT
            3->Direction.RIGHT
            else->Direction.UP
        }
        //判断是否和碰撞的方向一致
        if(newDirection==badDirection){
            randomDirection(badDirection)
        }
        return newDirection
    }

    var startTime = System.currentTimeMillis()
    //发射子弹的时间间隔
    val timeGap = 1000

    override fun autoShot(): Bullet? {
        //判断时间间隔
        val curTime = System.currentTimeMillis()
        if(curTime-startTime<timeGap)return null
        startTime = curTime

        var bulletX = 0
        var bulletY = 0

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
}