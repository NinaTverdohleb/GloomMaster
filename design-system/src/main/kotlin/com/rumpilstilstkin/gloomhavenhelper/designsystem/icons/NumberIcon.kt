package com.rumpilstilstkin.gloomhavenhelper.designsystem.icons

import com.rumpilstilstkin.gloomhavenhelper.designsystem.R

enum class NumberIcon(override val resId: Int) : GloomIcon {
    Number0(R.drawable.ic_0),
    Number1(R.drawable.ic_1),
    Number2(R.drawable.ic_2),
    Number3(R.drawable.ic_3),
    Number4(R.drawable.ic_4),
    Number5(R.drawable.ic_5),
    Number6(R.drawable.ic_6),
    Number7(R.drawable.ic_7),
    Number8(R.drawable.ic_8),
    Number9(R.drawable.ic_9),
    Number10(R.drawable.ic_10),
    Number11(R.drawable.ic_11),
    Number12(R.drawable.ic_12),
    Number13(R.drawable.ic_13),
    Number14(R.drawable.ic_14),
    Number15(R.drawable.ic_15);
}

fun Int.toNumberIcon() = when (this) {
    1 -> NumberIcon.Number1
    2 -> NumberIcon.Number2
    3 -> NumberIcon.Number3
    4 -> NumberIcon.Number4
    5 -> NumberIcon.Number5
    6 -> NumberIcon.Number6
    7 -> NumberIcon.Number7
    8 -> NumberIcon.Number8
    9 -> NumberIcon.Number9
    10 -> NumberIcon.Number10
    11 -> NumberIcon.Number11
    12 -> NumberIcon.Number12
    13 -> NumberIcon.Number13
    14 -> NumberIcon.Number14
    15 -> NumberIcon.Number15
    else -> NumberIcon.Number0


}