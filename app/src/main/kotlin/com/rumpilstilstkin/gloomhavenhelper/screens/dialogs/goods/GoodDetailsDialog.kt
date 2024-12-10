package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.goods

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.painterResource
import com.rumpilstilstkin.gloomhavenhelper.R

import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoodDetailsDialog(
    goodNumber: Int,
    showDialog: Boolean,
    onAction: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    buttonText: String = "Добавть",
    isAction: Boolean = true,
) {
    if (!showDialog) return

    BasicAlertDialog(
        modifier = modifier,
        content = {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black
                )

            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(
                            width = 240.dp,
                            height = 350.dp
                        ),
                        contentScale = ContentScale.FillBounds,
                        painter = painterResource(id = goodNumber.toGoodImage()),
                        contentDescription = null
                    )
                    if(isAction) {
                        Button(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .width(240.dp),
                            onClick = { onAction(goodNumber) }
                        ) {
                            Text(buttonText)
                        }
                    }
                }
            }
        },
        onDismissRequest = { onDismiss() },
    )
}

private fun Int.toGoodImage(): Int = when (this) {
    1 -> R.drawable.item_1
    2 -> R.drawable.item_2
    3 -> R.drawable.item_3
    4 -> R.drawable.item_4
    5 -> R.drawable.item_5
    6 -> R.drawable.item_6
    7 -> R.drawable.item_7
    8 -> R.drawable.item_8
    9 -> R.drawable.item_9
    10 -> R.drawable.item_10
    11 -> R.drawable.item_11
    12 -> R.drawable.item_12
    13 -> R.drawable.item_13
    14 -> R.drawable.item_14
    15 -> R.drawable.item_15
    16 -> R.drawable.item_16
    17 -> R.drawable.item_17
    18 -> R.drawable.item_18
    19 -> R.drawable.item_19
    20 -> R.drawable.item_20
    21 -> R.drawable.item_21
    22 -> R.drawable.item_22
    23 -> R.drawable.item_23
    24 -> R.drawable.item_24
    25 -> R.drawable.item_25
    26 -> R.drawable.item_26
    27 -> R.drawable.item_27
    28 -> R.drawable.item_28
    29 -> R.drawable.item_29
    30 -> R.drawable.item_30
    31 -> R.drawable.item_31
    32 -> R.drawable.item_32
    33 -> R.drawable.item_33
    34 -> R.drawable.item_34
    35 -> R.drawable.item_35
    36 -> R.drawable.item_36
    37 -> R.drawable.item_37
    38 -> R.drawable.item_38
    39 -> R.drawable.item_39
    40 -> R.drawable.item_40
    41 -> R.drawable.item_41
    42 -> R.drawable.item_42
    43 -> R.drawable.item_43
    44 -> R.drawable.item_44
    45 -> R.drawable.item_45
    46 -> R.drawable.item_46
    47 -> R.drawable.item_47
    48 -> R.drawable.item_48
    49 -> R.drawable.item_49
    50 -> R.drawable.item_50
    51 -> R.drawable.item_51
    52 -> R.drawable.item_52
    53 -> R.drawable.item_53
    54 -> R.drawable.item_54
    55 -> R.drawable.item_55
    56 -> R.drawable.item_56
    57 -> R.drawable.item_57
    58 -> R.drawable.item_58
    59 -> R.drawable.item_59
    60 -> R.drawable.item_60
    61 -> R.drawable.item_61
    62 -> R.drawable.item_62
    63 -> R.drawable.item_63
    64 -> R.drawable.item_64
    65 -> R.drawable.item_65
    66 -> R.drawable.item_66
    67 -> R.drawable.item_67
    68 -> R.drawable.item_68
    69 -> R.drawable.item_69
    70 -> R.drawable.item_70
    71 -> R.drawable.item_71
    72 -> R.drawable.item_72
    73 -> R.drawable.item_73
    74 -> R.drawable.item_74
    75 -> R.drawable.item_75
    76 -> R.drawable.item_76
    77 -> R.drawable.item_77
    78 -> R.drawable.item_78
    79 -> R.drawable.item_79
    80 -> R.drawable.item_80
    81 -> R.drawable.item_81
    82 -> R.drawable.item_82
    83 -> R.drawable.item_83
    84 -> R.drawable.item_84
    85 -> R.drawable.item_85
    86 -> R.drawable.item_86
    87 -> R.drawable.item_87
    88 -> R.drawable.item_88
    89 -> R.drawable.item_89
    90 -> R.drawable.item_90
    91 -> R.drawable.item_91
    92 -> R.drawable.item_92
    93 -> R.drawable.item_93
    94 -> R.drawable.item_94
    95 -> R.drawable.item_95
    96 -> R.drawable.item_96
    97 -> R.drawable.item_97
    98 -> R.drawable.item_98
    99 -> R.drawable.item_99
    100 -> R.drawable.item_100
    101 -> R.drawable.item_101
    102 -> R.drawable.item_102
    103 -> R.drawable.item_103
    104 -> R.drawable.item_104
    105 -> R.drawable.item_105
    106 -> R.drawable.item_106
    107 -> R.drawable.item_107
    108 -> R.drawable.item_108
    109 -> R.drawable.item_109
    110 -> R.drawable.item_110
    111 -> R.drawable.item_111
    112 -> R.drawable.item_112
    113 -> R.drawable.item_113
    114 -> R.drawable.item_114
    115 -> R.drawable.item_115
    116 -> R.drawable.item_116
    117 -> R.drawable.item_117
    118 -> R.drawable.item_118
    119 -> R.drawable.item_119
    120 -> R.drawable.item_120
    121 -> R.drawable.item_121
    122 -> R.drawable.item_122
    123 -> R.drawable.item_123
    124 -> R.drawable.item_124
    125 -> R.drawable.item_125
    126 -> R.drawable.item_126
    127 -> R.drawable.item_127
    128 -> R.drawable.item_128
    129 -> R.drawable.item_129
    130 -> R.drawable.item_130
    131 -> R.drawable.item_131
    132 -> R.drawable.item_132
    133 -> R.drawable.item_133
    152 -> R.drawable.item_152
    153 -> R.drawable.item_153
    154 -> R.drawable.item_154
    155 -> R.drawable.item_155
    156 -> R.drawable.item_156
    157 -> R.drawable.item_157
    158 -> R.drawable.item_158
    159 -> R.drawable.item_159
    160 -> R.drawable.item_160
    161 -> R.drawable.item_161
    162 -> R.drawable.item_162
    163 -> R.drawable.item_163
    164 -> R.drawable.item_164
    else -> R.drawable.unknown_good
}

@Preview
@Composable
private fun GoodDetailsDialogPreview() {
    GloomhavenHalperTheme {
        GoodDetailsDialog(
            goodNumber = 19,
            showDialog = true
        )
    }
}