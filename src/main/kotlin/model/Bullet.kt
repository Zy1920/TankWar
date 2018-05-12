package model

import Config
import business.AttackAble
import business.AutoMoveAble
import business.DestoryAble
import business.SufferAble
import enmus.Direction
import org.itheima.kotlin.game.core.Painter


/**
 * ClassName:Bullet
 * Description:子弹
 */
class Bullet(override val owner: View,override val direction: Direction, block: (Int, Int) -> Pair<Int, Int>) : View,
        AutoMoveAble, DestoryAble, AttackAble,SufferAble {
    override var blood: Int = 1

    override fun notifySuffer(attack: AttackAble): Array<View>? {
        return arrayOf(Blast(x,y))
    }

    //子弹的攻击力
    override val attackPower: Int = 1
    private var needDestory: Boolean = false

    override fun needDestory(): Boolean {
        //判断是否销毁
        if(needDestory)return true

        if (x <= -width) return true//屏幕左边
        if (x >= Config.gameWidth) return true//屏幕右边
        if (y <= -height) return true//上边
        if (y >= Config.gameHeight) return true//下边
        return false
    }

    override val speed: Int = 8
    //自动移动
    override fun autoMove() {
        when (direction) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
    }

    override var x: Int = 0
    override var y: Int = 0

    override var width: Int = 0
    override var height: Int = 0
    val imgPath by lazy {
        when (direction) {
            Direction.UP -> "img/bullet_u.gif"
            Direction.DOWN -> "img/bullet_d.gif"
            Direction.LEFT -> "img/bullet_l.gif"
            Direction.RIGHT -> "img/bullet_r.gif"
        }
    }

    init {
        //获取子弹的宽度和高度

        val size = Painter.size(imgPath)
        width = size[0]
        height = size[1]

        val pair = block(width, height)
        x = pair.first
        y = pair.second
    }

    override fun draw() =
            Painter.drawImage(imgPath, x, y)

    override fun isCollision(suffer: SufferAble): Boolean {
        return checkCollision(block = suffer)
    }

    override fun notifyAttack(suffer: SufferAble) {
        needDestory = true
    }
}