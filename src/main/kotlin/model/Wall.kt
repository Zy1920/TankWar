package model

import Config
import business.AttackAble
import business.BlockAble
import business.DestoryAble
import business.SufferAble
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter


/**
 * ClassName:Wall
 * Description:砖墙
 */
class Wall(override var x: Int, override var y: Int) :BlockAble,SufferAble,DestoryAble {
    override fun needDestory(): Boolean = blood==0

    override var blood: Int = 3

    override var width: Int = Config.blockSize
    override var height: Int = Config.blockSize

    override fun draw() {
        Painter.drawImage("img/wall.gif",x,y)
    }
    override fun notifySuffer(attack: AttackAble):Array<View>? {
        //减少一定的血量
        blood -= attack.attackPower
        //播放疼的声音
        Composer.play("snd/hit.wav")
        return arrayOf(Blast(x, y))
    }
}