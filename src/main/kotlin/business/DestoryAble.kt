package business

import model.View


/**
 * ClassName:DestoryAble
 * Description:销毁能力
 */
interface DestoryAble {
    /**
     * 判断是否需要销毁
     * true 需要销毁
     * false 不需要销毁
     */
    fun needDestory():Boolean
    /**
     * 销毁效果
     */
    fun showDestory():Array<View>?{
        return null
    }
}