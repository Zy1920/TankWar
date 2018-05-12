package business

import model.View


/**
 * ClassName:SufferAble
 * Description:受攻击的能力
 */
interface SufferAble:View {
    //血量值
    var blood:Int
    /**
     * 通知被攻击了
     * 返回值就是受攻击产生的回馈view 不想回馈就返回null
     */
    fun notifySuffer(attack: AttackAble):Array<View>?
}