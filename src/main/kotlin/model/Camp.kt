package model

import Config
import business.AttackAble
import business.BlockAble
import business.DestoryAble
import business.SufferAble
import org.itheima.kotlin.game.core.Painter


/**
 * ClassName:Camp
 * Description:大本营
 */
class Camp(override var x: Int, override var y: Int) :View,BlockAble,SufferAble,DestoryAble {
    override fun needDestory()  = blood<=0

    //血量
    override var blood: Int = 12
    override fun notifySuffer(attack: AttackAble): Array<View>? {
        blood -= attack.attackPower

        //判断血量小于3或者血量小于6  需要有爆炸的效果返回 返回多个爆炸的效果View
        if(blood==3||blood==6){
            if (blood == 3 || blood == 6) {
                val x = x - 32
                val y = y - 32
                return arrayOf(Blast(x, y)
                        , Blast(x + 32, y)
                        , Blast(x + Config.blockSize, y)
                        , Blast(x + Config.blockSize + 32, y)
                        , Blast(x + Config.blockSize * 2, y)
                        , Blast(x, y + 32)
                        , Blast(x, y + Config.blockSize)
                        , Blast(x, y + Config.blockSize + 32)
                        , Blast(x + Config.blockSize * 2, y + 32)
                        , Blast(x + Config.blockSize * 2, y + Config.blockSize)
                        , Blast(x + Config.blockSize * 2, y + Config.blockSize + 32))
            }
        }
        return null//不需要效果
    }

    override var width: Int = 2*Config.blockSize
    override var height: Int = Config.blockSize+32

    override fun draw() {

        //血量小于3  没有墙
        if(blood<=3){
            //重新确定x和y  以及宽度和高度
            x = (Config.gameWidth-Config.blockSize)/2
            y = Config.gameHeight-Config.blockSize
            width = Config.blockSize
            height = Config.blockSize
            Painter.drawImage("img/camp.gif",x,y)
        }else if(blood<=6){
        //血量小于6  绘制砖墙
            Painter.drawImage("img/camp.gif",x+32,y+32)
            //绘制周围墙
            Painter.drawImage("img/wall_small.gif",x,y)
            Painter.drawImage("img/wall_small.gif",x+32,y)
            Painter.drawImage("img/wall_small.gif",x+64,y)
            Painter.drawImage("img/wall_small.gif",x+96,y)
            Painter.drawImage("img/wall_small.gif",x,y+32)
            Painter.drawImage("img/wall_small.gif",x,y+64)
            Painter.drawImage("img/wall_small.gif",x+96,y+32)
            Painter.drawImage("img/wall_small.gif",x+96,y+64)
        }else{
            Painter.drawImage("img/camp.gif",x+32,y+32)
            //绘制周围墙
            Painter.drawImage("img/steel_small.gif",x,y)
            Painter.drawImage("img/steel_small.gif",x+32,y)
            Painter.drawImage("img/steel_small.gif",x+64,y)
            Painter.drawImage("img/steel_small.gif",x+96,y)
            Painter.drawImage("img/steel_small.gif",x,y+32)
            Painter.drawImage("img/steel_small.gif",x,y+64)
            Painter.drawImage("img/steel_small.gif",x+96,y+32)
            Painter.drawImage("img/steel_small.gif",x+96,y+64)
        }
    }

    override fun showDestory(): Array<View>? {
        return arrayOf(Blast(x - 32, y - 32)
                , Blast(x, y - 32)
                , Blast(x + 32, y - 32)
                , Blast(x - 32, y)
                , Blast(x, y)
                , Blast(x + 32, y)
                , Blast(x - 32, y + 32)
                , Blast(x, y + 32)
                , Blast(x + 32, y + 32))

    }
}