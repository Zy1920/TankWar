import business.*
import enmus.Direction
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import model.*
import org.itheima.kotlin.game.core.Window
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList

/**
 * ClassName:GameWindow
 * Description:游戏窗体
 */
class GameWindow : Window(title = "坦克大战", icon = "img/logo.jpg",
        width = Config.gameWidth, height = Config.gameHeight) {
    //所有控件的集合
    val viewList by lazy { CopyOnWriteArrayList<View>() }

    //我方坦克
    val myTank by lazy { Tank(10 * Config.blockSize, 12 * Config.blockSize) }
    //游戏结束标记
    var gameOver = false

    override fun onCreate() {
        //初始化地图
        val file = File(javaClass.getResource("map/1.map").path)
        //地图每一行数据集合
        val lines = file.readLines(Charsets.UTF_8)
        lines.forEachIndexed { lineIndex, s ->
            s.forEachIndexed { rowIndex, c ->
                when (c) {
                    '砖' -> viewList.add(Wall(rowIndex * Config.blockSize, lineIndex * Config.blockSize))
                    '铁' -> viewList.add(Steel(rowIndex * Config.blockSize, lineIndex * Config.blockSize))
                    '草' -> viewList.add(Grass(rowIndex * Config.blockSize, lineIndex * Config.blockSize))
                    '水' -> viewList.add(Water(rowIndex * Config.blockSize, lineIndex * Config.blockSize))
                    '敌' -> viewList.add(Enemy(rowIndex * Config.blockSize, lineIndex * Config.blockSize))
                }
            }
        }

        //添加我方坦克
        viewList.add(myTank)

        //添加大本营
        viewList.add(Camp(Config.gameWidth/2-Config.blockSize,Config.gameHeight-3*Config.blockSize/2))
    }

    override fun onDisplay() {
        //打印界面元素个数
//        println(viewList.size)
        //绘制每一个元素
        viewList.forEach { it.draw() }
    }

    override fun onKeyPressed(event: KeyEvent) {
        if(gameOver)return
        when (event.code) {
            KeyCode.W -> myTank.direction = Direction.UP
            KeyCode.S -> myTank.direction = Direction.DOWN
            KeyCode.A -> myTank.direction = Direction.LEFT
            KeyCode.D -> myTank.direction = Direction.RIGHT
            KeyCode.ENTER -> viewList.add(myTank.shot())
        }
    }

    override fun onRefresh() {
        //检测子弹销毁
        viewList.filter { it is DestoryAble }.forEach {
            it as DestoryAble
            //如果需要销毁  就从viewList集合中移除
            if (it.needDestory()) {
                viewList.remove(it)
                //销毁效果
                val destotyView = it.showDestory()
                destotyView?.let {
                    viewList.addAll(destotyView)
                }
            }
        }
        if(gameOver)return

        //检测碰撞
        //通过坦克和每一个阻挡物进行判断是否会碰撞
        val moveList = viewList.filter { it is MoveAble }

        val blockList = viewList.filter { it is BlockAble }

        tag1@ for (move in moveList) {
            move as MoveAble
            //将要和哪个碰撞物碰撞 null没有碰撞
            var badBlock: BlockAble? = null
            //将要和碰撞物碰撞的方向
            var badDirection: Direction? = null

            tag2@ for (block in blockList) {
                //遇到和自己比较就跳出当前循环
                if(block==move)continue

                block as BlockAble
                //碰撞判断
                val direction = move.willCollision(block)
                if (direction != null) {
                    //碰撞
                    badDirection = direction
                    badBlock = block
                    //跳出内层循环
                    break@tag2
                }
            }
            //通知碰撞
            move.notifyCollision(badBlock, badDirection)
        }

        //子弹自动移动
        viewList.filter { it is AutoMoveAble }.forEach {
            it as AutoMoveAble
            it.autoMove()
        }



        //攻击者和受攻击者碰撞检测
        val attackList = viewList.filter { it is AttackAble }
        val sufferList = viewList.filter { it is SufferAble }
        //1.循环攻击能力
        outTag@for (attack in attackList) {
            attack as AttackAble
            //2.循环受攻击能力
            inTag@for (suffer in sufferList) {
                //如果攻击者是受攻击者  就跳出循环
                if(attack==suffer)continue

                //如果攻击者的所有者就是当前suffer  跳出当前循环
                if(attack.owner==suffer)continue
                suffer as SufferAble
                //判断攻击能力的受攻击能力是否发生碰撞
                if(attack.isCollision(suffer)){
                    //通知攻击者发生了攻击
                    attack.notifyAttack(suffer)
                    //通知受攻击者发生了受攻击
                    val sufferView = suffer.notifySuffer(attack)
                    sufferView?.let {
                        viewList.addAll(it)
                    }
                    break@inTag
                }
            }
        }

        //敌方坦克自动射击
        val autoShotList = viewList.filter { it is AutoShotAble }
        autoShotList.forEach {
            it as AutoShotAble
            val bullet = it.autoShot()
            bullet?.let {
                viewList.add(it)
            }
        }
        //判断游戏结束
        if(viewList.filter { it is Camp }.isEmpty()){
            gameOver = true
        }
    }
}