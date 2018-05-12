package model

import Config
import business.DestoryAble
import org.itheima.kotlin.game.core.Painter


/**
 * ClassName:Blast
 * Description:爆炸物
 */
class Blast(override var x: Int, override var y: Int) :View,DestoryAble {
    override fun needDestory(): Boolean = index==blastList.size


    override var width: Int = Config.blockSize
    override var height: Int = Config.blockSize
    //集合保存爆炸物地址
    val blastList = arrayListOf<String>()
    //图片index
    var index = 0
    init {
        (1..32).forEach {
            blastList.add("img/blast_${it}.png")
        }
    }
    override fun draw() {
        index%=blastList.size
        Painter.drawImage(blastList[index],x,y)
        //下一次绘制下一张图片
        index++
    }
}