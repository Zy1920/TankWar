package business

import model.View


/**
 * ClassName:AttackAble
 * Description:攻击能力
 */
interface AttackAble:View {
    //攻击者的所有者
    val owner:View

    //攻击力
    val attackPower:Int
    /**
     * 判断是否和当前受攻击对象发生碰撞
     */
    fun isCollision(suffer: SufferAble):Boolean

    /**
     * 通知发生了攻击
     */
    fun notifyAttack(suffer: SufferAble)
}