
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import javafx.scene.text.Font
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import org.itheima.kotlin.game.core.Window

/**
 * ClassName:GameWindow
 * Description:游戏窗体
 */
class GameWindow:Window() {
    /**
     * 窗体创建
     * 初始化(加载地图)
     */
    override fun onCreate() {
        println("窗体创建了")
    }

    /**
     * 界面渲染 界面存在会一直执行
     * 界面绘制操作
     */
    override fun onDisplay() {
        //绘制一辆坦克
        Painter.drawImage("tank_u.gif",0,0)
        //绘制颜色
        Painter.drawColor(Color.RED,100,100,100,100)
        //绘制文字
        Painter.drawText("坦克大战",300,300,Color.YELLOW,Font.font(20.0))
    }

    /**
     * 用户交互
     * 键盘事件监听
     */
    override fun onKeyPressed(event: KeyEvent) {
        when(event.code){
            KeyCode.ENTER-> {
                println("点击了Enter键")
                //播放爆炸声音
                Composer.play("blast.wav")
//                Composer.playLoop("blast.wav")
            }
        }
    }

    /**
     * 界面刷新  耗时操作
     */
    override fun onRefresh() {
//        println("界面刷新")
    }
}