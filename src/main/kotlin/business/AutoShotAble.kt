package business

import model.Bullet


/**
 * ClassName:AutoShotAble
 * Description:自动射击能力(敌方坦克)
 */
interface AutoShotAble {
    //自动射击 子弹有可能不存在
    fun autoShot():Bullet?
}