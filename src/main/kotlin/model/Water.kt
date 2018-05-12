package model

import Config
import business.BlockAble
import org.itheima.kotlin.game.core.Painter


/**
 * ClassName:Steel
 * Description:水墙
 */
class Water(override var x: Int, override var y: Int) : BlockAble {

    override var width: Int = Config.blockSize
    override var height: Int = Config.blockSize

    override fun draw() {
        Painter.drawImage("img/water.gif", x, y)
    }
}