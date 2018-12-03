package com.v.minesweeper.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.v.minesweeper.R
import com.v.minesweeper.constant.*
import java.util.*
import kotlin.collections.HashMap

/**
 * the mine view
 * @author V
 * @since 2018/6/7
 */
class MineView @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var _width: Int = 0//横向格子数量
    private var _height: Int = 0//纵向格子数量
    private var mineNum: Int = 0//雷的数量
    private var _itemSize: Int = 0//每个格子的大小
    private var minePositions = arrayListOf<Int>()//地雷分布
    private var searchMap = HashMap<Int, Int>()//地雷搜索状态
    private var clickX: Int = -1//当前点击的X坐标
    private var clickY: Int = -1//当前点击的Y坐标
    var state: Int = -1
        //当前
        set(value) {
            field = if (value in arrayOf(STATE_MARK, STATE_SEARCH)) value else -1
        }
    private var searchedArea = 0
    var playing = false

    private val random = Random()
    private val mPaint = Paint()
    private val dividerPaint = Paint()
    private val minePaint = Paint()
    private val clickPaint = Paint()

    init {
        mPaint.textSize = 100f

        dividerPaint.isAntiAlias = true
        dividerPaint.style = Paint.Style.STROKE
        dividerPaint.color = Color.BLACK
        dividerPaint.strokeWidth = context.resources.getDimensionPixelOffset(R.dimen.stroke_width).toFloat()

        minePaint.isAntiAlias = true
        minePaint.style = Paint.Style.FILL
        minePaint.textSize = context.resources.getDimensionPixelOffset(R.dimen.mine_text_size).toFloat()

        clickPaint.isAntiAlias = true
        clickPaint.color = 0x80000000.toInt()
        clickPaint.style = Paint.Style.FILL

        _itemSize = context.resources.getDimensionPixelSize(R.dimen.mine_item_size)
    }

    /**
     * 调用该方法初始化一局游戏
     */
    fun play(width: Int, height: Int, mineNum: Int) {
        playing = true

        _width = width
        _height = height
        this.mineNum = mineNum

        clickX = -1
        clickY = -1
        state = STATE_SEARCH
        searchedArea = 0
        searchMap.clear()
        minePositions.clear()
        //随机生成雷
        var errorCount = 0
        for (i in 0 until mineNum) {
            var mine = random.nextInt(_width * _height)
            var tryCount = 0
            while (minePositions.contains(mine) && tryCount < 10) {
                mine = random.nextInt(_width * _height)
                tryCount++
            }
            if (tryCount >= 10) {//如果尝试10次还不能找到没有重复的雷的位置，标记一次错误的尝试
                errorCount++
            }
            minePositions.add(mine)
        }
        if (errorCount > 0) {
            Log.w("MineView", "warn: mine generate error for $errorCount times")
        }

        postInvalidate()//所有数据生成好之后刷新
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //根据每个格子大小计算宽高
        val width = _itemSize * _width + paddingLeft + paddingRight
        val height = _itemSize * _height + paddingTop + paddingBottom

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        if (isInEditMode) {
            canvas.drawText("in edit mode!", 0f, (height / 2).toFloat(), mPaint)
            return
        }
        if (_height == 0 || _width == 0) return

        canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
        drawGrid(canvas)
        drawMines(canvas)
        drawClickEffect(canvas)
    }

    private fun drawGrid(canvas: Canvas) {
        //画竖线
        canvas.save()
        for (i in 0.._width) {
            canvas.drawLine(0f, 0f, 0f, _itemSize.toFloat() * _height, dividerPaint)
            canvas.translate(_itemSize.toFloat(), 0f)
        }
        canvas.restore()

        //画横线
        canvas.save()
        for (i in 0.._height) {
            canvas.drawLine(0f, 0f, _itemSize.toFloat() * _width, 0f, dividerPaint)
            canvas.translate(0f, _itemSize.toFloat())
        }
        canvas.restore()
    }

    private fun drawMines(canvas: Canvas) {
        for (x in 0 until _width)
            for (y in 0 until _height) {
                val index = x + y * _width
                when (searchMap[index]) {//根据不同情况绘制出文字
                    MINE_STATE_NUM_1,
                    MINE_STATE_NUM_2,
                    MINE_STATE_NUM_3,
                    MINE_STATE_NUM_4,
                    MINE_STATE_NUM_5,
                    MINE_STATE_NUM_6,
                    MINE_STATE_NUM_7,
                    MINE_STATE_NUM_8,
                    MINE_STATE_FLAG_MINE,
                    MINE_STATE_FLAG_UNKNOWN,
                    MINE_STATE_REAL_MINE
                    -> {
                        val n = searchMap[index]
                        minePaint.color = getNumColor(n!!)
                        val text = if (n in MINE_STATE_NUM_1..MINE_STATE_NUM_8) n.toString() else when (n) {MINE_STATE_FLAG_MINE -> "F"
                            MINE_STATE_FLAG_UNKNOWN -> "?"
                            MINE_STATE_REAL_MINE -> "M"
                            else -> ""
                        }
                        //计算文字的位置和绘制文字
                        val dx = (_itemSize * x).toFloat() + ((_itemSize - minePaint.measureText(text)) / 2)
                        val dy = (_itemSize * (y + 1)).toFloat() - ((_itemSize - minePaint.textSize) / 2)
                        canvas.drawText(text, dx, dy, minePaint)
                    }
                    MINE_STATE_NUM_0 -> {
                        val dx = x * _itemSize.toFloat()
                        val dy = y * _itemSize.toFloat()
                        minePaint.color = getNumColor(0)
                        canvas.drawRect(dx, dy, dx + _itemSize, dy + _itemSize, minePaint)
                    }
                    MINE_STATE_DEFAULT, null -> {
                    }
                    else -> {
                    }
                }
            }
    }

    @ColorInt
    private fun getNumColor(num: Int): Int {
        return when (num) {
            0 -> Color.GRAY
            1 -> 0xff0080ff.toInt()
            2 -> 0xff00a000.toInt()
            3 -> Color.RED
            4 -> 0xff0000a0.toInt()
            5 -> 0xff800000.toInt()
            6 -> 0xff00a0a0.toInt()
            7 -> Color.BLACK
            8 -> Color.GRAY
            MINE_STATE_FLAG_UNKNOWN -> Color.BLUE
            MINE_STATE_FLAG_MINE -> Color.RED
            MINE_STATE_REAL_MINE -> Color.BLACK
            else -> Color.BLACK
        }
    }

    /**
     * 点击效果绘制
     * 显示一个灰色遮罩
     */
    private fun drawClickEffect(canvas: Canvas) {
        if (clickX < 0 || clickX > _width - 1 || clickY < 0 || clickY > _height - 1) return
        canvas.save()

        canvas.translate((_itemSize * clickX).toFloat(), (_itemSize * clickY).toFloat())
        canvas.drawRect(0f, 0f, _itemSize.toFloat(), _itemSize.toFloat(), clickPaint)

        canvas.restore()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val result = super.onTouchEvent(event)
        if (playing) {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    //按下时记录index，并刷新（因为需要绘制点击效果）
                    markIndex(event)
                    postInvalidate()
                    return true
                }
                MotionEvent.ACTION_UP -> {
                    //放手时计算点击并清空点击的index
                    checkClick()
                    clearIndex()
                    postInvalidate()
                    return true
                }
                MotionEvent.ACTION_CANCEL -> {
                    //取消点击时清空点击的index
                    clearIndex()
                    postInvalidate()
                }
            }
        }
        return result
    }

    /**
     * 根据xy坐标位置计算Index
     */
    private fun markIndex(event: MotionEvent) {
        clickX = (event.x / _itemSize).toInt()
        clickY = (event.y / _itemSize).toInt()
    }

    /**
     * 清掉index
     */
    private fun clearIndex() {
        clickX = -1
        clickY = -1
    }

    /**
     * 根据已经记录好的xy点击坐标执行相关逻辑
     */
    private fun checkClick() {
        if (!checkBoundsIn(clickX, clickY)) {
            return
        }

        val index = clickX + clickY * _width
        when (state) {
            STATE_MARK -> {//标记模式逻辑
                when (searchMap[index]) {
                    MINE_STATE_DEFAULT, null -> {
                        searchMap[index] = MINE_STATE_FLAG_MINE
                    }
                    MINE_STATE_FLAG_MINE -> {
                        searchMap[index] = MINE_STATE_FLAG_UNKNOWN
                    }
                    MINE_STATE_FLAG_UNKNOWN -> {
                        searchMap[index] = MINE_STATE_DEFAULT
                    }
                    else -> {
                        return
                    }
                }
            }
            STATE_SEARCH -> {//搜索模式逻辑
                when (searchMap[index]) {
                    MINE_STATE_FLAG_UNKNOWN, MINE_STATE_DEFAULT, null -> {
                        checkMine(clickX, clickY, index)
                    }
                    else -> {
                        return
                    }
                }
            }
        }

    }

    /**
     * 检测目标位置的雷的分布情况
     */
    private fun checkMine(clickX: Int, clickY: Int, index: Int) {
        if (minePositions.contains(index)) {
            showMines()
            gameOver(false)
        } else {
            var mineCount = 0
            val around = getAround(clickX, clickY)
            for (i in around) {
                i?.let {
                    if (minePositions.contains(it))
                        mineCount++
                }
            }
            searchMap[index] = mineCount
            searchedArea++
            if (!checkGameOver())
                if (mineCount == 0) {//如果目标位置的雷数为0则需要用递归扩散检查所有边界
                    for (i in around) {
                        i?.let {
                            if (checkBoundsIn(clickX, clickY) && searchMap[it] in arrayOf(MINE_STATE_FLAG_UNKNOWN, MINE_STATE_DEFAULT, null)) {
                                checkMine(it % _width, it / _width, it)
                            }
                        }
                    }
                }
        }


    }

    /**
     * 检测游戏是否已经结束
     */
    private fun checkGameOver(): Boolean {
        if (searchedArea + mineNum == _width * _height) {
            gameOver(true)
            return true
        }
        return false
    }

    /**
     * 获取一个xy坐标位置周围的8个位置的index
     */
    private fun getAround(clickX: Int, clickY: Int): Array<Int?> {
        return arrayOf(
                xy2Index(clickX - 1, clickY - 1),    //left top
                xy2Index(clickX - 1, clickY),              //left
                xy2Index(clickX - 1, clickY + 1),   //left bottom
                xy2Index(clickX, clickY - 1),              //top
                xy2Index(clickX, clickY + 1),             //bottom
                xy2Index(clickX + 1, clickY - 1),   //right top
                xy2Index(clickX + 1, clickY),             //right
                xy2Index(clickX + 1, clickY + 1)   //right bottom
        )
    }

    private fun showMines() {
        for (i in minePositions) {
            searchMap[i] = MINE_STATE_REAL_MINE
        }
        postInvalidate()
    }

    private fun gameOver(win: Boolean) {
        Toast.makeText(context, "游戏结束，你${if (win) "赢" else "输"}了", Toast.LENGTH_LONG).show()

        playing = false
    }

    private fun xy2Index(x: Int, y: Int): Int? = if (checkBoundsIn(x, y)) (x + y * _width) else null

    private fun checkBoundsIn(x: Int, y: Int) = x in 0 until _width && y in 0 until _height
}
